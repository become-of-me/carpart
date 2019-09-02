package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.City;

import java.util.List;

/**
 * @Description:
 * @Author: Hu MengLong
 * @CreateDate: 2019/8/26 16:23
 * @Version: 1.0
 **/

public interface CityService {
    List<City> findCitiesByParentId(Integer parentId);
  void  deleteCityById(int id);

    List<City> findCities();
}
