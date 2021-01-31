package app.appmeteo;

import app.appmeteo.controller.APIQuery;
import app.appmeteo.controller.AppMeteoController;
import app.appmeteo.controller.Query;
import app.appmeteo.model.City;
import app.appmeteo.model.Favourite;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Scanner;

public class AppMeteoCLI {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the weather app");

        System.out.println("You requested command '" + args[0] + "' with parameter '" + args[1] + "'");

        System.out.println("Input your command: ");
        Scanner scanner = new Scanner(System.in);
        Query query = new Query(scanner);
        AppMeteoController.setStopControl(false);
        while(!AppMeteoController.hasToStop()) {
            System.out.println("Input your command: ");
            query.next();
            try {
                AppMeteoController.TreatQuery(query);
            } catch (InvalidParameterException e) {
                AppMeteoController.setStopControl(true);
                System.out.println("Invalid Query");
            } finally {
                if (AppMeteoController.hasToStop())
                    System.out.println("Thanks to use our app");
                else {
                    System.out.println("Another request? Type it ! ");
                    System.out.println("Else type quit. ");
                }
            }
        }


        scanner.close();



        //Favourite.deleteFavourite(cityName);

        //System.out.println(cityName);



    }


    private static double KelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }
}



