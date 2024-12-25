package pojo;

public class Product {
    //商品编号
    private int id;
    //商品名称
    private String name;
    //商品价格
    private double price;
    //商品是否存在，0表示存在，1表示不存在
    private int isDeleted;
    //商品库存量
    private int stock;

    public Product() {
    }

    public Product(int id, String name, double price, int isDeleted, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isDeleted = isDeleted;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", isDeleted=" + isDeleted +
                ", stock=" + stock +
                '}';
    }
}
