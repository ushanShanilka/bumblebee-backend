package com.bumblebee.bumblebeebackend.repo;

import com.bumblebee.bumblebeebackend.entity.ProductHasImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/1/2023
 **/
@Repository
public interface ProductHasImagesRepo extends JpaRepository<ProductHasImages, Long> {
    @Query(value = "select * from product_has_images where product_id=?1 and concat(status_id) like %?2%", nativeQuery = true)
    List<ProductHasImages> getAllImagesByProductId(Long productId, int statusId);
}
