package dao;

import pojo.OrderItem;
import utils.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDaoImpl implements OrderItemDao {
    //通过订单id查询订单商品
    @Override
    public List<OrderItem> selectById(int id) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<OrderItem> list = new ArrayList<>();
        try {
            conn = JdbcUtil.getConnection();
            String sql = "select goods_id,quantity from order_Items where order_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);

            rs = pst.executeQuery();
            while (rs.next()) {
                OrderItem orderItem = new OrderItem(id, rs.getInt("goods_id"), rs.getInt("quantity"));
                list.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(conn, pst, rs);
            return list;
        }
    }

    //插入订单商品表
    @Override
    public int insert(OrderItem orderItem) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;

        try {
            conn = JdbcUtil.getConnection();
            String sql = "insert into order_items (order_id,goods_id,quantity) values(?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, orderItem.getOrderId());
            pst.setInt(2, orderItem.getProductId());
            pst.setInt(3, orderItem.getQuantity());
            //执行sql
            count = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(conn, pst, rs);
            return count;
        }
    }

    //修改订单商品表
    @Override
    public int update(List<OrderItem> list) {
        int count=0;
        for (OrderItem orderItem : list) {
            if(count==0)
                delete(orderItem.getOrderId());
            insert(orderItem);
            count++;
        }
        return count;
    }

    //通过订单id删除订单商品表
    public int delete(int orderId) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "delete from order_items where order_id = ?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1, orderId);
            //执行sql
            count= pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(conn, pst, rs);
            return count;
        }
    }
}
