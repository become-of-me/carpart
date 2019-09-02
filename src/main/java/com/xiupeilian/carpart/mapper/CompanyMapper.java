package com.xiupeilian.carpart.mapper;

import com.xiupeilian.carpart.base.BaseMapper;
import com.xiupeilian.carpart.model.Company;
import com.xiupeilian.carpart.vo.CompanyPictureVo;

public interface CompanyMapper extends BaseMapper<Company> {

    Company findCompanyByCompanyName(String companyname);

    Company findCompanyById(Integer companyId);
    Company findCompanyByCompanyId(Integer id);
    void updateCompanyPictureById(CompanyPictureVo vo);

    void updateCompanyPictureById2(CompanyPictureVo vo);

    void updateCompanyPictureById3(CompanyPictureVo vo);

    void updateCompanyMemoById(Company company);
}