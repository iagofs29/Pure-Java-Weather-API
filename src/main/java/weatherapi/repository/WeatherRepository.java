package weatherapi.repository;

import weatherapi.dto.Weather;
import weatherapi.dto.WeatherRequest;

public interface WeatherRepository {
    public Weather fetchWeatherByRequest(WeatherRequest request);
}
