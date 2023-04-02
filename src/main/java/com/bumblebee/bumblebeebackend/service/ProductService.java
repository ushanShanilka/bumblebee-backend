package com.bumblebee.bumblebeebackend.service;

import com.bumblebee.bumblebeebackend.dto.Paginated.PaginatedDTO;
import com.bumblebee.bumblebeebackend.dto.ProductDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/1/2023
 **/
public interface ProductService {
    String productSave(ProductDTO dto, String userName, String type);
    String productUpdate(ProductDTO dto, String userName, String type);
    Map<String, Object> getProduct(Long id, String userName, String type);
    String deleteProduct(Long id, String userName, String type);
    List<Map<String, Object>> getAllProduct(String value, String type);

}
