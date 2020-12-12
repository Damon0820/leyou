package com.leyou.item.mapper;


import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface BrandMapper extends Mapper<Brand> {
    /**
     * 新增分类品牌中间表
     * @param cid
     * @param bid
     */
    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid}, #{bid})")
    public int insertCategoryBrand(@Param("cid") Long cid, @Param("bid") Long bid);
}
