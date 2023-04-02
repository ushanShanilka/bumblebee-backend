package com.bumblebee.bumblebeebackend.service.impl;

import com.bumblebee.bumblebeebackend.dto.CategoryDTO;
import com.bumblebee.bumblebeebackend.entity.Category;
import com.bumblebee.bumblebeebackend.entity.Status;
import com.bumblebee.bumblebeebackend.exception.EntryDuplicateException;
import com.bumblebee.bumblebeebackend.repo.CategoryRepo;
import com.bumblebee.bumblebeebackend.service.CategoryService;
import com.bumblebee.bumblebeebackend.service.StatusService;
import com.bumblebee.bumblebeebackend.util.StatusId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/2/2023
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    StatusService statusService;

    @Override
    public String save (CategoryDTO dto, String userName, String type) {
        if (Objects.equals(type, "ADMIN") || Objects.equals(type, "SUPERADMIN")){
            Category category = categoryRepo.findCategoryByNameAndStatus(dto.getCategoryName(), 1);
            if (Objects.equals(category, null)){
                dto.setCategoryId(0L);
                categoryRepo.save(toCategory(dto, userName));
                return "success";
            }
            throw new EntryDuplicateException("Category Name Already Exist");
        }
        throw new BadCredentialsException("Invalid User");
    }

    @Override
    public String update (CategoryDTO dto, String userName, String type) {
        if (Objects.equals(type, "ADMIN") || Objects.equals(type, "SUPERADMIN")){
            Category category = categoryRepo.findCategoryById(dto.getCategoryId());
            if (!Objects.equals(category, null)){
                categoryRepo.save(toCategory(dto, userName));
                return "success";
            }
            throw new EntryDuplicateException("Category not Exist");
        }
        throw new BadCredentialsException("Invalid User");
    }

    @Override
    public Map<String, Object> get (Long id, String userName, String type) {
        Category category = null;
        if (Objects.equals(type, "ADMIN") || Objects.equals(type, "SUPERADMIN")){
            category = categoryRepo.findCategoryById(id);
        }else if (type.equals("USER")){
            category = categoryRepo.findCategoryByIdStatus(id, 1);
        }else {
            throw new BadCredentialsException("Invalid User");
        }
        Map<String, Object> obj = new HashMap<>();

        obj.put("categoryId",category.getId());
        obj.put("createAt",category.getCreatedAt());
        obj.put("updatedAt",category.getUpdatedAt());
        obj.put("categoryName",category.getCategoryName());
        obj.put("lastUpdatedBy",category.getLastUpdatedBy());
        obj.put("status",category.getStatusId().getId());

        return obj;
    }

    @Override
    public String delete (Long id, String userName, String type) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getAll (String value, String type) {
        List<Category> all = null;
        if (!Objects.equals(type, "USER") && Objects.equals(value, "all")){
            all = categoryRepo.findAllCategory();
        }else if (!Objects.equals(type, "USER") && Objects.equals(value, "active")){
            all = categoryRepo.findCategoryByStatusId(1);
        }else {
            throw new BadCredentialsException("Invalid User");
        }
        List<Map<String, Object>> map = new ArrayList<>();

        for (Category category:all) {
            Map<String, Object> obj = new HashMap<>();

            obj.put("categoryId",category.getId());
            obj.put("createAt",category.getCreatedAt());
            obj.put("updatedAt",category.getUpdatedAt());
            obj.put("categoryName",category.getCategoryName());
            obj.put("lastUpdatedBy",category.getLastUpdatedBy());
            obj.put("status",category.getStatusId().getId());

            map.add(obj);
        }

        return map;
    }


    private Category toCategory(CategoryDTO dto, String userName){

        Date date = new Date();
        Status status = null;

        if (dto.getStatus() == 1){
            status = statusService.getStatus(StatusId.ACTIVE);
        }else {
            status = statusService.getStatus(StatusId.DELETE);
        }

        Category category = new Category(
                dto.getCategoryId(),
                null,
                null,
                dto.getCategoryName(),
                userName,
                status
        );

        if (category.getId() > 0){
            category.setUpdatedAt(date);
        }else {
            category.setCreatedAt(date);
            category.setUpdatedAt(date);
        }
        return category;
    }
}
