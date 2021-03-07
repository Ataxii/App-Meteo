package app.appmeteo;

import app.appmeteo.controller.CLI.WeatherSession;
import app.appmeteo.controller.GUI.GUIUtilities;
import app.appmeteo.model.User;

import java.io.IOException;

/**
 * Invoked by gradlew runCLI
 * Asks user for a city's name and prints this city's current weather
 */
public class AppMeteoCLI {

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the weather app!\n");

        // TRY GET WEATHER OF LAST ADDED FAVOURITE
        User user = new User(new String[]{"", ""});

        String city;
        String countryCode;
        String[] lastResearch = GUIUtilities.getAppState()[0].split(" ");

        if (!user.getFavouriteList().getList().isEmpty()) {
            System.out.println("Displaying current weather of last added favourite....");
            city = user.getFavouriteList().getFavouriteAtIndex(user.getFavouriteList().getList().size() - 1).getName();
            countryCode = user.getFavouriteList().getFavouriteAtIndex(user.getFavouriteList().getList().size() - 1).getCountryCode();
        } else{
            System.out.println("Displaying current weather of last research/default town....");
            city = lastResearch[0];
            countryCode = lastResearch[1];
        }

        user.getQuery().setCommandLineOption(0, city);
        user.getQuery().setCommandLineOption(1, countryCode);

        WeatherSession session = new WeatherSession(user);
        session.treatQuery();
        System.out.println();
        session.launch();
    }

}

