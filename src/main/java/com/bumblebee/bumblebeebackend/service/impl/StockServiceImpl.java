package com.bumblebee.bumblebeebackend.service.impl;

import com.bumblebee.bumblebeebackend.dto.StockDTO;
import com.bumblebee.bumblebeebackend.entity.Product;
import com.bumblebee.bumblebeebackend.entity.Status;
import com.bumblebee.bumblebeebackend.entity.Stock;
import com.bumblebee.bumblebeebackend.repo.StockRepo;
import com.bumblebee.bumblebeebackend.service.StatusService;
import com.bumblebee.bumblebeebackend.service.StockService;
import com.bumblebee.bumblebeebackend.util.StatusId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/1/2023
 **/
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    StockRepo stockRepo;
    @Autowired
    StatusService statusService;

    @Override
    public String saveStock (StockDTO dto, Product product, String userName) {
        stockRepo.save(toStock(dto,product,userName));
        return "success";
    }

    @Override
    public String updateStock (StockDTO dto, Product product, String userName) {
        stockRepo.save(toStock(dto,product,userName));
        return "success";
    }

    @Override
    public Map<String, Object> getStockByProduct (Long productId) {
        Stock stock = stockRepo.getStockByproduct(productId, 1);
        Map<String, Object> obj = new HashMap<>();
        if (!Objects.equals(stock, null)){
            obj.put("id", stock.getId());
            obj.put("qty", stock.getQty());
        }
        return obj;
    }

    private Stock toStock(StockDTO dto, Product product, String userName){
        Date date = new Date();
        Status active = statusService.getStatus(StatusId.ACTIVE);

        Stock stock = new Stock(
                dto.getId(),
                null,
                null,
                dto.getQty(),
                product,
                userName,
                active
        );

        if (stock.getId() > 0){
            stock.setUpdatedAt(date);
        }else {
            stock.setCreatedAt(date);
            stock.setUpdatedAt(date);
        }

        return stock;
    }
}
