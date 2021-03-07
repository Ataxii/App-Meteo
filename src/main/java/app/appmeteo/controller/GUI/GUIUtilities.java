package app.appmeteo.controller.GUI;

import app.appmeteo.controller.APIQuery;
import app.appmeteo.model.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class GUIUtilities {

    /**
     * @return an array representing all data in the text file appState.txt
     * if the file does not exist or does not contains all data, returns a default state.
     */
    public static String[] getAppState() {
        String defaultCity = "Paris FR";
        String defaultTheme = "false";
        String[] appState = new String[]{defaultCity, defaultTheme};
        File appStateFile = new File("./src/main/resources/app/appmeteo/appState.txt");
        if (!appStateFile.exists() || !appStateFile.canRead()) return appState;
        try {
            Scanner scan = new Scanner(appStateFile);
            appState[0] = scan.nextLine();
            appState[1] = scan.nextLine();
            scan.close();
        } catch (NoSuchElementException | FileNotFoundException e) {
            System.out.println("Error : appState.txt might be corrupted, try delete and create it again");
        }
        return appState;
    }

    /**
     * Saves in a file appState.txt
     * if this file does not exist it is created.
     *
     * @param city       : the last researched city (in search bar or in favorites)
     * @param darkModeOn : darkMode status (enabled or not)
     */
    public static void saveAppState(City city, boolean darkModeOn) {
        try {
            File appState = new File("./src/main/resources/app/appmeteo/appState.txt");
            appState.createNewFile();
            FileWriter fileWriter = new FileWriter(appState, false);
            fileWriter.write(
                    city.getName().replace(' ', '_') + " " + city.getCountry()
                            + "\n" + darkModeOn);
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("Error : appState.txt might be corrupted, try delete and create it again");
        }
    }

    /**
     * @param cityName
     * @param countryCode represented by two capital letters
     * @param zipCode     represented by a five digits sequence
     * @return String containing data of the weather report.
     * @throws IOException if query's invalid
     * @see APIQuery#QueryWithCity(String)
     * @see APIQuery#QueryWithCountryCode(String, String)
     * @see APIQuery#QueryWithZip(String, String)
     */
    public static String getQueryResponse(String cityName, String countryCode, String zipCode) throws IOException {
        if (countryCode == null || countryCode.length() != 2) return APIQuery.QueryWithCity(cityName);
        else if (zipCode != null && zipCode.length() >= 5) return APIQuery.QueryWithZip(zipCode, countryCode);
        else return APIQuery.QueryWithCountryCode(cityName, countryCode);
    }

}
