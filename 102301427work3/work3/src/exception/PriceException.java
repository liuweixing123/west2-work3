package exception;

//价格异常
public class PriceException extends RuntimeException{
    public PriceException() {
        super();
    }

    public PriceException(String message) {
        super(message);
    }
}
