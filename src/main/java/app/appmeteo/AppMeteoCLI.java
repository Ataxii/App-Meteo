package app.appmeteo;

import app.appmeteo.controller.CLI.session.FavouriteSession;
import app.appmeteo.controller.CLI.session.MainSession;
import app.appmeteo.controller.CLI.session.Session;
import app.appmeteo.model.User;

import java.io.IOException;
import java.util.Scanner;

/**
 * Invoked by gradlew runCLI
 * Asks user for a city's name and prints this city's current weather
 */
public class AppMeteoCLI {

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the weather app");

        Session session = new MainSession(new User(new String[] { "fav", "-getW"}));
        session.treatQuery();
        session.launch();
    }

}

