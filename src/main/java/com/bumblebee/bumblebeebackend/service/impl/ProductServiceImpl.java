package com.bumblebee.bumblebeebackend.service.impl;

import com.bumblebee.bumblebeebackend.dto.ProductDTO;
import com.bumblebee.bumblebeebackend.dto.ProductUpdateDTO;
import com.bumblebee.bumblebeebackend.entity.Product;
import com.bumblebee.bumblebeebackend.entity.Status;
import com.bumblebee.bumblebeebackend.exception.EntryDuplicateException;
import com.bumblebee.bumblebeebackend.repo.ProductHasImagesRepo;
import com.bumblebee.bumblebeebackend.repo.ProductRepo;
import com.bumblebee.bumblebeebackend.repo.StockRepo;
import com.bumblebee.bumblebeebackend.service.ProductHasImagesService;
import com.bumblebee.bumblebeebackend.service.ProductService;
import com.bumblebee.bumblebeebackend.service.StatusService;
import com.bumblebee.bumblebeebackend.service.StockService;
import com.bumblebee.bumblebeebackend.util.StatusId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/1/2023
 **/
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepo productRepo;
    @Autowired
    ProductHasImagesService productHasImagesService;
    @Autowired
    StockService stockService;
    @Autowired
    StatusService statusService;

    @Transactional
    @Modifying
    @Override
    public String productSave (ProductDTO dto, String userName) {
        Product product = productRepo.existsByProductName(dto.getProductName());
        if (Objects.equals(product, null)){
            Product save = productRepo.save(toProduct(dto, userName));
            productHasImagesService.saveImages(dto.getProductHasImageDTOS(), save, userName);
            stockService.saveStock(dto.getQty(), save,userName);
            return "success";
        }
        throw new EntryDuplicateException("Product Name Already Exist");
    }

    @Override
    public String productUpdate (ProductUpdateDTO dto, String userName) {
        return null;
    }

    @Override
    public Map<String, Object> getProduct (Long id, String userName) {
        return null;
    }

    @Override
    public String deleteProduct (Long id, String userName) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getAllProduct (String value) {
        return null;
    }

    private Product toProduct(ProductDTO dto, String user){
        Date date = new Date();
        Status active = statusService.getStatus(StatusId.ACTIVE);

        return new Product(
                0L,
                date,
                date,
                dto.getProductName(),
                dto.getDescription(),
                dto.getRating(),
                dto.getPrice(),
                user,
                active
        );
    }
}
