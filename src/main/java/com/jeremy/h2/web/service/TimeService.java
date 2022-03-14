package com.jeremy.h2.web.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TimeService {

    @Cacheable(cacheNames = "getTime")
    public Date getTime() {
        return new Date();
    }

    @Cacheable("currentTimeMillis")
    public Long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
}
