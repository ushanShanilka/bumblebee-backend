package com.bumblebee.bumblebeebackend.service.impl;

import com.bumblebee.bumblebeebackend.dto.AuthenticationRequestDTO;
import com.bumblebee.bumblebeebackend.dto.LoginResponseDTO;
import com.bumblebee.bumblebeebackend.dto.RegisterDTO;
import com.bumblebee.bumblebeebackend.entity.*;
import com.bumblebee.bumblebeebackend.exception.CustomException;
import com.bumblebee.bumblebeebackend.exception.EntryDuplicateException;
import com.bumblebee.bumblebeebackend.exception.EntryNotFoundException;
import com.bumblebee.bumblebeebackend.repo.*;
import com.bumblebee.bumblebeebackend.service.MailService;
import com.bumblebee.bumblebeebackend.service.OtpService;
import com.bumblebee.bumblebeebackend.service.UserService;
import com.bumblebee.bumblebeebackend.util.JwtUtil;
import com.bumblebee.bumblebeebackend.util.LoginStatusId;
import com.bumblebee.bumblebeebackend.util.PlanId;
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
import java.util.*;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    StatusRepo statusRepo;
    @Autowired
    UserPasswordRepo userPasswordRepo;
    @Autowired
    UserLoginCredentialRepo userLoginCredentialRepo;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    InstallmentPlanRepo installmentPlanRepo;
    @Autowired
    OtpService otpService;

    @Override
    @Transactional
    @Modifying
    public String userSingUp (RegisterDTO dto) {
        Status active = statusRepo.findById(StatusId.ACTIVE);
        Status notVerified = statusRepo.findById(StatusId.NOTVERIFIED);

        if (!Objects.equals(dto.getEmail(), dto.getConfirmEmail())){
            throw new CustomException("email and confirm email dos not match");
        }

        if (!Objects.equals(dto.getPassword(), dto.getConfirmPassword())){
            throw new CustomException("password and confirm password email dos not match");
        }

        if (userRepo.existsByNicNo(dto.getNic())){
            throw new EntryDuplicateException("nic no already exist");
        }

        if (userRepo.existsByEmail(dto.getEmail())){
            throw new EntryDuplicateException("email already exist");
        }

        InstallmentPlan plan = installmentPlanRepo.getPlanByIdAndStatus(PlanId.INITIAL, 1);
        if (Objects.equals(plan, null)){
            throw new EntryNotFoundException("Plan Not Found");
        }
        Date date = new Date();
        User user = new User(
                0L,
                date,
                date,
                dto.getFirstName()+" "+dto.getFirstName(),
                dto.getDateOfBirth(),
                15000.00,
                0.00,
                dto.getEmail(),
                dto.getNic(),
                dto.getAddress(),
                dto.getCountryCode(),
                dto.getPhoneNumber(),
                plan,
                notVerified
        );
        User saveUser = userRepo.save(user);
        if (!Objects.equals(saveUser.getId(), null)){
            UserLoginCredential userLoginCredential = new UserLoginCredential(
                    0L,
                    date,
                    date,
                    dto.getEmail(),
                    (short)0,
                    "",
                    saveUser,
                    active
            );

            UserLoginCredential saveCredential = userLoginCredentialRepo.save(userLoginCredential);
            if (!Objects.equals(userLoginCredential.getId(), null)){
                UserPassword userPassword = new UserPassword(
                        0L,
                        date,
                        date,
                        BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10)),
                        saveCredential,
                        active
                );
                userPasswordRepo.save(userPassword);
                otpService.sendOtp(user.getEmail(), saveUser);
                return "success";
            }
        }
        throw new CustomException("something went wrong !");
    }

    @Override
    public LoginResponseDTO userLogin (AuthenticationRequestDTO dto) {
        LoginResponseDTO response = new LoginResponseDTO();

        Status status = statusRepo.findById(StatusId.ACTIVE);
        UserLoginCredential userLogin = userLoginCredentialRepo.findByUserNameAndStatusId(dto.getUsername(), status);

        if (Objects.equals(userLogin, null)) {
            response.setMessage("not found");
            response.setCode(404);
            return response;
        }

        if (!userRepo.existsByIdAndStatusId(userLogin.getUserId().getId(), status)){
            response.setMessage("not found");
            response.setCode(404);
            return response;
        }

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        if (Objects.equals(authenticate, null)){
            throw new BadCredentialsException("Bad Credentials");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        String jwt = jwtUtil.generateToken(userDetails, "USER");

        userLogin.setLoginStatus(LoginStatusId.LOGGEDIN);
        userLogin.setActiveJwt(jwt);
        userLogin.setUpdatedAt(new Date());
        userLoginCredentialRepo.save(userLogin);

        response.setCode(200);
        response.setJwt(jwt);
        response.setUserName(userLogin.getUserName());
        response.setMessage("success");

        return response;
    }

    @Override
    public List<Map<String, Object>> getAllUsers (String type) {
        if (type.equals("ADMIN") || type.equals("SUPERADMIN")){
            List<User> all = userRepo.findAll();
            List<Map<String, Object>> map = new ArrayList<>();

            for (User user :all) {
                Map<String, Object> obj = new HashMap<>();
                obj.put("id",user.getId());
                obj.put("fullName",user.getFullName());
                obj.put("dateOfBirth",user.getDateOfBirth());
                obj.put("loanBalance",user.getLoanBalance());
                obj.put("usedAmount",user.getUsedAmount());
                obj.put("plan",user.getInstallmentPlanId().getPlanName());
                obj.put("nic",user.getNicNo());
                obj.put("phoneNumber",user.getPhoneNumber());
                obj.put("address",user.getAddress());
                obj.put("countryCode",user.getCountryCode());
                obj.put("status",user.getStatusId().getStatus());

                map.add(obj);
            }
            return map;
        }
        throw new BadCredentialsException("invalid User");
    }
}
