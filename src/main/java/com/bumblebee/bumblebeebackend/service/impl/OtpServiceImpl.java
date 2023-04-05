package com.bumblebee.bumblebeebackend.service.impl;

import com.bumblebee.bumblebeebackend.dto.LoginResponse;
import com.bumblebee.bumblebeebackend.dto.OtpConfirmationDTO;
import com.bumblebee.bumblebeebackend.entity.Status;
import com.bumblebee.bumblebeebackend.entity.TempOtp;
import com.bumblebee.bumblebeebackend.entity.User;
import com.bumblebee.bumblebeebackend.exception.EntryNotFoundException;
import com.bumblebee.bumblebeebackend.repo.TempOtpRepo;
import com.bumblebee.bumblebeebackend.repo.UserRepo;
import com.bumblebee.bumblebeebackend.service.MailService;
import com.bumblebee.bumblebeebackend.service.OtpService;
import com.bumblebee.bumblebeebackend.service.StatusService;
import com.bumblebee.bumblebeebackend.util.OtpGenerate;
import com.bumblebee.bumblebeebackend.util.StatusId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/3/2023
 **/
@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    TempOtpRepo tempOtpRepo;
    @Autowired
    StatusService statusService;
    @Autowired
    OtpGenerate otpGenerate;
    @Autowired
    UserRepo userRepo;
    @Autowired
    MailService mailService;

    @Override
    @Transactional
    @Modifying
    public String otpConfirmation (OtpConfirmationDTO dto) {
        Status active = statusService.getStatus(StatusId.ACTIVE);

        TempOtp temp = tempOtpRepo.findByEmailAndStatus(dto.getEmail(), active);
        if (!Objects.equals(temp, null)){
            if (BCrypt.checkpw(String.valueOf(dto.getOtp()), temp.getOtp())) {

                tempOtpRepo.delete(temp);
                temp.getUserId().setStatusId(active);
                userRepo.save(temp.getUserId());
                mailService.mailSender("REGISTRATION SUCCESS!","Hello "+temp.getUserId().getFullName()+"<br\">, Thank you for opening an account on our website. We have already given you a loan amount of 15000.00 rupees. To know more about it and use our mobile app", temp.getEmail());
                return "success";
            } else {
                throw new EntryNotFoundException("invalid OTP");
            }
        }
        throw new EntryNotFoundException("otp not found OTP");
    }

    @Override
    public String commonOtpConfirmation (OtpConfirmationDTO dto) {
        return null;
    }

    @Transactional
    @Modifying
    @Override
    public String sendOtp (String email, User user) {
        Status active = statusService.getStatus(StatusId.ACTIVE);
        Date date = new Date();

        TempOtp temp = tempOtpRepo.findByEmailAndStatus(email, active);
        if (!Objects.equals(temp, null)){
            tempOtpRepo.delete(temp);
        }

        int otp = otpGenerate.generateCode();

        TempOtp tempOtp = new TempOtp(
                null,
                email,
                BCrypt.hashpw(String.valueOf(otp), BCrypt.gensalt(10)),
                user,
                date,
                active
        );
        tempOtpRepo.save(tempOtp);
        String message = "Your authorization code is: " + otp + "";

        mailService.mailSender("PLEASE VERIFIED ACCOUNT", message, email);
        return "OTP Send";
    }

    @Override
    public String resendOtp (String email) {
        Status status = statusService.getStatus(StatusId.NOTVERIFIED);
        User user = userRepo.findByEmailAndStatusId(email, status);
        if (Objects.equals(user, null)){
            throw new EntryNotFoundException("user not exist");
        }

        Status active = statusService.getStatus(StatusId.ACTIVE);
        TempOtp otp = tempOtpRepo.findByEmailAndStatus(email, active);
        if (!Objects.equals(otp, null)){
            tempOtpRepo.delete(otp);
        }
        sendOtp(user.getEmail(), user);
        return "success";
    }
}
