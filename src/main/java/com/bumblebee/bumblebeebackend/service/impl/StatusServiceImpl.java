package com.bumblebee.bumblebeebackend.service.impl;

import com.bumblebee.bumblebeebackend.entity.Status;
import com.bumblebee.bumblebeebackend.repo.StatusRepo;
import com.bumblebee.bumblebeebackend.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 4/1/2023
 **/
@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    StatusRepo statusRepo;

    @Override
    public Status getStatus (short id) {
        return statusRepo.findById(id);
    }
}
