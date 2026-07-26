package weatherapi.repository;

import weatherapi.models.Weather;

public interface WeatherRepository {
    public Weather fetchWeatherByCity(String city);
}
