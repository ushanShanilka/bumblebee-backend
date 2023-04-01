package com.bumblebee.bumblebeebackend.service;

import com.bumblebee.bumblebeebackend.dto.ProductHasImageDTO;
import com.bumblebee.bumblebeebackend.dto.ProductHasImageUpdateDTO;
import com.bumblebee.bumblebeebackend.entity.Product;

import java.util.List;
import java.util.Map;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/1/2023
 **/
public interface ProductHasImagesService {
    String saveImages(List<ProductHasImageDTO> dto, Product product, String userName);
    String updateImages(List<ProductHasImageUpdateDTO> dto, String userName);
    String deleteImage(Long id, String userName);
    List<Map<String, Object>> getImagesByProductId(Long productId);

}
