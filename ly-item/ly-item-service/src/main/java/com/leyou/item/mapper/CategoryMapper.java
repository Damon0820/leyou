package com.leyou.item.mapper;


import com.leyou.item.pojo.Category;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;

@Resource
@Component
public interface CategoryMapper extends Mapper<Category> {

}
