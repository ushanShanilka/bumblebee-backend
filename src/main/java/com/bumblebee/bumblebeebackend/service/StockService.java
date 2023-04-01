package com.bumblebee.bumblebeebackend.service;

import com.bumblebee.bumblebeebackend.dto.StockDTO;
import com.bumblebee.bumblebeebackend.entity.Product;

import java.util.Map;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/1/2023
 **/
public interface StockService {
    String saveStock(StockDTO dto, Product product, String userName);
    String updateStock(StockDTO dto, Product product, String userName);
    Map<String, Object> getStockByProduct(Long productId);
}
