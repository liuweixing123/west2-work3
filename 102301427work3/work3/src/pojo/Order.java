package pojo;

import dao.ProductDaoImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class Order {
    //订单编号
    private int id;
    //用来存储商品信息,键：商品名  值：商品数量
    List<OrderItem> list;
    //下单时间
    private LocalDateTime dateTime;
    //订单价格
    private double price;

    public Order() {
    }

    public Order(int id, List<OrderItem> list, LocalDateTime dateTime, double price) {
        this.id = id;
        this.list = list;
        this.dateTime = dateTime;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OrderItem> getList() {
        return list;
    }

    public void setList(List<OrderItem> list) {
        this.list = list;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        ProductDaoImpl impl=new ProductDaoImpl();
        String productList="";
        for (OrderItem orderItem : list) {
            Product product = impl.selectById2(orderItem.getProductId());
            productList=productList+ product.getName()+"数量="+orderItem.getQuantity()+" ";
        }
        return "Order{" +
                "id=" + id +
                ", list= " + productList +
                ", dateTime=" + dateTime +
                ", price=" + price +
                '}';
    }
}
