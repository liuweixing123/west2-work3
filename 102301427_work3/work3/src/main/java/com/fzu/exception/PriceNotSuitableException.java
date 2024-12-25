package com.fzu.exception;

//价格不合法异常
public class PriceNotSuitableException extends RuntimeException{
    public PriceNotSuitableException() {
    }

    public PriceNotSuitableException(String message) {
        super(message);
    }
}
