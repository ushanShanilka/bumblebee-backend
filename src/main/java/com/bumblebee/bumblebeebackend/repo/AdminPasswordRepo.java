package com.bumblebee.bumblebeebackend.repo;


import com.bumblebee.bumblebeebackend.entity.AdminLoginCredential;
import com.bumblebee.bumblebeebackend.entity.AdminPassword;
import com.bumblebee.bumblebeebackend.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/14/2023
 **/

@Repository
public interface AdminPasswordRepo extends JpaRepository<AdminPassword,Long> {
    AdminPassword findByAdminLoginCredentialIdAndStatusId(AdminLoginCredential adminLoginCredential, Status status);
    void deleteById(Long id);
}
