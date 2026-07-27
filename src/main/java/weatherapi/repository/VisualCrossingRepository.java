package weatherapi.repository;

import weatherapi.dto.Weather;
import weatherapi.dto.WeatherRequest;
import weatherapi.dto.WeatherResponse;
import weatherapi.exceptions.ApiConnectionException;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;

public class VisualCrossingRepository implements WeatherRepository{

    private final Gson gson = new Gson();
    private static final String API_KEY = System.getenv("WEATHER_API_KEY");

    public VisualCrossingRepository(){}
   
    public Weather fetchWeatherByRequest(WeatherRequest request){

        // 1. Build HTTP request and get response.

        HttpClient httpClient = HttpClient.newHttpClient();

        if (API_KEY == null || API_KEY.isBlank()) {
            throw new IllegalStateException("Environment variable 'WEATHER_API_KEY' is not defined.");
        }

        URI url = URI.create("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + this.encodeCity(request.city()) + "?unitGroup=metric&include=current&key=" + API_KEY + "&contentType=json");

        HttpRequest getRequest = HttpRequest.newBuilder(url).GET().build();

        try {
            HttpResponse<String> response = httpClient.send(getRequest, BodyHandlers.ofString());

            int statusCode = response.statusCode();

            if(statusCode >= 200 && statusCode < 300){
                
                WeatherResponse weatherResponse = gson.fromJson(response.body(), WeatherResponse.class);
                return weatherResponse.getDays().get(0);
            }else{
                throw new ApiConnectionException("* Error: Server replied with code " + statusCode);
            }
        
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String encodeCity(String city){
        return URLEncoder.encode(city, StandardCharsets.UTF_8); // In case the city contains spaces or characters like 'á' or 'ñ'. E.G.: 'A Coruña'.
    }
}
