package com.bumblebee.bumblebeebackend.repo;

import com.bumblebee.bumblebeebackend.entity.Brand;
import com.bumblebee.bumblebeebackend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/2/2023
 **/
@Repository
public interface BrandRepo extends JpaRepository<Brand, Long> {

    @Query(value = "select * from brand where brand_name=?1 and status_id=?2", nativeQuery = true)
    Brand findBrandyByNameAndStatus(String name, int status);

    @Query(value = "select * from brand where id=?1 and status_id=?2", nativeQuery = true)
    Brand findBrandByIdStatus(Long id, int status);

    @Query(value = "select * from brand where id=?1", nativeQuery = true)
    Brand findBrandById(Long id);

    @Query(value = "select * from brand where status_id=?1", nativeQuery = true)
    List<Brand> findBrandByStatusId(int id);

    @Query(value = "select * from brand", nativeQuery = true)
    List<Brand> findAllBrands();
}
