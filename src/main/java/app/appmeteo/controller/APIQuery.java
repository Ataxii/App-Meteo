package app.appmeteo.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIQuery {
    private static final String key = "5fbdee4725692638183914f166ab5066"; // API Key

    public static void QueryWithCity(String city) throws IOException {
        // API Query
        String StrUrl = "http://api.openweathermap.org/data/2.5/weather?q="+ city +"&appid=" + key;

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


}
