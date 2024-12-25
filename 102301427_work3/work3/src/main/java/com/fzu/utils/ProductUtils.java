package com.fzu.utils;

import com.fzu.dao.ProductMapper;
import com.fzu.pojo.Product;
import org.apache.ibatis.session.SqlSession;

public class ProductUtils {
    //通过id获取商品信息
    public static Product getProductById(int id){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        ProductMapper mapper = sqlSession.getMapper(ProductMapper.class);

        Product product = mapper.selectById(id);
        return product;
    }
}
