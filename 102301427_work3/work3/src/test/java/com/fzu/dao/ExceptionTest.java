package com.fzu.dao;

import com.fzu.pojo.Order;
import com.fzu.pojo.Product;
import com.fzu.utils.OrderUtils;
import com.fzu.utils.ProductUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

//异常测试
public class ExceptionTest {
    //添加订单中商品不存在情况
    @Test
    public void ProductNotExistTest(){
        ArrayList<Product> list=new ArrayList<>();
        //该商品在商品表中不存在
        Product product = new Product(6, "不存在的商品", 100, 0, 3);
        list.add(product);
        Order order = new Order(100, new Date(), 2450.0, list);
        int count = OrderUtils.addOrder(order);
        if(count>0)
            System.out.println("添加订单成功！！！");
        else
            System.out.println("添加订单失败！！！");
    }

    //添加订单中商品价格不合理情况
    @Test
    public void PriceNotSuitableExceptionTest(){
        ArrayList<Product> list=new ArrayList<>();
        //该商品在商品表中不存在
        Product product = new Product(1, "价格不合理的商品", -100, 0, 3);
        list.add(product);
        Order order = new Order(100, new Date(), 2450.0, list);
        int count = OrderUtils.addOrder(order);
        if(count>0)
            System.out.println("添加订单成功！！！");
        else
            System.out.println("添加订单失败！！！");
    }

    //添加订单中无商品情况
    @Test
    public void NoProductException(){
        ArrayList<Product> list=new ArrayList<>();
        Order order = new Order(100, new Date(), 2450.0, list);
        int count = OrderUtils.addOrder(order);
        if(count==0)
            System.out.println("订单中无任何商品存在！！！");
        else
            System.out.println("订单中有商品存在！！！");
    }
}
