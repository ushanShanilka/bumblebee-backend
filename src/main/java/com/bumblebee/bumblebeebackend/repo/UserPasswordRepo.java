package com.bumblebee.bumblebeebackend.repo;

import com.bumblebee.bumblebeebackend.entity.Status;
import com.bumblebee.bumblebeebackend.entity.UserLoginCredential;
import com.bumblebee.bumblebeebackend.entity.UserPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/
@Repository
public interface UserPasswordRepo extends JpaRepository<UserPassword, Long> {
    UserPassword findByUserLoginCredentialIdAndStatusId (UserLoginCredential userLoginCredentialId, Status statusId);

}
