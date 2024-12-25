package com.fzu.dao;

import com.fzu.pojo.OrderItems;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface OrderItemsMapper {
    //查询订单商品表
    @Select("SELECT order_id,product_id,quantity FROM order_items ")
    List<OrderItems> selectAll();

    //添加订单商品信息
    @Insert("insert into order_items (order_id,product_id,quantity) values(#{orderId},#{productId},#{quantity})")
    int insertOrderItems(OrderItems oi);

    //修改订单商品信息
    @Update("update order_items set quantity=#{quantity} where order_id=#{orderId} and product_id=#{productId}")
    int updateOrderItem(OrderItems oi);


    //删除订单商品表
    @Delete("delete  from order_items where order_id=#{orderId}")
    int deleteOrderItem(@Param("orderId") int orderId);
}
