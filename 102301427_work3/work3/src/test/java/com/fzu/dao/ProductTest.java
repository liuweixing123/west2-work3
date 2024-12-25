package com.fzu.dao;

import com.fzu.pojo.Product;
import com.fzu.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class ProductTest {

    @Test
    //查询所有存在的商品
    public void selectAll(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        ProductMapper mapper = sqlSession.getMapper(ProductMapper.class);

        //执行sql
        for (Product product : mapper.selectAll()) {
            System.out.println(product);
        }

        sqlSession.close();
    }

    @Test
    //通过id查询商品
    public void selectById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        ProductMapper mapper = sqlSession.getMapper(ProductMapper.class);

        Product product = mapper.selectById(2);

        System.out.println(product);

        sqlSession.close();
    }

    @Test
    //添加商品
    public void insertProduct(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ProductMapper mapper = sqlSession.getMapper(ProductMapper.class);

        int count = mapper.insertProduct(new Product(6, "小米充电器", 50, 0, 100));

        if(count>0)
            System.out.println("添加成功");
        else
            System.out.println("添加失败");

        sqlSession.close();
    }

    @Test
    //修改商品
    public void updateProduct(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ProductMapper mapper = sqlSession.getMapper(ProductMapper.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",1);
        map.put("name","手机防窥膜");
        map.put("price",10);

        int count = mapper.updateProduct(map);

        if(count>0)
            System.out.println("修改成功");
        else
            System.out.println("修改失败");

        sqlSession.close();
    }

    @Test
    //删除商品
    public void deleteProduct(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ProductMapper mapper = sqlSession.getMapper(ProductMapper.class);

        //执行sql
        int count = mapper.deleteProduct(6);
        if(count>0)
            System.out.println("删除成功");
        else
            System.out.println("删除失败");

        sqlSession.close();

    }

    @Test
    //按照价格降序排序
    public void selectPriceByDesc(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ProductMapper mapper = sqlSession.getMapper(ProductMapper.class);

        for (Product product : mapper.selectByPriceDesc()) {
            System.out.println(product);
        }
    }

    @Test
    //按照价格升序排序
    public void selectPriceByASC(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ProductMapper mapper = sqlSession.getMapper(ProductMapper.class);

        for (Product product : mapper.selectByPriceAsc()) {
            System.out.println(product);
        }
    }
}
