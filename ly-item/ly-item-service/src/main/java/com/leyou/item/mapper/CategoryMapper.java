package com.leyou.item.mapper;


import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;

//@Resource
//@Component
//@Repository
public interface CategoryMapper extends Mapper<Category> {
    @Select("SELECT * FROM tb_category WHERE id IN (SELECT category_id FROM tb_category_brand WHERE brand_id = #{bid}) ")
    public List<Category> queryCategoryByBrandId(Long bid);
}
