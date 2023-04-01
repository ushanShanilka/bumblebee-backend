package com.bumblebee.bumblebeebackend.service.impl;

import com.bumblebee.bumblebeebackend.dto.Paginated.PaginatedDTO;
import com.bumblebee.bumblebeebackend.dto.ProductDTO;
import com.bumblebee.bumblebeebackend.entity.Product;
import com.bumblebee.bumblebeebackend.entity.Status;
import com.bumblebee.bumblebeebackend.exception.EntryDuplicateException;
import com.bumblebee.bumblebeebackend.exception.EntryNotFoundException;
import com.bumblebee.bumblebeebackend.repo.ProductRepo;
import com.bumblebee.bumblebeebackend.service.ProductHasImagesService;
import com.bumblebee.bumblebeebackend.service.ProductService;
import com.bumblebee.bumblebeebackend.service.StatusService;
import com.bumblebee.bumblebeebackend.service.StockService;
import com.bumblebee.bumblebeebackend.util.StatusId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    public String productSave (ProductDTO dto, String userName, String type) {
        if (!Objects.equals(type, "ADMIN") || !Objects.equals(type, "SUPERADMIN")){
            throw new BadCredentialsException("Invalid User");
        }else {
            Product product = productRepo.existsByProductName(dto.getProductName());
            if (Objects.equals(product, null)){
                dto.setId(0L);
                Product save = productRepo.save(toProduct(dto, userName));
                productHasImagesService.saveImages(dto.getProductHasImageDTOS(), save, userName);
                stockService.saveStock(dto.getQty(), save,userName);
                return "success";
            }
            throw new EntryDuplicateException("Product Name Already Exist");
        }
    }

    @Override
    public String productUpdate (ProductDTO dto, String userName, String type) {
        if (!Objects.equals(type, "ADMIN") || !Objects.equals(type, "SUPERADMIN")){
            throw new BadCredentialsException("Invalid User");
        }else {
            Product product = productRepo.existsById(dto.getId(), 1);
            if (!Objects.equals(product, null)){
                Product save = productRepo.save(toProduct(dto, userName));
                productHasImagesService.updateImages(dto.getProductHasImageDTOS(), save, userName);
                stockService.updateStock(dto.getQty(), save,userName);
                return "success";
            }
            throw new EntryNotFoundException("Product Not Exist");
        }
    }

    @Override
    public Map<String, Object> getProduct (Long id, String userName) {
        Product product = productRepo.existsById(id, 1);
        if (!Objects.equals(product, null)){
            Map<String, Object> obj = new HashMap<>();

            obj.put("id",product.getId());
            obj.put("createAt",product.getCreatedAt());
            obj.put("updatedAt",product.getUpdatedAt());
            obj.put("name",product.getProductName());
            obj.put("description",product.getDescription());
            obj.put("rating",product.getRating());
            obj.put("price",product.getPrice());
            obj.put("lastUpdatedBy",product.getLastUpdatedBy());
            obj.put("status",product.getStatusId().getStatus());

            List<Map<String, Object>> images = productHasImagesService.getImagesByProductId(product.getId());
            obj.put("imageList", images);

            Map<String, Object> stock = stockService.getStockByProduct(product.getId());
            obj.put("stock", stock);

            return obj;
        }
        throw new EntryNotFoundException("Product Not Exist");
    }

    @Override
    public String deleteProduct (Long id, String userName, String type) {
        if (!Objects.equals(type, "ADMIN") || !Objects.equals(type, "SUPERADMIN")){
            throw new BadCredentialsException("Invalid User");
        }else {
            Product product = productRepo.existsById(id, 1);
            if (!Objects.equals(product, null)){
                Date date = new Date();
                Status delete = statusService.getStatus(StatusId.DELETE);

                product.setStatusId(delete);
                product.setUpdatedAt(date);
                product.setLastUpdatedBy(userName);

                productRepo.save(product);
                return "success";
            }
            throw new EntryNotFoundException("Product Not Exist");
        }

    }

    @Override
    public PaginatedDTO getAllProduct (String value, String type, Pageable pageable) {
        Page<Product> all = null;
        List<Map<String, Object>> map = new ArrayList<>();

        if (Objects.equals(type, "ADMIN") || Objects.equals(type, "SUPERADMIN")){
            all = productRepo.getAllProductForAdmin(value, pageable);
        }else if (Objects.equals(type,"USER")){
            all = productRepo.getAllProduct(value, 1, pageable);
        }else {
            throw new BadCredentialsException("Invalid User");
        }
        for (Product p :all) {
            Map<String, Object> obj = new HashMap<>();

            obj.put("id",p.getId());
            obj.put("createAt",p.getCreatedAt());
            obj.put("updatedAt",p.getUpdatedAt());
            obj.put("name",p.getProductName());
            obj.put("description",p.getDescription());
            obj.put("rating",p.getRating());
            obj.put("price",p.getPrice());
            obj.put("lastUpdatedBy",p.getLastUpdatedBy());
            obj.put("status",p.getStatusId().getStatus());

            List<Map<String, Object>> images = productHasImagesService.getImagesByProductId(p.getId());
            obj.put("imageList", images);

            Map<String, Object> stock = stockService.getStockByProduct(p.getId());
            obj.put("stock", stock);

            map.add(obj);
        }
        return new PaginatedDTO(map, (long) map.size());

    }

    private Product toProduct(ProductDTO dto, String user){
        Date date = new Date();
        Status active = statusService.getStatus(StatusId.ACTIVE);

        return new Product(
                dto.getId(),
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
