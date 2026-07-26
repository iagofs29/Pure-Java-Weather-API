package weatherapi;

import java.util.Scanner;

import weatherapi.controllers.*;
import weatherapi.models.*;
import weatherapi.repository.*;
import weatherapi.services.*;
import weatherapi.exceptions.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WeatherRepository repository = new ApiWeatherRepository();
        WeatherService weatherService = new WeatherService(repository);
        WeatherController weatherController = new WeatherController(scanner, weatherService);

        while(true){
            weatherController.showWeatherForCity();
        }
    }
}