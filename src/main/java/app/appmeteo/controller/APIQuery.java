package app.appmeteo.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Controller to query API
 * @version 1.2
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
        String data = r.readLine();

        // return string
        return data;
    }

    /**
     * Creates a query for the API asking for current weather in the city passed in parameter
     * @param city the String corresponding to city's name
     * @param countryCode the String corresponding to city's country code
     * @throws IOException if the URL sent is not valid
     * @return a string containing all .json infos from the API answer
     * @since 1.2
     */
    public static String QueryWithCountryCode(String city, String countryCode) throws IOException {
        // API Query
        String StrUrl = "http://api.openweathermap.org/data/2.5/weather?q="+ city + "," + countryCode +
                "&units=metric&appid=" + key;

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
     * Creates a query for the API asking for current weather in the city passed in parameter
     * @param id the String corresponding to city's ID
     * @throws IOException if the URL sent is not valid
     * @return a string containing all .json infos from the API answer
     * @since 1.2
     */
    public static String QueryWithID(String id) throws IOException {
        // API Query
        String StrUrl = "http://api.openweathermap.org/data/2.5/weather?id="+ id +"&units=metric&appid=" + key;

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
     * Creates a query for the API asking for current weather in the city passed in parameter
     * @param zip the String corresponding to city's ZIP Code
     * @param countryCode the String corresponding to city's country code
     * @throws IOException if the URL sent is not valid
     * @return a string containing all .json infos from the API answer
     * @since 1.2
     */
    public static String QueryWithZip(String zip, String countryCode) throws IOException {
        // API Query
        String StrUrl = "http://api.openweathermap.org/data/2.5/weather?zip="+ zip + "," + countryCode +"&units=metric&appid=" + key;

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
     * Creates and returns a string containing all .json infos from the API answer about 7 days forecast
     * @param longitude longitude of the city
     * @param latitude latitude of the city
     * @return a string containing all .json infos from the API answer
     * @throws IOException if the URL sent is not valid
     * @since 1.1
     */
    public static String QueryOneCallWithPos(double longitude, double latitude) throws IOException {
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

    /**
     * Creates a query for the API asking for weather in coordinates passed in parameter
     * Creates and returns a string containing all .json infos from the API answer about current weather
     * @param longitude longitude of the city
     * @param latitude latitude of the city
     * @return a string containing all .json infos from the API answer
     * @throws IOException if the URL sent is not valid
     * @since 1.1
     */
    public static String QueryWeatherWithPos(double longitude, double latitude) throws IOException {
        // API Query
        String StrUrl = "http://api.openweathermap.org/data/2.5/weather?lat=" + latitude +
                "&lon=" + longitude + "&appid=" + key;

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
