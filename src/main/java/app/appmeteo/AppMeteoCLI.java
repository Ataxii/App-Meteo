package app.appmeteo;

import app.appmeteo.controller.APIQuery;
import app.appmeteo.model.City;
import app.appmeteo.model.Favourite;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Scanner;

public class AppMeteoCLI {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the weather app");

        System.out.println("You requested command '" + args[0] + "' with parameter '" + args[1] + "'");

        System.out.println("Input your command: ");
        Scanner scanner = new Scanner(System.in);
        String cityName = scanner.nextLine();

        Favourite.deleteFavourite(cityName);
        scanner.close();
        System.out.println(cityName);
        APIQuery.QueryWithCity(cityName);

        City city = new City("Data.json");
        // Prediction time
        System.out.println(LocalDateTime.ofEpochSecond(city.getWheatherNow().getTime() + city.getTimezone()
                ,0, ZoneOffset.UTC));
        // Weather
        System.out.println(city.getWheatherNow().getMain());
        // Temperature Celsius
        System.out.println(city.getWheatherNow().getTemp() - 273.15);
        // Wind speed meter/sec
        System.out.println(city.getWheatherNow().getWindSpeed());
    }


    private static double KelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }
}

