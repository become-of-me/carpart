package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.Company;

/**
 * @Description:
 * @Author: Hu MengLong
 * @CreateDate: 2019/8/26 15:12
 * @Version: 1.0
 **/

public interface CompanyService {

    Company findCompanyByCompanyName(String companyname);
}
