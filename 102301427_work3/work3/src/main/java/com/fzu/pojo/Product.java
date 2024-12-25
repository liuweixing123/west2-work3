package com.fzu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    //商品id
    private int id;
    //商品名称
    private String name;
    //商品价格
    private double price;
    //商品是否存在
    private int isdeleted;
    //商品数量
    private int quantity;

}
