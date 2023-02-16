package com.bumblebee.bumblebeebackend.service.impl;

import com.bumblebee.bumblebeebackend.dto.AdminRegisterDTO;
import com.bumblebee.bumblebeebackend.dto.AuthenticationRequestDTO;
import com.bumblebee.bumblebeebackend.dto.LoginResponseDTO;
import com.bumblebee.bumblebeebackend.dto.PasswordChangeDTO;
import com.bumblebee.bumblebeebackend.entity.*;
import com.bumblebee.bumblebeebackend.exception.EntryDuplicateException;
import com.bumblebee.bumblebeebackend.repo.*;
import com.bumblebee.bumblebeebackend.service.AdminService;
import com.bumblebee.bumblebeebackend.util.AdminTypeId;
import com.bumblebee.bumblebeebackend.util.JwtUtil;
import com.bumblebee.bumblebeebackend.util.LoginStatusId;
import com.bumblebee.bumblebeebackend.util.StatusId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

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

        if (Objects.equals(adminLogin, null)) {
            response.setMessage("not found");
            response.setCode(404);
            return response;
        }

        boolean exists = adminRepo.existsById(adminLogin.getAdminId().getId());
        if (!exists){
            response.setMessage("not found");
            response.setCode(404);
            return response;
        }

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        if (Objects.equals(authenticate, null)){
            throw new BadCredentialsException("Bad Credentials");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        String jwt = jwtUtil.generateToken(userDetails);

        adminLogin.setLoginStatus(LoginStatusId.LOGGEDIN);
        adminLogin.setActiveJwt(jwt);
        adminLogin.setUpdatedAt(new Date());
        adminLoginCredentialRepo.save(adminLogin);

        response.setCode(200);
        response.setJwt(jwt);
        response.setMessage("success");

        return response;
    }

    @Transactional
    @Override
    public String adminSingUp (AdminRegisterDTO dto, String userName) {
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

    @Override
    public LoginResponseDTO adminPasswordChange (PasswordChangeDTO dto) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getAllAdmin (String value) {
        return null;
    }

    @Override
    public String deleteUser (Long adminId) {
        return null;
    }
}
