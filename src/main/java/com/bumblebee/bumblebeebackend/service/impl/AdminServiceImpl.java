package com.bumblebee.bumblebeebackend.service.impl;

import com.bumblebee.bumblebeebackend.dto.RegisterDTO;
import com.bumblebee.bumblebeebackend.dto.AuthenticationRequestDTO;
import com.bumblebee.bumblebeebackend.dto.LoginResponseDTO;
import com.bumblebee.bumblebeebackend.dto.PasswordChangeDTO;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

        System.out.println("1");
        if (Objects.equals(adminLogin, null)) {
            throw new EntryNotFoundException("User Not Found");
        }
        System.out.println("2");
        boolean exists = adminRepo.existsById(adminLogin.getAdminId().getId());
        if (!exists){
            throw new EntryNotFoundException("User Not Found");
        }

        System.out.println("3");
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

                Admin a = new Admin();
                a.setFirstName(dto.getFirstName());
                a.setLastName(dto.getLastName());
                a.setEmail(dto.getEmail());
                a.setDateOfBirth(dto.getDateOfBirth());
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
            }
            throw new BadCredentialsException("Invalid super admin");
        }
        throw new BadCredentialsException("Invalid admin");
    }

    @Override
    public LoginResponseDTO adminPasswordChange (PasswordChangeDTO dto) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getAllAdmin (String value) {
        return null;
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
