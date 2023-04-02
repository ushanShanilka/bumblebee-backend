package com.bumblebee.bumblebeebackend.service.impl;

import com.bumblebee.bumblebeebackend.dto.BrandDTO;
import com.bumblebee.bumblebeebackend.entity.Brand;
import com.bumblebee.bumblebeebackend.entity.Status;
import com.bumblebee.bumblebeebackend.exception.EntryDuplicateException;
import com.bumblebee.bumblebeebackend.repo.BrandRepo;
import com.bumblebee.bumblebeebackend.service.BrandService;
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
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandRepo brandRepo;
    @Autowired
    StatusService statusService;

    @Override
    public String save (BrandDTO dto, String userName, String type) {
        System.out.println(dto);
        if (Objects.equals(type, "ADMIN") || Objects.equals(type, "SUPERADMIN")){
            Brand brand = brandRepo.findBrandyByNameAndStatus(dto.getBrandName(), 1);
            if (Objects.equals(brand, null)){
                dto.setBrandId(0L);
                brandRepo.save(toBrand(dto, userName));
                return "success";
            }
            throw new EntryDuplicateException("Brand Name Already Exist");
        }
        throw new BadCredentialsException("Invalid User");
    }

    @Override
    public String update (BrandDTO dto, String userName, String type) {
        if (Objects.equals(type, "ADMIN") || Objects.equals(type, "SUPERADMIN")){
            Brand brand = brandRepo.findBrandById(dto.getBrandId());
            if (!Objects.equals(brand, null)){
                brandRepo.save(toBrand(dto, userName));
                return "success";
            }
            throw new EntryDuplicateException("Brand not Exist");
        }
        throw new BadCredentialsException("Invalid User");
    }

    @Override
    public Map<String, Object> get (Long id, String userName, String type) {
        Brand brand = null;
        if (Objects.equals(type, "ADMIN") || Objects.equals(type, "SUPERADMIN")){
            brand = brandRepo.findBrandById(id);
        }else if (type.equals("USER")){
            brand = brandRepo.findBrandByIdStatus(id, 1);
        }else {
            throw new BadCredentialsException("Invalid User");
        }
        Map<String, Object> obj = new HashMap<>();

        obj.put("brandId",brand.getId());
        obj.put("createAt",brand.getCreatedAt());
        obj.put("updatedAt",brand.getUpdatedAt());
        obj.put("brandName",brand.getBrandName());
        obj.put("lastUpdatedBy",brand.getLastUpdatedBy());
        obj.put("status",brand.getStatusId().getId());

        return obj;
    }

    @Override
    public String delete (Long id, String userName, String type) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getAll (String value, String type) {
        List<Brand> all = null;
        if (!Objects.equals(type, "USER") && Objects.equals(value, "all")){
            all = brandRepo.findAllBrands();
        }else if (!Objects.equals(type, "USER") && value.equals("active")){
            all = brandRepo.findBrandByStatusId(1);
        }else {
            throw new BadCredentialsException("Invalid User");
        }
        List<Map<String, Object>> map = new ArrayList<>();

        for (Brand brand:all) {
            Map<String, Object> obj = new HashMap<>();

            obj.put("brandId",brand.getId());
            obj.put("createAt",brand.getCreatedAt());
            obj.put("updatedAt",brand.getUpdatedAt());
            obj.put("brandName",brand.getBrandName());
            obj.put("lastUpdatedBy",brand.getLastUpdatedBy());
            obj.put("status",brand.getStatusId().getId());

            map.add(obj);
        }

        return map;
    }


    private Brand toBrand(BrandDTO dto, String userName){

        Date date = new Date();
        Status status = null;

        if (dto.getStatus() == 1){
            status = statusService.getStatus(StatusId.ACTIVE);
        }else {
            status = statusService.getStatus(StatusId.DELETE);
        }

        Brand brand = new Brand(
                dto.getBrandId(),
                null,
                null,
                dto.getBrandName(),
                userName,
                status
        );

        if (brand.getId() > 0){
            brand.setUpdatedAt(date);
        }else {
            brand.setCreatedAt(date);
            brand.setUpdatedAt(date);
        }
        return brand;
    }
}
