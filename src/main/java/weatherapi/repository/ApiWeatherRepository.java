package weatherapi.repository;

import weatherapi.models.Weather;
import com.google.gson.Gson;

public class ApiWeatherRepository implements WeatherRepository{

    private final Gson gson = new Gson();

   
    public Weather fetchWeatherByCity(String city){
        
        

        return null;
    }
}
