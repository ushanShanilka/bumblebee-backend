package com.bumblebee.bumblebeebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/1/2023
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StockUpdateDTO implements Serializable {
    private Long id;
    private Long productId;
    private double qty;
}
