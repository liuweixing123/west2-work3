package com.fzu.exception;

import com.fzu.pojo.Product;

//商品不存在异常
public class ProductNotExistException extends RuntimeException{
    public ProductNotExistException(){}

    public ProductNotExistException(String msg){
        super(msg);
    }
}
