package weatherapi.exceptions;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(String msg){
        super(msg);
    }
}
