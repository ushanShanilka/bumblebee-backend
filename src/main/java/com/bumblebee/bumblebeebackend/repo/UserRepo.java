package com.bumblebee.bumblebeebackend.repo;

import com.bumblebee.bumblebeebackend.entity.Status;
import com.bumblebee.bumblebeebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByNicNoAndStatusId (String nicNo, Status statusId);
    boolean existsByEmailAndStatusId (String email, Status statusId);
    boolean existsByIdAndStatusId (Long id, Status statusId);
}
