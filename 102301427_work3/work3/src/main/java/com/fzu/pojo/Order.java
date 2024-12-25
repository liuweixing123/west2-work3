package com.fzu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("Order")
public class Order {
    //订单id
    private int id;
    //订单时间
    private Date datetime;
    //订单价格
    private Double price;
    //商品信息
    private List<Product> productList;
}
