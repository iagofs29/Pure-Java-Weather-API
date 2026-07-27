package weatherapi.controllers;

import weatherapi.dto.WeatherRequest;
import weatherapi.dto.WeatherResponse;
import weatherapi.exceptions.ApiConnectionException;
import weatherapi.exceptions.CityNotFoundException;
import weatherapi.http.QueryParser;
import weatherapi.services.WeatherService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class WeatherController implements HttpHandler {
    private final Gson gson;
    private final WeatherService weatherService;
    private final QueryParser queryParser = new QueryParser();

    public WeatherController(WeatherService weatherService, Gson gson){
        this.weatherService = weatherService;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange){

        if(!"GET".contentEquals(exchange.getRequestMethod())){
            this.handleResponse(exchange, 
                        """
                        {
                            "error": "endpoint only accepts GET method"
                        }
                        """, 
                        405);
            return;
        }

        String path = exchange.getRequestURI().getPath();
        String query = exchange.getRequestURI().getQuery();

        if(query == null || query.isBlank() || query.isEmpty() || !query.contains("city=")){
            this.handleResponse(exchange, 
                        """
                        {
                            "error": "must specify a city"
                        }
                        """, 
                        400);
            return;
        }

        Map<String, String> params = this.queryParser.parse(query);
        WeatherRequest request = new WeatherRequest(params.get("city"), params.get("unitGroup"), params.get("days") == null ? 7 : Integer.parseInt(params.get("days")));

        try{
            switch(path){
                case "/weather":
                case "/weather/current":
                    WeatherResponse retrievedCurrent = this.weatherService.getCurrentWeather(request);

                    String responseBody = gson.toJson(retrievedCurrent, WeatherResponse.class);

                    this.handleResponse(exchange, responseBody, 200);
                    break;

                case "/weather/forecast":
                    if(request.days() < 1 || request.days() > 7){
                        this.handleResponse(exchange, 
                            """
                            {
                                "error": "Invalid days query parameter, must be between 1 and 7"
                            }
                            """,
                            400);
                    }else{
                        WeatherResponse retrievedForecast = this.weatherService.getForecast(request);

                        String responseForecast = gson.toJson(retrievedForecast, WeatherResponse.class);
                        this.handleResponse(exchange, responseForecast, 200);
                    }
                    break;
                
                default: this.handleResponse(exchange, 
                        """
                        {
                            "error": "Invalid request"
                        }
                        """, 
                        400);
            }
        }catch(ApiConnectionException | CityNotFoundException e){
            handleResponse(exchange, 
                """
                {
                    "error": """ + e.getMessage() + """
                
                } 
                """
                , e.getClass() == ApiConnectionException.class ? 502 : 404);
            return;
        }

    }

    private void handleResponse(HttpExchange exchange, String json, int statusCode){
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        byte[] bodyByte = json.getBytes(StandardCharsets.UTF_8);

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
