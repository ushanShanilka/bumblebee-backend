package com.bumblebee.bumblebeebackend.service.impl;

import com.bumblebee.bumblebeebackend.dto.*;
import com.bumblebee.bumblebeebackend.entity.*;
import com.bumblebee.bumblebeebackend.exception.CustomException;
import com.bumblebee.bumblebeebackend.exception.EntryDuplicateException;
import com.bumblebee.bumblebeebackend.exception.EntryNotFoundException;
import com.bumblebee.bumblebeebackend.repo.*;
import com.bumblebee.bumblebeebackend.service.AdminService;
import com.bumblebee.bumblebeebackend.util.AdminTypeId;
import com.bumblebee.bumblebeebackend.util.JwtUtil;
import com.bumblebee.bumblebeebackend.util.LoginStatusId;
import com.bumblebee.bumblebeebackend.util.StatusId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.transaction.TransactionalException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    StatusRepo statusRepo;
    @Autowired
    AdminRepo adminRepo;
    @Autowired
    AdminLoginCredentialRepo adminLoginCredentialRepo;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    AdminTypeRepo adminTypeRepo;
    @Autowired
    AdminPasswordRepo adminPasswordRepo;


    @Override
    public LoginResponseDTO adminLogin (AuthenticationRequestDTO dto) {
        LoginResponseDTO response = new LoginResponseDTO();

        Status status = statusRepo.findById(StatusId.ACTIVE);
        AdminLoginCredential adminLogin = adminLoginCredentialRepo.findByUserNameAndStatusId(dto.getUsername(), status);

        if (Objects.equals(adminLogin, null)) {
            throw new EntryNotFoundException("User Not Found");
        }
        boolean exists = adminRepo.existsByIdAndStatusId(adminLogin.getAdminId().getId(), status);
        if (!exists){
            throw new EntryNotFoundException("User Not Found");
        }

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        if (Objects.equals(authenticate, null)){
            throw new BadCredentialsException("Bad Credentials");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        String jwt = jwtUtil.generateToken(userDetails,"ADMIN");

        adminLogin.setLoginStatus(LoginStatusId.LOGGEDIN);
        adminLogin.setActiveJwt(jwt);
        adminLogin.setUpdatedAt(new Date());
        adminLoginCredentialRepo.save(adminLogin);

        response.setCode(200);
        response.setJwt(jwt);
        response.setUserName(adminLogin.getUserName());
        response.setMessage("success");

        return response;
    }

    @Override
    @Transactional
    @Modifying
    public String adminSingUp (RegisterDTO dto, String userName) {
        Status active = statusRepo.findById(StatusId.ACTIVE);
        AdminLoginCredential adminLogin = adminLoginCredentialRepo.findByUserNameAndStatusId(userName, active);

        if (!Objects.equals(adminLogin, null)){
            Admin admin = adminRepo.findByEmailAndStatusId(userName, active);
            if (Objects.equals(admin.getAdminTypeId().getId(), AdminTypeId.SUPERADMIN)){
                if (adminRepo.existsByEmailAndStatusId(dto.getEmail(), active)) {
                    throw new EntryDuplicateException("Email Already Exist!");
                }

                if (!Objects.equals(dto.getEmail(), dto.getConfirmEmail())){
                    throw new CustomException("Email and confirm email not equal");
                }

                if (!Objects.equals(dto.getPassword(), dto.getConfirmPassword())){
                    throw new CustomException("Password not equal");
                }

                AdminType type = adminTypeRepo.findById(AdminTypeId.ADMIN);
                Date date = new Date();

                Status status = null;

                System.out.println(dto.getStatus());

                if (dto.getStatus() == 1){
                    status = statusRepo.findById(StatusId.ACTIVE);
                }else {
                    status = statusRepo.findById(StatusId.NOTVERIFIED);
                }

                Admin a = new Admin();
                a.setFirstName(dto.getFirstName());
                a.setLastName(dto.getLastName());
                a.setEmail(dto.getEmail());
                a.setDateOfBirth(dto.getDateOfBirth());
                a.setCountryCode(dto.getCountryCode());
                a.setPhoneNumber(dto.getPhoneNumber());
                a.setAdminTypeId(type);
                a.setStatusId(status);
                a.setCreatedAt(date);
                a.setUpdatedAt(date);

                Admin save = adminRepo.save(a);
                if (!Objects.equals(save.getId(),null)){
                    AdminLoginCredential adminLoginCredential = new AdminLoginCredential();
                    adminLoginCredential.setAdminId(save);
                    adminLoginCredential.setUserName(dto.getEmail());
                    adminLoginCredential.setStatusId(active);
                    adminLoginCredential.setCreatedAt(date);
                    adminLoginCredential.setUpdatedAt(date);
                    AdminLoginCredential loginCredential = adminLoginCredentialRepo.save(adminLoginCredential);

                    if (!Objects.equals(loginCredential.getId(),null)){
                        AdminPassword password = new AdminPassword();
                        password.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10)));
                        password.setAdminLoginCredentialId(adminLoginCredential);
                        password.setStatusId(active);
                        password.setCreatedAt(date);
                        password.setUpdatedAt(date);
                        adminPasswordRepo.save(password);
                        return "Success";
                    }else {
                        throw new TransactionalException("something went wrong",null);
                    }
                }else {
                    throw new TransactionalException("something went wrong",null);
                }
            }
            throw new BadCredentialsException("Invalid super admin");
        }
        throw new BadCredentialsException("Invalid admin");
    }

    @Override
    @Transactional
    @Modifying
    public String updateAdmin (AdminDTO dto, String type) {
        if (Objects.equals(type, "ADMIN") || Objects.equals(type, "SUPERADMIN")){

            Admin a = adminRepo.getAdmin(dto.getId());
            if (!Objects.equals(dto.getEmail(), dto.getConfirmEmail())){
                throw new CustomException("Email and confirm email not equal");
            }

            if (!Objects.equals(a, null)){

                Date date = new Date();

                Status status = null;

                if (dto.getStatus() == 1){
                    status = statusRepo.findById(StatusId.ACTIVE);
                }else {
                    status = statusRepo.findById(StatusId.NOTVERIFIED);
                }

                a.setDateOfBirth(dto.getBirthDay());
                a.setCountryCode(dto.getCountryCode());
                a.setEmail(dto.getEmail());
                a.setFirstName(dto.getFirstName());
                a.setLastName(dto.getLastName());
                a.setUpdatedAt(date);
                a.setStatusId(status);
                a.setPhoneNumber(dto.getPhoneNumber());

                adminRepo.save(a);

                AdminLoginCredential loginCredential = adminLoginCredentialRepo.findByAdminId(a);
                loginCredential.setUserName(dto.getEmail());
                loginCredential.setUpdatedAt(date);
                adminLoginCredentialRepo.save(loginCredential);

                Status active = statusRepo.findById(StatusId.ACTIVE);

                System.out.println(dto.getPassword());

                if (!dto.getPassword().equals("")){

                    if (!Objects.equals(dto.getPassword(), dto.getConfirmPassword())){
                        throw new CustomException("Password not equal");
                    }

                    AdminPassword password = adminPasswordRepo.findByAdminLoginCredentialIdAndStatusId(loginCredential, active);
                    password.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10)));
                    password.setUpdatedAt(date);
                    adminPasswordRepo.save(password);
                }

                return "success";
            }
            throw new EntryNotFoundException("user not exist");
        }
        throw new BadCredentialsException("invalid user");
    }

    @Override
    public LoginResponseDTO adminPasswordChange (PasswordChangeDTO dto) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getAllAdmin (String type) {
        if (Objects.equals(type, "ADMIN") || Objects.equals(type, "SUPERADMIN")){
            List<Admin> all = adminRepo.findAll();
            List<Map<String, Object>> map = new ArrayList<>();

            for (Admin a:all) {
                Map<String, Object> obj = new HashMap<>();
                obj.put("id",a.getId());
                obj.put("countryCode",a.getCountryCode());
                obj.put("createdAt",new SimpleDateFormat("dd-MMM-yyyy").format(a.getUpdatedAt()));
                obj.put("updatedAt",new SimpleDateFormat("dd-MMM-yyyy").format(a.getUpdatedAt()));
                obj.put("birthDay",a.getDateOfBirth());
                obj.put("email",a.getEmail());
                obj.put("confirmEmail",a.getEmail());
                obj.put("firstName",a.getFirstName());
                obj.put("lastName",a.getLastName());
                obj.put("phoneNumber",a.getPhoneNumber());
                obj.put("adminType",a.getAdminTypeId().getType());
                obj.put("status",a.getStatusId().getId());

                map.add(obj);
            }
            return map;
        }
        throw new BadCredentialsException("invalid user");
    }

    @Override
    public Map<String, Object> getAdmin (Long adminId,String type) {
        if (Objects.equals(type, "ADMIN") || Objects.equals(type, "SUPERADMIN")){
            Admin a = adminRepo.getAdmin(adminId);
            if (!Objects.equals(a, null)){
                Map<String, Object> obj = new HashMap<>();
                obj.put("id",a.getId());
                obj.put("countryCode",a.getCountryCode());
                obj.put("createdAt",new SimpleDateFormat("dd-MMM-yyyy").format(a.getUpdatedAt()));
                obj.put("updatedAt",new SimpleDateFormat("dd-MMM-yyyy").format(a.getUpdatedAt()));
                obj.put("birthDay",a.getDateOfBirth());
                obj.put("email",a.getEmail());
                obj.put("confirmEmail",a.getEmail());
                obj.put("firstName",a.getFirstName());
                obj.put("lastName",a.getLastName());
                obj.put("phoneNumber",a.getPhoneNumber());
                obj.put("adminType",a.getAdminTypeId().getType());
                obj.put("status",a.getStatusId().getId());

                return obj;
            }
            throw new EntryNotFoundException("user not exist");
        }
        throw new BadCredentialsException("invalid user");
    }

    @Override
    @Transactional
    @Modifying
    public String deleteUser (Long adminId) {
        return null;
    }

    public String singUp (RegisterDTO dto, String userName) {
        Status active = statusRepo.findById(StatusId.ACTIVE);
        AdminLoginCredential adminLogin = adminLoginCredentialRepo.findByUserNameAndStatusId(userName, active);

        if (!Objects.equals(adminLogin, null)){
            Admin admin = adminRepo.findByEmailAndStatusId(userName, active);
            if (Objects.equals(admin.getAdminTypeId().getId(), AdminTypeId.SUPERADMIN)){

                if (!adminRepo.existsByEmailAndStatusId(dto.getEmail(), active)){
                    AdminType type = adminTypeRepo.findById(AdminTypeId.ADMIN);
                    Date date = new Date();

                    Admin a = new Admin();
                    a.setFirstName(dto.getFirstName());
                    a.setLastName(dto.getLastName());
                    a.setEmail(dto.getEmail());
                    a.setCountryCode(dto.getCountryCode());
                    a.setPhoneNumber(dto.getPhoneNumber());
                    a.setAdminTypeId(type);
                    a.setStatusId(active);
                    a.setCreatedAt(date);
                    a.setUpdatedAt(date);

                    Admin save = adminRepo.save(a);
                    if (!Objects.equals(save.getId(),null)){
                        AdminLoginCredential adminLoginCredential = new AdminLoginCredential();
                        adminLoginCredential.setAdminId(save);
                        adminLoginCredential.setUserName(dto.getEmail());
                        adminLoginCredential.setStatusId(active);
                        adminLoginCredential.setCreatedAt(date);
                        adminLoginCredential.setUpdatedAt(date);
                        AdminLoginCredential loginCredential = adminLoginCredentialRepo.save(adminLoginCredential);

                        if (!Objects.equals(loginCredential.getId(),null)){
                            AdminPassword password = new AdminPassword();
                            password.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10)));
                            password.setAdminLoginCredentialId(adminLoginCredential);
                            password.setStatusId(active);
                            password.setCreatedAt(date);
                            password.setUpdatedAt(date);
                            adminPasswordRepo.save(password);
                            return "Success";
                        }else {
                            throw new TransactionalException("something went wrong",null);
                        }
                    }else {
                        throw new TransactionalException("something went wrong",null);
                    }
                }else {
                    throw new EntryDuplicateException("Email Already Exist!");
                }
            }
            throw new BadCredentialsException("Invalid super admin");
        }
        throw new BadCredentialsException("Invalid admin");
    }
}
