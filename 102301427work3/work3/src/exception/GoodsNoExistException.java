package exception;

//商品不存在异常
public class GoodsNoExistException extends RuntimeException{
    public GoodsNoExistException() {
        super();
    }

    public GoodsNoExistException(String message) {
        super(message);
    }
}
