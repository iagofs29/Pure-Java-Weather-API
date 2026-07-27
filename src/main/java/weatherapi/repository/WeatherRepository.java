package weatherapi.repository;

import weatherapi.dto.WeatherRequest;
import weatherapi.dto.WeatherResponse;

public interface WeatherRepository {
    public WeatherResponse fetchCurrentWeather(WeatherRequest request);
    public WeatherResponse fetchForecast(WeatherRequest request);
}
