package weatherapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import weatherapi.controllers.*;
import weatherapi.repository.*;
import weatherapi.services.*;

public class Main {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        WeatherRepository repository = new VisualCrossingRepository(gson);
        WeatherService weatherService = new WeatherService(repository);
        WeatherController weatherController = new WeatherController(weatherService, gson);

        try{
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
            server.createContext("/weather", weatherController);
            server.start();

            System.out.println("Server succesfully initialized in http://localhost:8080");

        }catch(IOException e){
            System.out.println("* Error: could not build server.");
            System.exit(0);
        }

    }
}