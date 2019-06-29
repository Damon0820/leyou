package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

    @Autowired
    BrandMapper brandMapper;

//    public PageResult<Brand> queryBrandByPageAndSort(
//            Integer page, Integer rows, String sortBy, Boolean desc, String key
//    ) {
//        // 开始分页
//        PageHelper.startPage(page, rows);
//        // 过滤
//
//        // 排序
//
//        // 查询
//
//    }
}
