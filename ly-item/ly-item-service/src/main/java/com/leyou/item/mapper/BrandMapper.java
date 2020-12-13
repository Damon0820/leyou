package com.leyou.item.mapper;


import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface BrandMapper extends Mapper<Brand> {
    /**
     * 新增分类品牌中间表
     * @param cid
     * @param bid
     */
    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid}, #{bid})")
    public int insertCategoryBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    /**
     * 更新品牌信息
     */
    @Update("UPDATE tb_brand SET `name`=#{name}, letter=#{letter} WHERE id = #{id}")
    public int updateBrand( @Param("id") String id, @Param("name") String name, @Param("letter") Character letter);

    /**
     * 删除商品信息
     */
    @Delete("DELETE FROM tb_brand WHERE id = #{bid}")
    public int deleteBrand(@Param("bid") Long bid);

    /**
     * 删除分类品牌中间表
     */
    @Delete("DELETE FROM tb_category_brand WHERE brand_id = #{bid}")
    public int deleteCategoryBrand(@Param("bid") Long bid);
}
