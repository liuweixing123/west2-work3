package com.fzu.exception;

//订单中无商品异常
public class NoProductException extends Exception{
    public NoProductException() {
    }

    public NoProductException(String message) {
        super(message);
    }
}
