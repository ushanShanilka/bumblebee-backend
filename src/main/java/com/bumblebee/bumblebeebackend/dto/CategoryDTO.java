package com.bumblebee.bumblebeebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/2/2023
 **/

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDTO implements Serializable {
    private Long categoryId;
    private String categoryName;
    private int status;
}
