package weatherapi;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOError;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Scanner;

import weatherapi.controllers.*;
import weatherapi.dto.*;
import weatherapi.repository.*;
import weatherapi.services.*;
import weatherapi.exceptions.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WeatherRepository repository = new VisualCrossingRepository();
        WeatherService weatherService = new WeatherService(repository);
        WeatherController weatherController = new WeatherController(scanner, weatherService);

        try{
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
            server.createContext("/weather", weatherController);
            server.start();

            System.out.println("Server succesfully initialized in http://localhost:8080");

        }catch(IOException e){
            System.out.println("* Error: could not build server.");
            scanner.close();
            System.exit(0);
        }

        /*while(true){
            weatherController.showWeatherForCity();
        }*/
    }
}