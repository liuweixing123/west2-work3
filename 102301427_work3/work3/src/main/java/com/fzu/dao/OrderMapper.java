package com.fzu.dao;

import com.fzu.pojo.Order;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
    //查询所有商品订单
    List<Order> selectAll();

    //通过Id查询商品订单
    Order selectById(int id);

    //添加商品订单
    @Insert("insert `order` (id,datetime,price) values(#{id},#{datetime},#{price})")
    int insertOrder(Order order);

    //修改商品订单
    int updateOrder(Map<String,Object>map);

    //删除商品订单
    @Delete("delete  from `order` where id=#{id}")
    int deleteOrder(int id);

    //按照订单价格降序排序
    List<Order> selectByPriceDesc();

    //按照订单价格升序排序
    List<Order> selectByPriceAsc();

    //查看最近时间的订单
    List<Order> selectByDateDesc();

    //查看最早时间的订单
    List<Order> selectByDateAsc();
}
