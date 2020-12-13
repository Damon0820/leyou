package com.leyou.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.util.StringUtil;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    BrandMapper brandMapper;

    public PageResult<Brand> queryBrandByPageAndSort(
            Integer page, Integer rows, String sortBy, Boolean desc, String key
    ) {
        // 开始分页
        PageHelper.startPage(page, rows);
        // 过滤
        Example example = new Example(Brand.class);
        if (StringUtil.isNotEmpty(key)) {
            example.createCriteria().andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }
        // 排序
        if (StringUtil.isNotEmpty(sortBy)) {
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        // 查询
        Page<Brand> pageInfo = (Page<Brand>) brandMapper.selectByExample(example);
        return new PageResult<Brand>(pageInfo.getTotal(), pageInfo);
    }

    public void insertCategoryBrand(String name, Character letter, List<Long> cids) {
//        return void;
        Brand brand = new Brand();
        brand.setName(name);
        brand.setLetter(letter);
        brandMapper.insertSelective(brand);
        Brand brand1 = brandMapper.selectOne(brand);
        for (Long cid:
             cids) {
            brandMapper.insertCategoryBrand(cid, brand.getId());
        }
    }

    public int updateBrand(String id, String name, Character letter) {
        Brand brand = new Brand();
        brand.setName(name);
        brand.setLetter(letter);
        return brandMapper.updateBrand(id, name, letter);
    }

    public void deleteBrand(Long bid) {
        brandMapper.deleteBrand(bid);
        brandMapper.deleteCategoryBrand(bid);
    }
};
