package dao;

import exception.GoodsNoExistException;
import exception.PriceException;
import pojo.OrderItem;
import pojo.Product;
import pojo.Order;
import utils.JdbcUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    //查询所有订单
    @Override
    public ArrayList<Order> selectAll() {
        ArrayList<Order> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn=JdbcUtil.getConnection();
            String sql="select * from `order`";
            pst=conn.prepareStatement(sql);
            //执行sql
            rs=pst.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");
                LocalDateTime dateTime=(LocalDateTime) rs.getObject("datetime");
                double price=rs.getDouble("price");
                //查询订单的商品信息
                OrderItemDaoImpl impl=new OrderItemDaoImpl();
                List<OrderItem> orderItems;
                orderItems = impl.selectById(id);
                list.add(new Order(id,orderItems,dateTime,price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(conn,pst,rs);
            return list;
        }
    }

    //通过订单编号查找订单
    @Override
    public Order selectById(int id) {
        Order order =null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn=JdbcUtil.getConnection();
            String sql="select * from `order` where id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            //执行sql
            rs=pst.executeQuery();
            while(rs.next()){
                int Id=rs.getInt("id");
                LocalDateTime dateTime=(LocalDateTime) rs.getObject("datetime");
                double price=rs.getDouble("price");
                OrderItemDaoImpl impl=new OrderItemDaoImpl();
                List<OrderItem> orderItems;
                orderItems = impl.selectById(id);
                order=new Order(Id,orderItems,dateTime,price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(conn,pst,rs);
            return order;
        }
    }

    //添加订单
    @Override
    public int insertOrder(Order order) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count =0;

        try {
            conn=JdbcUtil.getConnection();
            //判断订单价格是否合法
            if(order.getPrice()<=0)
                throw new PriceException("订单价格不合法！！！");
            //判断插入的订单中是否包含不存在的商品
            for (OrderItem orderItem : order.getList()) {
                ProductDaoImpl impl=new ProductDaoImpl();
                Product product = impl.selectById(orderItem.getProductId());
                if(product==null)
                    throw new GoodsNoExistException("插入订单中商品不存在！！！");
            }
            String sql="insert into `order` (`id`,`datetime`,`price`) values(?,?,?) ";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,order.getId());
            pst.setObject(2,order.getDateTime());
            pst.setDouble(3,order.getPrice());
            count=pst.executeUpdate();
            //插入订单商品表
            OrderItemDaoImpl impl=new OrderItemDaoImpl();
            for (OrderItem orderItem : order.getList()) {
                impl.insert(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PriceException e){
            System.out.println(e.getMessage());
        }finally {
            JdbcUtil.release(conn,pst,rs);
            return count;
        }
    }

    //修改订单
    @Override
    public int updateOrder(Order order) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count =0;
        try {
            //判断订单价格是否合法
            if(order.getPrice()<=0)
                throw new PriceException("订单价格不合法！！！");
            //判断插入的订单中是否包含不存在的商品
            for (OrderItem orderItem : order.getList()) {
                ProductDaoImpl impl=new ProductDaoImpl();
                Product product = impl.selectById(orderItem.getProductId());
                if(product==null)
                    throw new GoodsNoExistException("插入订单中商品不存在！！！");
            }
            conn=JdbcUtil.getConnection();
            String sql="update `order` set datetime=?,price=? where `id` =?";
            pst=conn.prepareStatement(sql);
            pst.setObject(1,order.getDateTime());
            pst.setDouble(2,order.getPrice());
            pst.setInt(3,order.getId());
            //执行sql,修改订单表
            count=pst.executeUpdate();
            //修改订单商品表
            OrderItemDaoImpl impl=new OrderItemDaoImpl();
            impl.update(order.getList());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(conn,pst,rs);
            return count;
        }
    }

    //删除订单
    @Override
    public int deleteOrder(int id) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count=0;

        try {
            conn = JdbcUtil.getConnection();
            String sql="delete from `order` where id = ?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,id);

            //执行sql,删除订单
            count = pst.executeUpdate();

            //删除订单商品表中的订单
            OrderItemDaoImpl impl=new OrderItemDaoImpl();
            impl.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(conn,pst,rs);
            return count;
        }
    }

    //通过订单价格升序排序
    @Override
    public ArrayList<Order> selectAsc() {
        ArrayList<Order> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn=JdbcUtil.getConnection();
            String sql="select * from `order` order by price asc ";
            pst=conn.prepareStatement(sql);
            //执行sql
            rs=pst.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");
                LocalDateTime dateTime=(LocalDateTime) rs.getObject("datetime");
                double price=rs.getDouble("price");
                //查询订单的商品信息
                OrderItemDaoImpl impl=new OrderItemDaoImpl();
                List<OrderItem> orderItems;
                orderItems = impl.selectById(id);
                list.add(new Order(id,orderItems,dateTime,price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(conn,pst,rs);
            return list;
        }
    }

    //通过订单价格降序排序
    @Override
    public ArrayList<Order> selectDesc() {
        ArrayList<Order> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn=JdbcUtil.getConnection();
            String sql="select * from `order` order by price desc ";
            pst=conn.prepareStatement(sql);
            //执行sql
            rs=pst.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");
                LocalDateTime dateTime=(LocalDateTime) rs.getObject("datetime");
                double price=rs.getDouble("price");
                //查询订单的商品信息
                OrderItemDaoImpl impl=new OrderItemDaoImpl();
                List<OrderItem> orderItems;
                orderItems = impl.selectById(id);
                list.add(new Order(id,orderItems,dateTime,price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(conn,pst,rs);
            return list;
        }
    }

    //分页查询
    @Override
    public ArrayList<Order> selectLimit(int startIndex, int pageSize) {
        ArrayList<Order> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn=JdbcUtil.getConnection();
            String sql="select * from `order` limit ? , ? ";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,startIndex);
            pst.setInt(2,pageSize);
            //执行sql
            rs=pst.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");
                LocalDateTime dateTime=(LocalDateTime) rs.getObject("datetime");
                double price=rs.getDouble("price");
                //查询订单的商品信息
                OrderItemDaoImpl impl=new OrderItemDaoImpl();
                List<OrderItem> orderItems;
                orderItems = impl.selectById(id);
                list.add(new Order(id,orderItems,dateTime,price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(conn,pst,rs);
            return list;
        }
    }
}
