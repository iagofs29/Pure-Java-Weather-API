package weatherapi.controllers;

import weatherapi.dto.WeatherRequest;
import weatherapi.dto.WeatherResponse;
import weatherapi.exceptions.ApiConnectionException;
import weatherapi.exceptions.CityNotFoundException;
import weatherapi.http.QueryParser;
import weatherapi.services.WeatherService;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class WeatherController implements HttpHandler {
    private final Scanner scanner;
    private final Gson gson;
    private final WeatherService weatherService;
    private final QueryParser queryParser = new QueryParser();

    public WeatherController(Scanner scanner, WeatherService weatherService, Gson gson){
        this.scanner = scanner;
        this.weatherService = weatherService;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange){
        Headers headers = exchange.getResponseHeaders();
        headers.set("Content-Type", "application/json; charset = UTF-8");

        if(!"GET".contentEquals(exchange.getRequestMethod())){
            this.handleResponse(exchange, headers, 
                        """
                        {
                            "error": "endpoint only accepts GET method"
                        }
                        """, 
                        405);
        }

        String path = exchange.getRequestURI().getPath();
        String query = exchange.getRequestURI().getQuery();

        if(!query.contains("city=")){
            this.handleResponse(exchange, headers, 
                        """
                        {
                            "error": "must specify a city"
                        }
                        """, 
                        400);
        }

        Map<String, String> params = this.queryParser.parse(query);

        try{
            switch(path){
                case "/weather":
                case "/weather/current":
                    WeatherRequest requestCurrent = new WeatherRequest(params.get("city"), params.get("unitGroup"));
                    WeatherResponse retrievedData = this.weatherService.getCurrentWeather(requestCurrent);

                    Type typeOfWeatherResponse = new TypeToken<WeatherResponse>(){}.getType();
                    String responseBody = gson.toJson(retrievedData, typeOfWeatherResponse);
                    this.handleResponse(exchange, headers, responseBody, 200);
                    break;

                case "/weather/forecast":
                    WeatherRequest requestForecast = new WeatherRequest(params.get("city"), params.get("unitGroup"));

            }
        }catch(ApiConnectionException | CityNotFoundException e){
            handleResponse(exchange, headers, 
                """
                {
                    "error": """ + e.getMessage() + """
                } 
                """
                , e.getClass() == ApiConnectionException.class ? 502 : 404);;
        }

    }

    private void handleResponse(HttpExchange exchange, Headers headers, String body, int statusCode){
        byte[] bodyByte = body.getBytes(StandardCharsets.UTF_8);

        try{
            exchange.sendResponseHeaders(statusCode, bodyByte.length);
            try(OutputStream stream = exchange.getResponseBody()){
                stream.write(bodyByte);
            }
        }catch(IOException e){
            System.out.println("Error sending HTTP response: " + e.getMessage());
        }
    }
}
