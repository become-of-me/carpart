package com.xiupeilian.carpart.service.impl;

import com.xiupeilian.carpart.mapper.CompanyMapper;
import com.xiupeilian.carpart.model.Company;
import com.xiupeilian.carpart.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Hu MengLong
 * @CreateDate: 2019/8/26 15:13
 * @Version: 1.0
 **/
@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public Company findCompanyByCompanyName(String companyname) {
        return companyMapper.findCompanyByCompanyName(companyname);
    }
}
