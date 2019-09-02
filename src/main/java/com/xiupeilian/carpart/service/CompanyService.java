package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.Company;
import com.xiupeilian.carpart.vo.CompanyPictureVo;

/**
 * @Description:
 * @Author: Hu MengLong
 * @CreateDate: 2019/8/26 15:12
 * @Version: 1.0
 **/

public interface CompanyService {

    Company findCompanyByCompanyName(String companyname);

    Company findCompanyById(Integer companyId);
    void updateCompanyPictureById(CompanyPictureVo vo);

    void updateCompanyPictureById2(CompanyPictureVo vo);

    void updateCompanyPictureById3(CompanyPictureVo vo);

    void updateCompanyMemoById(Company company);

    void updateByPrimaryKeySelective(Company company);

    Company findCompanyByCompanyId(Integer companyId);
}
