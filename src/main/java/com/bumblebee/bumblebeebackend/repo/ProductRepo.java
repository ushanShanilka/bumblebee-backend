package com.bumblebee.bumblebeebackend.repo;

import com.bumblebee.bumblebeebackend.entity.Product;
import com.bumblebee.bumblebeebackend.entity.Status;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/1/2023
 **/
@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query(value = "select * from product where product_name=?1 and status_id=1", nativeQuery = true)
    Product existsByProductName(String name);

    @Query(value = "select * from product where id=?1 and status_id=?2", nativeQuery = true)
    Product existsByIdAndStatus (Long id, int statusId);

    @Query(value = "select * from product where id=?1", nativeQuery = true)
    Product existsByID (Long id);

    @Query(value = "select * from product where concat(product_name, rating, description, price) like %?1% and status_id=?2", nativeQuery = true)
    List<Product> getAllProduct(String value, int statusId);

    @Query(value = "select * from product where concat(product_name, rating, description, price) like %?1%", nativeQuery = true)
    List<Product> getAllProductForAdmin(String value);
}
