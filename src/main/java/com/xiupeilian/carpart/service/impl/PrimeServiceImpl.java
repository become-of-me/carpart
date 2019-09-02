package com.xiupeilian.carpart.service.impl;

import com.xiupeilian.carpart.mapper.PrimeMapper;
import com.xiupeilian.carpart.model.Prime;
import com.xiupeilian.carpart.service.PrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrimeServiceImpl implements PrimeService {
    @Autowired
    private PrimeMapper primeMapper;
    @Override
    public Prime finPrimeById(int primeid) {
        return primeMapper.finPrimeById(primeid);
    }
}
