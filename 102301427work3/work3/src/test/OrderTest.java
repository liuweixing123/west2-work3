package test;

import dao.OrderDaoImpl;
import org.junit.Test;
import pojo.Order;
import pojo.OrderItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderTest {
    //查询所有订单
    @Test
    public void selectAll(){
        OrderDaoImpl impl=new OrderDaoImpl();
        for (Order order : impl.selectAll()) {
            System.out.println(order);
        }
    }

    //通过id查询订单
    @Test
    public void selectById(){
        OrderDaoImpl impl =new OrderDaoImpl();
        Order order = impl.selectById(3);
        System.out.println(order);
    }

    //添加订单
    @Test
    public void insertOrder(){
        //创建订单
        List<OrderItem> list=new ArrayList<>();
        list.add(new OrderItem(3,4,2));
        list.add(new OrderItem(3,3,1));
        Order order=new Order(3,list,LocalDateTime.now(),9000);

        //添加订单
        OrderDaoImpl impl=new OrderDaoImpl();
        int count=impl.insertOrder(order);
        if(count>0)
            System.out.println("插入订单成功");
        else
            System.out.println("插入订单失败");
    }

    //修改订单商品表
    @Test
    public void updateOrder(){
        //创建订单
        List<OrderItem> list=new ArrayList<>();
        list.add(new OrderItem(3,4,1));
        list.add(new OrderItem(3,3,2));
        Order order=new Order(3,list,LocalDateTime.now(),19000);

        //修改订单
        OrderDaoImpl impl=new OrderDaoImpl();
        int count = impl.updateOrder(order);
        if(count>0)
            System.out.println("订单修改成功");
    }

    //删除订单
    @Test
    public void deleteOrder(){
        OrderDaoImpl impl=new OrderDaoImpl();
        int count= impl.deleteOrder(3);
        if(count>0)
            System.out.println("订单删除成功");
        else
            System.out.println("订单删除失败");
    }

    //根据价格升序排序订单
    @Test
    public void selectAsc(){
        OrderDaoImpl impl =new OrderDaoImpl();
        for (Order order : impl.selectAsc()) {
            System.out.println(order);
        }
    }

    //根据价格降序排序订单
    @Test
    public void selectDesc(){
        OrderDaoImpl impl =new OrderDaoImpl();
        for (Order order : impl.selectDesc()) {
            System.out.println(order);
        }
    }

    //分页查询
    @Test
    public void selectLimit(){
        OrderDaoImpl impl=new OrderDaoImpl();
        for (Order order : impl.selectLimit(1, 1)) {
            System.out.println(order);
        }
    }
}
