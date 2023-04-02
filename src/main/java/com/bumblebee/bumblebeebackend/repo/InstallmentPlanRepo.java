package com.bumblebee.bumblebeebackend.repo;

import com.bumblebee.bumblebeebackend.entity.InstallmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/3/2023
 **/
@Repository
public interface InstallmentPlanRepo extends JpaRepository<InstallmentPlan, Long> {

    @Query(value = "select * from installment_plan where id=?1 and status_id=?2", nativeQuery = true)
    InstallmentPlan getPlanByIdAndStatus(Long id, int status);
}
