package weatherapi.models;

import java.util.List;

public class WeatherResponse {
    private String address;
    private List<Weather> days;

    public WeatherResponse(){}

    public String getAddress(){
        return this.address;
    }

    public List<Weather> getDays(){
        return days;
    }
}
