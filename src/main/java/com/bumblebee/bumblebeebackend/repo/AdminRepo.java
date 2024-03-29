package com.bumblebee.bumblebeebackend.repo;

import com.bumblebee.bumblebeebackend.entity.Admin;
import com.bumblebee.bumblebeebackend.entity.AdminType;
import com.bumblebee.bumblebeebackend.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/14/2023
 **/

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {
    List<Admin> findAll ();
    boolean existsByIdAndStatusId (Long id, Status statusId);
    Admin findByEmailAndStatusId(String email, Status status);
    Admin findByEmailAndAdminTypeIdAndStatusId (String email, AdminType adminTypeId, Status statusId);
    boolean existsByEmailAndStatusId (String email, Status statusId);

    @Query(value = "select * from admin where id=?1", nativeQuery = true)
    Admin getAdmin(Long id);
}
