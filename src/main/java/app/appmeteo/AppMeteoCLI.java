package app.appmeteo;

import app.appmeteo.controller.APIQuery;
import app.appmeteo.model.City;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Scanner;

/**
 * Invoked by gradlew runCLI
 * Asks user for a city's name and prints this city's current weather
 */
public class AppMeteoCLI {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the weather app");

//        System.out.println("You requested command '" + args[0] + "' with parameter '" + args[1] + "'");
//
//        System.out.println("Input your command: ");
        System.out.println("What's the weather like in : ");
        Scanner scanner = new Scanner(System.in);
        String cityName = scanner.nextLine();

        scanner.close();
        APIQuery.QueryWithCity(cityName);

        City city = new City("Data.json");
        // Prediction time
        System.out.print("Time : ");
        System.out.println(LocalDateTime.ofEpochSecond(city.getWeatherNow().getTime() + city.getTimezone()
                ,0, ZoneOffset.UTC));
        // Weather
        System.out.print("Weather : ");
        System.out.println(city.getWeatherNow().getMain());
        // Temperature Celsius
        System.out.print("Temperature : ");
        System.out.print(city.getWeatherNow().getTemp());
        System.out.println("°C");
        // Wind speed meter/sec
        System.out.print("Wind : ");
        System.out.print(city.getWeatherNow().getWindSpeed());
        System.out.println("m/s");
    }
}

