package com.xiupeilian.carpart.service.impl;

import com.xiupeilian.carpart.mapper.PartsMapper;
import com.xiupeilian.carpart.model.Parts;
import com.xiupeilian.carpart.service.PartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartsServiceImpl implements PartsService {
    @Autowired
    private PartsMapper partsMapper;


}
