package weatherapi.services;

import weatherapi.dto.Weather;
import weatherapi.dto.WeatherRequest;
import weatherapi.dto.WeatherResponse;
import weatherapi.repository.WeatherRepository;

public class WeatherService {
    private final WeatherRepository repository;

    public WeatherService(WeatherRepository repository){
        this.repository = repository;
    }

    public WeatherResponse getCurrentWeather(WeatherRequest request){
        return repository.fetchCurrentWeather(request);
    }

    //public Weather getForecast(WeatherRequest request){}
}
