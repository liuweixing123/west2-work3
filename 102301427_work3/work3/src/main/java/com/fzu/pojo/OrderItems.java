package com.fzu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("OrderItems")
//订单商品表的实例
public class OrderItems {
    private int orderId;
    private int productId;
    private int quantity;

}
