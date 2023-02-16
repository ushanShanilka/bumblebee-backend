package com.bumblebee.bumblebeebackend.repo;

import com.bumblebee.bumblebeebackend.entity.AdminType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/14/2023
 **/

@Repository
public interface AdminTypeRepo extends JpaRepository<AdminType,Long> {
    AdminType findById(Short id);
}
