package com.bumblebee.bumblebeebackend.util;

import com.bumblebee.bumblebeebackend.entity.Status;
import com.bumblebee.bumblebeebackend.repo.TempOtpRepo;
import com.bumblebee.bumblebeebackend.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/
@Service
public class OtpGenerate {

    @Autowired
    StatusService statusService;
    @Autowired
    TempOtpRepo tempOtpRepo;

    public int generateCode() {
        Status active = statusService.getStatus(StatusId.ACTIVE);

        int min = 10000;
        int max = 99999;
        int otp = (int)(Math.random()*(max-min+1)+min);

        boolean b = tempOtpRepo.existsByOtpAndStatus(BCrypt.hashpw(String.valueOf(otp), BCrypt.gensalt(10)), active);
        if (b){
            generateCode();
        }
        System.out.println(otp);
        return otp;

    }
}
