package com.fzu.dao;

import com.fzu.pojo.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface ProductMapper {
    //查询所有商品
    @Select("SELECT id,`name`,price,isdeleted,quantity FROM product WHERE isdeleted=0")
    List<Product> selectAll();

    //通过id查询商品
    @Select("SELECT id,`name`,price,isdeleted,quantity FROM product WHERE isdeleted=0 AND id=#{UID}")
    Product selectById(@Param("UID") int id);

    //添加商品
    @Insert("insert into product (`id`,`name`,`price`,`isdeleted`,`quantity`) values(#{id},#{name},#{price},#{isdeleted},#{quantity})")
    int insertProduct(Product product);

    //修改商品
    int updateProduct(Map<String,Object> map);

    //删除商品
    @Update("update product set isdeleted =1 where id=#{Id}" )
    int deleteProduct(@Param("Id") int id);

    //按照价格降序排序
    @Select("SELECT id,`name`,price,isdeleted,quantity FROM product WHERE isdeleted=0 ORDER BY price DESC")
    List<Product> selectByPriceDesc();

    //按照价格升序排序
    @Select("SELECT id,`name`,price,isdeleted,quantity FROM product WHERE isdeleted=0 ORDER BY price asc")
    List<Product> selectByPriceAsc();

}
