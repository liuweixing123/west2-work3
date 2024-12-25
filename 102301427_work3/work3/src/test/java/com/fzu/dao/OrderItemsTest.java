package com.fzu.dao;

import com.fzu.pojo.OrderItems;
import com.fzu.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class OrderItemsTest {
    @Test
    //查询订单商品表的所有信息
    public void selectAll(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrderItemsMapper mapper = sqlSession.getMapper(OrderItemsMapper.class);

        for (OrderItems orderItems : mapper.selectAll()) {
            System.out.println(orderItems);
        }
        sqlSession.close();
    }

    //添加订单商品表
    @Test
    public void insertOrderItems(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrderItemsMapper mapper = sqlSession.getMapper(OrderItemsMapper.class);

        OrderItems orderItems = new OrderItems(3, 2, 3);
        int count = mapper.insertOrderItems(orderItems);

        if(count>0)
            System.out.println("添加成功");
        sqlSession.close();
    }

    //修改订单商品表
    @Test
    public void updateOrderItems(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrderItemsMapper mapper = sqlSession.getMapper(OrderItemsMapper.class);

        OrderItems orderItems = new OrderItems(3, 2, 4);
        int count = mapper.updateOrderItem(orderItems);

        if(count>0)
            System.out.println("修改成功");
        sqlSession.close();
    }

    //删除订单商品表
    @Test
    public void deleteOrderItems(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrderItemsMapper mapper = sqlSession.getMapper(OrderItemsMapper.class);

        int count = mapper.deleteOrderItem(3);

        if(count>0)
            System.out.println("修改成功");
        sqlSession.close();
    }
}
