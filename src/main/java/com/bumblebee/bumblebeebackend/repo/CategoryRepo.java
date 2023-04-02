package com.bumblebee.bumblebeebackend.repo;

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
public interface CategoryRepo extends JpaRepository<Category, Long> {

    @Query(value = "select * from category where category_name=?1 and status_id=?2", nativeQuery = true)
    Category findCategoryByNameAndStatus(String name, int status);

    @Query(value = "select * from category where id=?1 and status_id=?2", nativeQuery = true)
    Category findCategoryByIdStatus(Long id, int status);

    @Query(value = "select * from category where id=?1", nativeQuery = true)
    Category findCategoryById(Long id);

    @Query(value = "select * from category where status_id=?1", nativeQuery = true)
    List<Category> findCategoryByStatusId(int id);

    @Query(value = "select * from category", nativeQuery = true)
    List<Category> findAllCategory();
}
