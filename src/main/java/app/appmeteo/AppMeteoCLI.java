package app.appmeteo;

import app.appmeteo.controller.CLI.session.FavouriteSession;
import app.appmeteo.controller.CLI.session.MainSession;
import app.appmeteo.controller.CLI.session.Session;
import app.appmeteo.model.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Invoked by gradlew runCLI
 * Asks user for a city's name and prints this city's current weather
 */
public class AppMeteoCLI {

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the weather app");

        // TRY GET WEATHER OF LAST ADDED FAVOURITE
        User user = new User(new String[] { "weather" + "" + ""});

        if (!user.getFavouriteList().getList().isEmpty()) {
            String city = user.getFavouriteList().getFavouriteAtIndex(user.getFavouriteList().getList().size()-1).getName();
            String countryCode = user.getFavouriteList().getFavouriteAtIndex(user.getFavouriteList().getList().size()-1).getCountryCode();
            user.getQuery().setCommandLineOption(1, city);
            user.getQuery().setCommandLineOption(2, countryCode);
            System.out.println(Arrays.toString(user.getQuery().getCommandLine()));
        }

        Session session = new MainSession(user);
        session.treatQuery();
        session.launch();
    }

}

