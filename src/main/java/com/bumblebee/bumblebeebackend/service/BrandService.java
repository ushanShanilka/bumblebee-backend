package com.bumblebee.bumblebeebackend.service;

import com.bumblebee.bumblebeebackend.dto.BrandDTO;

import java.util.List;
import java.util.Map;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/2/2023
 **/
public interface BrandService {
    String save(BrandDTO dto, String userName, String type);
    String update(BrandDTO dto, String userName, String type);
    Map<String, Object> get(Long id, String userName, String type);
    String delete(Long id, String userName, String type);
    List<Map<String, Object>> getAll(String value, String type);
}
