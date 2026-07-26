package weatherapi.exceptions;

public class ApiConnectionException extends RuntimeException {

    public ApiConnectionException(String msg){
        super(msg);
    }

    public ApiConnectionException(String msg, Throwable cause){
        super(msg, cause);
    }
    
}
