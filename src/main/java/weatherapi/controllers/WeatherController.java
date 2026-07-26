package weatherapi.controllers;

import weatherapi.services.WeatherService;
import weatherapi.exceptions.ApiConnectionException;
import weatherapi.models.Weather;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class WeatherController {
    private final Scanner scanner;
    private final WeatherService weatherService;

    public WeatherController(Scanner scanner, WeatherService weatherService){
        this.scanner = scanner;
        this.weatherService = weatherService;
    }

    public void showWeatherForCity(){
        String city = "";

        System.out.print("Type the city you would like to know the weather of: ");
        try{
            city = scanner.nextLine();
        }catch(NoSuchElementException e){
            System.out.println("Please type a valid line.");
        }

        try{
            Weather weather = weatherService.getWeatherByCity(city);
            System.out.println(weather);
        }catch(ApiConnectionException | IllegalStateException e){
            System.out.println(e.getMessage());
        }
        
    }
}
