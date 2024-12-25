package dao;

import exception.PriceException;
import pojo.Product;
import utils.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDaoImpl implements ProductDao {

    //查询所有商品
    @Override
    public ArrayList<Product> selectAll() {
        ArrayList<Product> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "select * from product where isdeleted =0 ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int isdeleted = rs.getInt("isdeleted");
                int stock=rs.getInt("stock");
                list.add(new Product(id, name, price, isdeleted,stock));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(conn, pst, rs);
            return list;
        }
    }

    //通过商品编号id查询商品
    @Override
    public Product selectById(int id) {
        Product goods = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtil.getConnection();
            String sql = "select * from product where id =? and isdeleted=0";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            //执行sql
            rs = pst.executeQuery();
            while (rs.next()) {
                int Id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int isdeleted = rs.getInt("isdeleted");
                int stock=rs.getInt("stock");
                goods=new Product(Id,name,price,isdeleted,stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            JdbcUtil.release(conn, pst, rs);
            return goods;
        }

    }

    //添加商品
    @Override
    public int insertProduct(Product goods) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = JdbcUtil.getConnection();
            //商品价格不合法，抛出异常
            if (goods.getPrice() <= 0)
                throw new PriceException("商品价格设置不合法");
            String sql = "insert into product (`id`,`name`,`price`,`isdeleted`,`stock`) values(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, goods.getId());
            pst.setString(2, goods.getName());
            pst.setDouble(3, goods.getPrice());
            pst.setInt(4, goods.isDeleted());
            pst.setInt(5,goods.getStock());

            //执行sql
            count = pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PriceException e) {
            System.out.println(e.getMessage());
        } finally {
            //释放资源
            JdbcUtil.release(conn, pst, rs);
            return count;
        }
    }

    //修改商品
    @Override
    public int updateProduct(Product goods) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = JdbcUtil.getConnection();
            //商品价格有误，抛出异常
            if (goods.getPrice() <= 0)
                throw new PriceException("商品价格不合法！！！");
            String sql = "update product set `name`=? ,price=? ,isdeleted =?,stock=? where id =?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, goods.getName());
            pst.setDouble(2, goods.getPrice());
            pst.setInt(3, goods.isDeleted());
            pst.setInt(5, goods.getId());
            pst.setInt(4,goods.getStock());
            //执行sql
            count = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PriceException e) {
            System.out.println(e.getMessage());
        } finally {
            //释放资源
            JdbcUtil.release(conn, pst, rs);
            return count;
        }
    }

    //删除商品
    @Override
    public int deleteProduct(int id) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count=0;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "update product set isdeleted =1 where id =? ";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            //执行sql
            count=pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            JdbcUtil.release(conn, pst, rs);
            return count;
        }
    }

    //按照价格从小到大排序商品
    @Override
    public ArrayList<Product> selectAsc() {
        ArrayList<Product> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "select * from product where isdeleted =0 order by price asc";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int isdeleted = rs.getInt("isdeleted");
                int stock=rs.getInt("stock");
                list.add(new Product(id, name, price, isdeleted,stock));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(conn, pst, rs);
            return list;
        }
    }

    //按照价格从大到小排序商品
    @Override
    public ArrayList<Product> selectDesc() {
        ArrayList<Product> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "select * from product where isdeleted =0 order by price desc";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int isdeleted = rs.getInt("isdeleted");
                int stock=rs.getInt("stock");
                list.add(new Product(id, name, price, isdeleted,stock));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(conn, pst, rs);
            return list;
        }
    }

    //分页查询
    @Override
    public ArrayList<Product> selectLimit(int startIndex, int pageSize) {
        ArrayList<Product> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "select * from product where isdeleted =0 limit ? ,?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,startIndex);
            pst.setInt(2,pageSize);
            //执行sql
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int isdeleted = rs.getInt("isdeleted");
                int stock=rs.getInt("stock");
                list.add(new Product(id, name, price, isdeleted,stock));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.release(conn, pst, rs);
            return list;
        }
    }

    //查询商品编号商品(用于订单查询已不存在的商品，防止订单查询不到已被删除的商品)
    @Override
    public Product selectById2(int id) {
        Product goods = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtil.getConnection();
            String sql = "select * from product where id =? ";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            //执行sql
            rs = pst.executeQuery();
            while (rs.next()) {
                int Id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int isdeleted = rs.getInt("isdeleted");
                int stock=rs.getInt("stock");
                goods=new Product(Id,name,price,isdeleted,stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            JdbcUtil.release(conn, pst, rs);
            return goods;
        }
    }
}
