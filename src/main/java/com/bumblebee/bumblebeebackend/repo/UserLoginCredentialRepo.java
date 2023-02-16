package com.bumblebee.bumblebeebackend.repo;

import com.bumblebee.bumblebeebackend.entity.Status;
import com.bumblebee.bumblebeebackend.entity.User;
import com.bumblebee.bumblebeebackend.entity.UserLoginCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/
@Repository
public interface UserLoginCredentialRepo extends JpaRepository<UserLoginCredential, Long> {
    UserLoginCredential findByUserNameAndStatusId (String userName, Status statusId);
    UserLoginCredential findByUserIdAndStatusId (User userId, Status statusId);
}
