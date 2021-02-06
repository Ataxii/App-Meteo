package app.appmeteo;

import app.appmeteo.controller.MainSession;
import app.appmeteo.controller.Session;

import java.io.IOException;
import java.util.Scanner;

/**
 * Invoked by gradlew runCLI
 * Asks user for a city's name and prints this city's current weather
 */
public class AppMeteoCLI {

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the weather app");

        Session session = new MainSession(new Scanner(System.in));
        session.launch();
    }

}

