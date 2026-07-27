package weatherapi.controllers;

import weatherapi.dto.WeatherRequest;
import weatherapi.http.QueryParser;
import weatherapi.repository.WeatherRepository;
import weatherapi.services.WeatherService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class WeatherController implements HttpHandler {
    private final Scanner scanner;
    private final WeatherService weatherService;
    private final QueryParser queryParser = new QueryParser();

    public WeatherController(Scanner scanner, WeatherService weatherService){
        this.scanner = scanner;
        this.weatherService = weatherService;
    }

    @Override
    public void handle(HttpExchange exchange){
        Headers headers = exchange.getResponseHeaders();
        headers.set("Content-Type", "application/json; charset = UTF-8");

        if(!"GET".contentEquals(exchange.getRequestMethod())){
            this.handleMethodNotAllowed(exchange, headers);
        }

        String path = exchange.getRequestURI().getPath();
        String query = exchange.getRequestURI().getQuery();

        if(!query.contains("city=")){
            this.handleBadRequest(exchange, headers);
        }

        Map<String, String> params = this.queryParser.parse(query);
        WeatherRequest request = new WeatherRequest(params.get("city"), params.get("unitGroup"));

        switch(path){
            case "/weather":
            case "/weather/current":
                try{
                    this.weatherService.getWeatherByRequest(request);
                }catch(IllegalStateException e){
                    System.out.println(e.getMessage());
                }


        }

    }

    private void handleBadRequest(HttpExchange exchange, Headers headers){
        String responseBody = """
                {
                    "error": "must specify a city"
                }
                """;
        byte[] bodyByte = responseBody.getBytes(StandardCharsets.UTF_8);

        try{
            exchange.sendResponseHeaders(400, bodyByte.length);
            try(OutputStream stream = exchange.getResponseBody()){
                stream.write(bodyByte);
            }
        }catch(IOException e){
            System.out.println("Error sending HTTP response: " + e.getMessage());
        }
    }

    private void handleMethodNotAllowed(HttpExchange exchange, Headers headers){
        headers.set("Allow", "GET");
        
        String responseBody = """
                {
                    "error": "endpoint only accepts GET method"
                }
                """;
        byte [] bodyBytes = responseBody.getBytes(StandardCharsets.UTF_8);
            
        try {
            exchange.sendResponseHeaders(405, bodyBytes.length);
            try(OutputStream stream = exchange.getResponseBody()){
                stream.write(bodyBytes);
            }
        }catch (IOException e) {
            System.out.println("Error sending HTTP response: " + e.getMessage());
        }
    }


    /* 
    public void showWeatherForCity(){
        String city = "";

        System.out.print("Type the city you would like to know the weather of (Press enter to exit program): ");

        try{
            city = scanner.nextLine();

            if(city.isBlank() || city.isEmpty()){
                scanner.close();
                System.exit(0);
            }

            try{
                Weather weather = weatherService.getWeatherByCity(city);
                System.out.println(weather);
            }catch(ApiConnectionException | IllegalStateException e){
                System.out.println(e.getMessage());
            }
        }catch(NoSuchElementException e){
            System.out.println("Please type a valid line.");
        }
        
    }
    */
    
}
