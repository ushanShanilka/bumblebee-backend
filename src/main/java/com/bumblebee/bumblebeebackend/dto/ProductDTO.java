package com.bumblebee.bumblebeebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/1/2023
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO implements Serializable {
    private Long id;
    private String productName;
    private String description;
    private double rating;
    private double price;
    private boolean status;
    private CategoryDTO categoryDTO;
    private BrandDTO brandsDTO;
    private StockDTO stock;
    private List<ProductHasImageDTO> productHasImageDTOS;
}
