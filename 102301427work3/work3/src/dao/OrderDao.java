package dao;

import pojo.Order;
import pojo.Product;

import java.util.ArrayList;

public interface OrderDao {
    //查询所有订单
    ArrayList<Order> selectAll();

    //通过订单编号查询订单
    Order selectById(int id);

    //添加订单
    int insertOrder(Order order);

    //修改订单
    int updateOrder(Order order);

    //删除订单
    int deleteOrder(int id);

    //通过订单价格升序排序订单
    ArrayList<Order> selectAsc();

    //通过订单价格降序排序订单
    ArrayList<Order> selectDesc();

    //分页查询订单
    ArrayList<Order> selectLimit(int startIndex,int pageSize);

}
