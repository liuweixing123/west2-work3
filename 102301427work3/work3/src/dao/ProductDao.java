package dao;

import pojo.Product;

import java.util.ArrayList;

public interface ProductDao {
    //查询所有
    ArrayList<Product> selectAll();

    //通过商品编号查询
    Product selectById(int id);

    //添加商品
    int insertProduct(Product goods);

    //修改商品
    int updateProduct(Product goods);

    //删除商品
    int deleteProduct(int id);

    //按照价格从小到大排序商品
    ArrayList<Product> selectAsc();

    //按照价格从大到小排序商品
    ArrayList<Product> selectDesc();

    //分页查询
    ArrayList<Product> selectLimit(int startIndex,int pageSize);

    //查询商品编号商品(用于订单查询已不存在的商品，防止订单查询不到已被删除的商品)
    public Product selectById2(int id);
}
