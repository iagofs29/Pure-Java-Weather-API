package weatherapi.repository;

import weatherapi.dto.VisualCrossingResponse;
import weatherapi.dto.Weather;
import weatherapi.dto.WeatherRequest;
import weatherapi.dto.WeatherResponse;
import weatherapi.exceptions.ApiConnectionException;
import weatherapi.exceptions.CityNotFoundException;

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

    private Gson gson;
    private final String API_KEY;
    private static final String INCLUDE_CURRENT = "current";
    private static final String INCLUDE_DAYS = "days";
    private static final String BASE_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    public VisualCrossingRepository(Gson gson){
        this.gson = gson;
        this.API_KEY = this.getApiKey();
    }

    private String getApiKey(){
        String apiKey = System.getenv("VISUAL_CROSSING_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
        throw new IllegalStateException("Environment variable VISUAL_CROSSING_API_KEY is not defined.");
        }

        return apiKey;
    }
    
    @Override
    public WeatherResponse fetchCurrentWeather(WeatherRequest request){
        VisualCrossingResponse vcResponse = fetchData(request, INCLUDE_CURRENT);
        WeatherResponse finalResponse = new WeatherResponse(vcResponse.address(), vcResponse.dayParams());
        return finalResponse;
    }

    // TODO
    @Override
    public WeatherResponse fetchForecast(WeatherRequest request){
        return null;
    }

    private VisualCrossingResponse fetchData(WeatherRequest request, String include){

        HttpClient httpClient = HttpClient.newHttpClient();

        URI url = URI.create(BASE_URL + this.encodeParam(request.city()) + "?unitGroup=" 
                    + this.encodeParam(request.unitGroup()) + "&include=" + include + "&key=" + API_KEY + "&contentType=json");

        HttpRequest getRequest = HttpRequest.newBuilder(url).GET().build();

        try {
            HttpResponse<String> response = httpClient.send(getRequest, BodyHandlers.ofString());

            int statusCode = response.statusCode();

            if(statusCode >= 200 && statusCode < 300){
                VisualCrossingResponse vcResponse = gson.fromJson(response.body(), VisualCrossingResponse.class);
                return vcResponse;
            }else{
                switch(statusCode){
                    case 404 -> throw new CityNotFoundException("City not found");
                    case 401, 403, 429, 500 -> throw new ApiConnectionException("Visual Crossing server replied with code " + statusCode);
                    default -> throw new ApiConnectionException("Visual Crossing server replied with code " + statusCode);

                }
            }
        
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }   


    private String encodeParam(String param){
        return URLEncoder.encode(param, StandardCharsets.UTF_8); // In case the city contains spaces or characters like 'á' or 'ñ'. E.G.: 'A Coruña'.
    }
}
