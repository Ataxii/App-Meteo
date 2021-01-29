package app.appmeteo;

import app.appmeteo.model.City;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Scanner;

public class AppMeteoCLI {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Welcome to the weather app");

        System.out.println("You requested command '" + args[0] + "' with parameter '" + args[1] + "'");

        System.out.println("Input your command: ");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sorry, I can't do anything yet ! (Read: " + scanner.nextLine() +")");

        scanner.close();

        City city = new City("test.json");
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
}

