package com.bumblebee.bumblebeebackend.repo;

import com.bumblebee.bumblebeebackend.entity.Status;
import com.bumblebee.bumblebeebackend.entity.TempOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/1/2023
 **/
@Repository
public interface TempOtpRepo extends JpaRepository<TempOtp, Long> {
    TempOtp findByEmailAndStatus (String email, Status status);
    boolean existsByOtpAndStatus (String otp, Status status);
    TempOtp findByEmailAndOtpAndStatus (String email, String otp, Status status);
}
