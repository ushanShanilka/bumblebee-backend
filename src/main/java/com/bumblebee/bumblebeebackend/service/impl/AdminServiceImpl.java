package com.bumblebee.bumblebeebackend.service.impl;

import com.bumblebee.bumblebeebackend.dto.AdminRegisterDTO;
import com.bumblebee.bumblebeebackend.dto.AuthenticationRequestDTO;
import com.bumblebee.bumblebeebackend.dto.LoginResponseDTO;
import com.bumblebee.bumblebeebackend.dto.PasswordChangeDTO;
import com.bumblebee.bumblebeebackend.entity.AdminLoginCredential;
import com.bumblebee.bumblebeebackend.entity.Status;
import com.bumblebee.bumblebeebackend.repo.AdminLoginCredentialRepo;
import com.bumblebee.bumblebeebackend.repo.AdminRepo;
import com.bumblebee.bumblebeebackend.repo.AdminTypeRepo;
import com.bumblebee.bumblebeebackend.repo.StatusRepo;
import com.bumblebee.bumblebeebackend.service.AdminService;
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
import org.springframework.stereotype.Service;

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

    @Override
    public String adminSingUp (AdminRegisterDTO dto) {
        return null;
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
