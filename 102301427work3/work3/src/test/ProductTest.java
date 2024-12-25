package test;


import dao.ProductDaoImpl;
import org.junit.Test;
import pojo.Product;
import java.util.ArrayList;

public class ProductTest {

    //查询所有商品
    @Test
    public void selectAll(){
        ProductDaoImpl Impl =new ProductDaoImpl();
        ArrayList<Product> goods = Impl.selectAll();
        for (Product good : goods) {
            System.out.println(good);
        }
    }

    //通过商品编号查询商品
    @Test
    public void selectById(){
        ProductDaoImpl impl =new ProductDaoImpl();
        Product goods = impl.selectById(2);
        System.out.println(goods);
    }

    //添加商品
    @Test
    public void insertProduct(){
        Product goods=new Product(5,"oppofindx6",1000,0,10000);
        ProductDaoImpl impl = new ProductDaoImpl();
        int i = impl.insertProduct(goods);
        if(i>0)
            System.out.println("添加成功");
        else
            System.out.println("添加失败");
    }

    //修改商品
    @Test
    public void updateProduct(){
        Product goods=new Product(2,"vivoy93",1200,0,500);
        ProductDaoImpl impl=new ProductDaoImpl();

        int count=impl.updateProduct(goods);
        if(count>0)
            System.out.println("修改成功");
        else
            System.out.println("修改失败");
    }

    //删除商品
    @Test
    public void deleteProduct(){
        ProductDaoImpl impl =new ProductDaoImpl();
        int count = impl.deleteProduct(5);
        if(count>0){
            System.out.println("删除成功");
        }else
            System.out.println("删除失败");
    }

    //按照价格升序查找商品
    @Test
    public void selectAsc(){
        ProductDaoImpl impl=new ProductDaoImpl();
        for (Product product : impl.selectAsc()) {
            System.out.println(product);
        }
    }

    //按照价格降序
    @Test
    public void selectDesc(){
        ProductDaoImpl impl=new ProductDaoImpl();
        for (Product product : impl.selectDesc()) {
            System.out.println(product);
        }
    }

    //分页查询
    @Test
    public void selectLimit(){
        ProductDaoImpl impl=new ProductDaoImpl();
        for (Product product : impl.selectLimit(0, 2)) {
            System.out.println(product);
        }
    }
}
