package com.bumblebee.bumblebeebackend.service.impl;

import com.bumblebee.bumblebeebackend.dto.StockDTO;
import com.bumblebee.bumblebeebackend.dto.StockUpdateDTO;
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
    public String saveStock (double qty, Product product, String userName) {
        stockRepo.save(toStock(qty,product,userName));
        return "success";
    }

    @Override
    public String updateStock (StockUpdateDTO dto, String userName) {
        return null;
    }

    private Stock toStock(double qty, Product product, String userName){
        Date date = new Date();
        Status active = statusService.getStatus(StatusId.ACTIVE);

        return new Stock(
                0L,
                date,
                date,
                qty,
                product,
                userName,
                active
        );
    }
}
