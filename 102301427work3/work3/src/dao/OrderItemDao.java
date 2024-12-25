package dao;

import pojo.OrderItem;

import java.util.List;

public interface OrderItemDao {
    //通过订单id查询订单商品表
    List<OrderItem> selectById(int id);

    //插入订单商品表
    int insert(OrderItem orderItem);

    //修改订单商品表
    int update(List<OrderItem> list);

    //通过订单id删除订单商品表
    int delete(int orderId);
}
