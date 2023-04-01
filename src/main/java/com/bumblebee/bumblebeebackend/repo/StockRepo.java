package com.bumblebee.bumblebeebackend.repo;

import com.bumblebee.bumblebeebackend.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/1/2023
 **/
@Repository
public interface StockRepo extends JpaRepository<Stock, Long> {
    @Query(value = "select * from stock where product_id=?1 and concat(status_id) like %?2%", nativeQuery = true)
    Stock getStockByproduct(Long productId, int statusId);
}
