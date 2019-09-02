package com.xiupeilian.carpart.service.impl;

import com.xiupeilian.carpart.mapper.CompanyMapper;
import com.xiupeilian.carpart.model.Company;
import com.xiupeilian.carpart.service.CompanyService;
import com.xiupeilian.carpart.vo.CompanyPictureVo;
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
    @Override
    public Company findCompanyByCompanyId(Integer id) {
        return companyMapper.findCompanyByCompanyId(id);
    }

    @Override
    public Company findCompanyById(Integer companyId) {
        return companyMapper.findCompanyById(companyId);
    }

    @Override
    public void updateCompanyPictureById(CompanyPictureVo vo) {
        companyMapper.updateCompanyPictureById(vo);
    }

    @Override
    public void updateCompanyPictureById2(CompanyPictureVo vo) {
        companyMapper.updateCompanyPictureById2(vo);
    }

    @Override
    public void updateCompanyPictureById3(CompanyPictureVo vo) {
        companyMapper.updateCompanyPictureById3(vo);
    }

    @Override
    public void updateCompanyMemoById(Company company) {
        companyMapper.updateCompanyMemoById(company);
    }

    @Override
    public void updateByPrimaryKeySelective(Company company) {
        companyMapper.updateByPrimaryKeySelective(company);
    }

}
