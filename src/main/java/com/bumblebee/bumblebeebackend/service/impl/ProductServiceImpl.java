package com.bumblebee.bumblebeebackend.service.impl;

import com.bumblebee.bumblebeebackend.dto.BrandDTO;
import com.bumblebee.bumblebeebackend.dto.CategoryDTO;
import com.bumblebee.bumblebeebackend.dto.ProductDTO;
import com.bumblebee.bumblebeebackend.entity.Brand;
import com.bumblebee.bumblebeebackend.entity.Category;
import com.bumblebee.bumblebeebackend.entity.Product;
import com.bumblebee.bumblebeebackend.entity.Status;
import com.bumblebee.bumblebeebackend.exception.EntryDuplicateException;
import com.bumblebee.bumblebeebackend.exception.EntryNotFoundException;
import com.bumblebee.bumblebeebackend.repo.BrandRepo;
import com.bumblebee.bumblebeebackend.repo.CategoryRepo;
import com.bumblebee.bumblebeebackend.repo.ProductRepo;
import com.bumblebee.bumblebeebackend.service.ProductHasImagesService;
import com.bumblebee.bumblebeebackend.service.ProductService;
import com.bumblebee.bumblebeebackend.service.StatusService;
import com.bumblebee.bumblebeebackend.service.StockService;
import com.bumblebee.bumblebeebackend.util.StatusId;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    BrandRepo brandRepo;

    @Transactional
    @Modifying
    @Override
    public String productSave (ProductDTO dto, String userName, String type) {
        if (Objects.equals(type, "ADMIN") || Objects.equals(type, "SUPERADMIN")){
            Product product = productRepo.existsByProductName(dto.getProductName());
            if (Objects.equals(product, null)){
                dto.setId(0L);
                Product save = productRepo.save(toProduct(dto, userName));
                productHasImagesService.saveImages(dto.getProductHasImageDTOS(), save, userName);
                stockService.saveStock(dto.getStock(), save,userName);
                return "success";
            }
            throw new EntryDuplicateException("Product Name Already Exist");
        }
        throw new BadCredentialsException("Invalid User");
    }

    @Override
    public String productUpdate (ProductDTO dto, String userName, String type) {
        if (Objects.equals(type, "ADMIN") || Objects.equals(type, "SUPERADMIN")){
            Product product = productRepo.existsByID(dto.getId());
            if (!Objects.equals(product, null)){
                Product save = productRepo.save(toProduct(dto, userName));
                productHasImagesService.updateImages(dto.getProductHasImageDTOS(), save, userName);
                stockService.updateStock(dto.getStock(), save,userName);
                return "success";
            }
            throw new EntryNotFoundException("Product Not Exist");
        }
        throw new BadCredentialsException("Invalid User");
    }

    @Override
    public Map<String, Object> getProduct (Long id, String userName, String type) {
        Product product = null;
        if (Objects.equals(type, "ADMIN") || Objects.equals(type, "SUPERADMIN")){
            product = productRepo.existsByID(id);
        }else if (Objects.equals(type, "USER")){
            product = productRepo.existsByIdAndStatus(id, 1);
        }
        if (!Objects.equals(product, null)){
            Map<String, Object> obj = new HashMap<>();

            obj.put("id",product.getId());
            obj.put("createAt",product.getCreatedAt());
            obj.put("updatedAt",product.getUpdatedAt());
            obj.put("productName",product.getProductName());
            obj.put("description",product.getDescription());
            obj.put("rating",product.getRating());
            obj.put("price",product.getPrice());
            obj.put("lastUpdatedBy",product.getLastUpdatedBy());
            obj.put("brandsDTO",new BrandDTO(product.getBrandId().getId(), product.getBrandId().getBrandName(), product.getBrandId().getStatusId().getId()));
            obj.put("categoryDTO",new CategoryDTO(product.getCategoryId().getId(), product.getCategoryId().getCategoryName(), product.getCategoryId().getStatusId().getId()));
            obj.put("status",product.getStatusId().getId());

            List<Map<String, Object>> images = productHasImagesService.getImagesByProductId(product.getId());
            obj.put("productHasImageDTOS", images);

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
            Product product = productRepo.existsByIdAndStatus(id, 1);
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
    public List<Map<String, Object>> getAllProduct (String value, String type) {
        List<Product> all = null;
        List<Map<String, Object>> map = new ArrayList<>();

        if (Objects.equals(type, "ADMIN") || Objects.equals(type, "SUPERADMIN")){
            all = productRepo.getAllProductForAdmin(value);
        }else if (Objects.equals(type,"USER")){
            all = productRepo.getAllProduct(value, 1);
        }else {
            throw new BadCredentialsException("Invalid User");
        }
        for (Product p :all) {
            Map<String, Object> obj = new HashMap<>();

            obj.put("id",p.getId());
            obj.put("createAt",p.getCreatedAt());
            obj.put("updatedAt",p.getUpdatedAt());
            obj.put("productName",p.getProductName());
            obj.put("description",p.getDescription());
            obj.put("rating",p.getRating());
            obj.put("price",p.getPrice());
            obj.put("lastUpdatedBy",p.getLastUpdatedBy());
            obj.put("brandsDTO",new BrandDTO(p.getBrandId().getId(), p.getBrandId().getBrandName(), p.getBrandId().getStatusId().getId()));
            obj.put("categoryDTO",new CategoryDTO(p.getCategoryId().getId(), p.getCategoryId().getCategoryName(), p.getCategoryId().getStatusId().getId()));
            obj.put("status",p.getStatusId().getId());

            List<Map<String, Object>> images = productHasImagesService.getImagesByProductId(p.getId());
            obj.put("productHasImageDTOS", images);

            Map<String, Object> stock = stockService.getStockByProduct(p.getId());
            obj.put("stock", stock);

            map.add(obj);
        }
        return map;
    }

    private Product toProduct(ProductDTO dto, String user){
        Date date = new Date();
        Status status = null;

        if (dto.isStatus()){
            status = statusService.getStatus(StatusId.ACTIVE);
        }else {
            status = statusService.getStatus(StatusId.DELETE);
        }

        System.out.println(dto);

        Category category = categoryRepo.findCategoryById(dto.getCategoryDTO().getCategoryId());
        if (Objects.equals(category, null)){
            throw new EntryNotFoundException("Category not exist");
        }

        Brand brand = brandRepo.findBrandById(dto.getBrandsDTO().getBrandId());
        if (Objects.equals(brand, null)){
            throw new EntryNotFoundException("Brand not exist");
        }

        Product product = new Product(
                dto.getId(),
                null,
                null,
                dto.getProductName(),
                dto.getDescription(),
                dto.getRating(),
                dto.getPrice(),
                user,
                brand,
                category,
                status
        );

        if (product.getId() > 0){
            product.setUpdatedAt(date);
        }else {
            product.setCreatedAt(date);
            product.setUpdatedAt(date);
        }
        return product;
    }
}
