package weatherapi.services;

import weatherapi.repository.WeatherRepository;
import weatherapi.models.Weather;

public class WeatherService {
    private final WeatherRepository repository;

    public WeatherService(WeatherRepository repository){
        this.repository = repository;
    }

    public Weather getWeatherByCity(String city){
        return repository.fetchWeatherByCity(city);
    }
}
