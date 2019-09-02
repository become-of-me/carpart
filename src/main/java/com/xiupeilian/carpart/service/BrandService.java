package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.Brand;
import com.xiupeilian.carpart.model.Parts;

import java.util.List;

/**
 * @Description:
 * @Author: Hu MengLong
 * @CreateDate: 2019/8/26 15:25
 * @Version: 1.0
 **/
public interface BrandService {
    List<Brand> findBrandAll();

    List<Parts> findPartsAll();

    List<Parts> findPrimeAll();


    Brand findBrandById(String singleBrand);

    Parts findPartsById(String singleParts);

}
