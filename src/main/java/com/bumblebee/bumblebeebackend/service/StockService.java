package com.bumblebee.bumblebeebackend.service;

import com.bumblebee.bumblebeebackend.dto.StockDTO;
import com.bumblebee.bumblebeebackend.dto.StockUpdateDTO;
import com.bumblebee.bumblebeebackend.entity.Product;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/1/2023
 **/
public interface StockService {
    String saveStock(double qty, Product product, String userName);
    String updateStock(StockUpdateDTO dto, String userName);
}
