package app.appmeteo.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Controller to query API
 * @version 1.1
 */
public class APIQuery {
    private static final String key = "5fbdee4725692638183914f166ab5066"; // API Key

    /**
     * Creates a query for the API asking for current weather in the city passed in parameter
     * Creates a JSON document from the API answer
     * @param city the String corresponding to city's name
     * @throws IOException if the URL sent is not valid
     * @since 1.0
     */
    public static void QueryWithCity(String city) throws IOException {
        // API Query
        String StrUrl = "http://api.openweathermap.org/data/2.5/weather?q="+ city +"&units=metric&appid=" + key;

        URL url = new URL(StrUrl);
        // URL connection
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        // Buffer creation
        InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));

        // JSON creation
        File file = new File("Data.json");
        BufferedWriter w = new BufferedWriter(new FileWriter(file));
        w.write(r.readLine());
        w.close();
    }

    /**
     * Creates a query for the API asking for current weather in the city passed in parameter
     * @param city the String corresponding to city's name
     * @throws IOException if the URL sent is not valid
     * @return a string containing all .json infos from the API answer
     * @since 1.0
     */
    public static String QueryStringWithCity(String city) throws IOException {
        // API Query
        String StrUrl = "http://api.openweathermap.org/data/2.5/weather?q="+ city +"&units=metric&appid=" + key;

        URL url = new URL(StrUrl);
        // URL connection
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        // Buffer creation
        InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));

        // return string
        return r.readLine();
    }


    /**
     * Creates a query for the API asking for weather in coordinates passed in parameter
     * Creates and returns a string containing all .json infos from the API answer
     * @param longitude longitude of the city
     * @param latitude latitude of the city
     * @return a string containing all .json infos from the API answer
     * @throws IOException if the URL sent is not valid
     */
    public static String QueryWithPos(double longitude, double latitude) throws IOException {
        // API Query
        String StrUrl = "https://api.openweathermap.org/data/2.5/onecall?lat=" + latitude +
                "&lon=" + longitude + "&exclude=minutely,alerts&appid=" + key;

        URL url = new URL(StrUrl);
        // URL connection
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        // Buffer creation
        InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));

        // return string
        return r.readLine();
    }
}
