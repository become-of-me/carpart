package com.xiupeilian.carpart.service.impl;

import com.xiupeilian.carpart.mapper.CityMapper;
import com.xiupeilian.carpart.model.City;
import com.xiupeilian.carpart.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: Hu MengLong
 * @CreateDate: 2019/8/26 16:24
 * @Version: 1.0
 **/
@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityMapper cityMapper;
    @Override
    @Cacheable(value = "canglaoshi")
    public List<City> findCitiesByParentId(Integer parentId) {
        return cityMapper.findCitiesByParentId(parentId);
    }
    @Override
    @CacheEvict(value="canglaoshi",allEntries = true)
    public void deleteCityById(int id) {
        cityMapper.deleteByPrimaryKey(id);
    }


    @Override
    public List<City> findCities() {
        return cityMapper.findCities();
    }

}
