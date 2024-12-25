package com.fzu.dao;

import com.fzu.pojo.Order;
import com.fzu.pojo.Product;
import com.fzu.utils.MybatisUtils;
import com.fzu.utils.OrderUtils;
import com.fzu.utils.ProductUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class OrderTest {
    //查询所有订单
    @Test
    public void selectAll(){
        for (Order order : OrderUtils.getAllOrder()) {
            System.out.println(order);
        }

    }

    //通过Id查询订单
    @Test
    public void selectByIdTest(){
        Order order = OrderUtils.getOrderById(1);
        System.out.println(order);
    }

    //添加订单
    @Test
    public void addOrderTest(){
        ArrayList<Product> list=new ArrayList<>();
        Product product1 = ProductUtils.getProductById(4);
        if(product1!=null){
            product1.setQuantity(1);
            list.add(product1);
        }

        Order order = new Order(3, new Date(), 5000.0, list);
        int count = OrderUtils.addOrder(order);
        if(count>0)
            System.out.println("添加订单成功");
    }

    //修改订单
    @Test
    public void updateOrderTest(){
        HashMap<String, Object> map = new HashMap<>();

        ArrayList<Product> products = new ArrayList<>();
        Product product = ProductUtils.getProductById(4);
        product.setQuantity(1);
        products.add(product);

        map.put("id",3);
        map.put("price",5100.0);
        map.put("productList",products);
        int count = OrderUtils.updateOrder(map);
        if(count>0)
            System.out.println("修改订单成功");
        else
            System.out.println("修改订单失败");
    }

    //删除订单
    @Test
    public void delete(){
        int count=OrderUtils.deleteOrder(3);
        if(count>0)
            System.out.println("订单删除成功");
        else
            System.out.println("订单删除失败");
    }

    //按照订单价格降序排序
    @Test
    public void PriceDesc(){
        for (Order order : OrderUtils.PriceDesc()) {
            System.out.println(order);
        }
    }

    //按照订单价格降序排序
    @Test
    public void PriceAsc(){
        for (Order order : OrderUtils.PriceAsc()) {
            System.out.println(order);
        }
    }

    //查看最近的订单
    @Test
    public void DateDesc(){
        for (Order order : OrderUtils.DateDesc()) {
            System.out.println(order);
        }
    }

    //查看最早的订单
    @Test
    public void DateASc(){
        for (Order order : OrderUtils.DateAsc()) {
            System.out.println(order);
        }
    }
}


