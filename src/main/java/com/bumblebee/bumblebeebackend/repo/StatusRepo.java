package com.bumblebee.bumblebeebackend.repo;

import com.bumblebee.bumblebeebackend.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/14/2023
 **/

@Repository
public interface StatusRepo extends JpaRepository<Status,Long> {
    Status findById(short id);
    List<Status> findAll();
    void deleteById(short id);
}
