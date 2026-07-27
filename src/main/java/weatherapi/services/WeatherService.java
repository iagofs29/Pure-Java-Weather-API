package weatherapi.services;

import weatherapi.dto.Weather;
import weatherapi.dto.WeatherRequest;
import weatherapi.repository.WeatherRepository;

public class WeatherService {
    private final WeatherRepository repository;

    public WeatherService(WeatherRepository repository){
        this.repository = repository;
    }

    public Weather getWeatherByRequest(WeatherRequest request){
        return repository.fetchWeatherByRequest(request);
    }
}
