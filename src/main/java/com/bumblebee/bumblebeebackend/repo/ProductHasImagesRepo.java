package com.bumblebee.bumblebeebackend.repo;

import com.bumblebee.bumblebeebackend.entity.ProductHasImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/1/2023
 **/
@Repository
public interface ProductHasImagesRepo extends JpaRepository<ProductHasImages, Long> {
}
