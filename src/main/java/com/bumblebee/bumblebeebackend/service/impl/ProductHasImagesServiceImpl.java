package com.bumblebee.bumblebeebackend.service.impl;

import com.bumblebee.bumblebeebackend.dto.ProductHasImageDTO;
import com.bumblebee.bumblebeebackend.entity.Product;
import com.bumblebee.bumblebeebackend.entity.ProductHasImages;
import com.bumblebee.bumblebeebackend.entity.Status;
import com.bumblebee.bumblebeebackend.entity.User;
import com.bumblebee.bumblebeebackend.repo.ProductHasImagesRepo;
import com.bumblebee.bumblebeebackend.service.ProductHasImagesService;
import com.bumblebee.bumblebeebackend.service.StatusService;
import com.bumblebee.bumblebeebackend.util.StatusId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/1/2023
 **/
@Service
public class ProductHasImagesServiceImpl implements ProductHasImagesService {

    @Autowired
    ProductHasImagesRepo productHasImagesRepo;
    @Autowired
    StatusService statusService;

    @Override
    public String saveImages (List<ProductHasImageDTO> dtos, Product product, String userName) {
        productHasImagesRepo.saveAll(toProductHasImagesList(dtos, product, userName));
        return "success";
    }

    @Override
    public String updateImages (List<ProductHasImageDTO> dto, Product product, String userName) {
        return null;
    }

    @Override
    public String deleteImage (Long id, String userName) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getImagesByProductId (Long productId) {
        List<ProductHasImages> all = productHasImagesRepo.getAllImagesByProductId(productId, 1);
        List<Map<String, Object>> map = new ArrayList<>();

        for (ProductHasImages i :all) {
            Map<String, Object> obj = new HashMap<>();
            obj.put("id",i.getId());
            obj.put("url",i.getUrl());
            map.add(obj);
        }
        return map;
    }

    private ProductHasImages toProductHasImages(ProductHasImageDTO dto, Product product, String user){
        Date date = new Date();
        Status active = statusService.getStatus(StatusId.ACTIVE);

        return new ProductHasImages(
                dto.getId(),
                date,
                date,
                dto.getUrl(),
                user,
                product,
                active
        );
    }

    private List<ProductHasImages> toProductHasImagesList(List<ProductHasImageDTO> dtos, Product product, String userName){
        ArrayList<ProductHasImages> list = new ArrayList<>();
        for (ProductHasImageDTO i :dtos) {
            list.add(toProductHasImages(i, product, userName));
        }
        return list;
    }
}
