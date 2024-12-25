package com.fzu.utils;

import com.fzu.dao.OrderItemsMapper;
import com.fzu.dao.OrderMapper;
import com.fzu.exception.NoProductException;
import com.fzu.exception.PriceNotSuitableException;
import com.fzu.exception.ProductNotExistException;
import com.fzu.pojo.Order;
import com.fzu.pojo.OrderItems;
import com.fzu.pojo.Product;
import org.apache.ibatis.session.SqlSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

//订单工具类
public class OrderUtils {
    //获取所有订单信息
    public static List<Order> getAllOrder() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);

        //执行sql
        List<Order> orders = mapper.selectAll();

        //关闭资源
        sqlSession.close();

        return orders;
    }

    //通过订单Id获取订单信息
    public static Order getOrderById(int id) {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        //执行sql
        Order order = mapper.selectById(id);

        //关闭资源
        sqlSession.close();

        return order;
    }

    //添加订单
    public static int addOrder(Order order) {
        SqlSession sqlSession = null;
        int count = 1;
        try {
            sqlSession = MybatisUtils.getSqlSession();
            OrderMapper ordermapper = sqlSession.getMapper(OrderMapper.class);
            OrderItemsMapper orderItemsMapper = sqlSession.getMapper(OrderItemsMapper.class);

            //添加订单表信息
            count = ordermapper.insertOrder(order);
            if(order.getProductList().size()==0){
                throw new NoProductException("订单中不存在任何商品！！！");
            }

            //添加订单商品表信息
            for (int i = 0; i < order.getProductList().size(); i++) {
                //将订单中每一个商品的信息封装到订单商品表中
                Product product = order.getProductList().get(i);

                //若商品不存在则抛出异常
                if(ProductUtils.getProductById(product.getId())==null){
                    throw new ProductNotExistException("商品不存在！！！");
                }
                //商品价格不合理则抛出异常
                if(product.getPrice()<0){
                    throw new PriceNotSuitableException("商品价格不合理！！！");
                }

                OrderItems orderItems = new OrderItems(order.getId(), product.getId(), product.getQuantity());
                orderItemsMapper.insertOrderItems(orderItems);
            }

            //提交事务
            sqlSession.commit();
        } catch (ProductNotExistException e) {
            //回滚事务
            sqlSession.rollback();
            count=0;
            e.printStackTrace();
        }catch(PriceNotSuitableException e){
            sqlSession.rollback();
            count=0;
            e.printStackTrace();
        } catch (NoProductException e) {
            sqlSession.rollback();
            count=0;
            e.printStackTrace();
        } finally {
            //关闭资源
            sqlSession.close();
        }
        return count;
    }

    //修改订单
    public static int updateOrder(Map<String,Object> map){
        SqlSession sqlSession=MybatisUtils.getSqlSession();
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        OrderItemsMapper OIMapper = sqlSession.getMapper(OrderItemsMapper.class);
        int count=0;
        try{
            if(map.containsKey("price")&&(double)map.get("price")<0){
                throw new PriceNotSuitableException("价格不合理！！！");
            }
            if(map.containsKey("productList")&&((List)map.get("productList")).size()==0){
                throw new NoProductException("不能让订单中不存在商品");
            }
            count=mapper.updateOrder(map);
            if(map.containsKey("productList")){
                OIMapper.deleteOrderItem((int)map.get("id"));
                List productList = (List<Product>)map.get("productList");
                for (int i = 0; i < productList.size(); i++) {
                    OIMapper.insertOrderItems(new OrderItems((int)map.get("id"),((Product)productList.get(i)).getId(),1));
                }
            }
            sqlSession.commit();
        }catch(PriceNotSuitableException e){
            count=0;
            sqlSession.rollback();
            e.printStackTrace();
        }catch(NoProductException e){
            count=0;
            sqlSession.rollback();
            e.printStackTrace();
        }finally{
            sqlSession.close();
        }
        return count;
    }

    //删除订单
    public static int deleteOrder(int id){
        int count=0;
        SqlSession sqlSession=MybatisUtils.getSqlSession();
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        OrderItemsMapper OIMapper = sqlSession.getMapper(OrderItemsMapper.class);

        count=mapper.deleteOrder(id);
        OIMapper.deleteOrderItem(id);
        sqlSession.commit();
        return count;
    }

    //按照订单价格降序排序
    public static List<Order> PriceDesc(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);

        return mapper.selectByPriceDesc();
    }

    //按照订单价格升序排序
    public static List<Order> PriceAsc(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);

        return mapper.selectByPriceAsc();
    }

    //查看最近的订单
    public static List<Order> DateDesc(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);

        return mapper.selectByDateDesc();
    }

    //查看最早的订单
    public static List<Order> DateAsc(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);

        return mapper.selectByDateAsc();
    }
}
