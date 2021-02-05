package app.appmeteo.model;

import app.appmeteo.controller.APIQuery;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a city
 * @version 1.1
 */
public class City {
    private final String id;
    private final String name;
    private final String country;
    private final double longitude;
    private final double latitude;
    private final long timezone;
    private final HourWeather weatherNow;
    private final ArrayList<HourWeather> weatherPerHour;
    private final ArrayList<DayWeather> weatherPerDay;


    /**
     * Only for tests, reads the JSON file in parameter and initializes all attributes
     * Creates a Weather object weatherNow corresponding to city's current weather
     * @param file the JSON file returned by an API query
     * @throws FileNotFoundException if wrong file name is passed in argument
     * @since 1.0
     */
    public City(File file) throws IOException {
        JsonObject obj = JsonParser.parseReader(new FileReader(file)).getAsJsonObject();
        id = obj.get("id").getAsString();
        name = obj.get("name").getAsString();
        country = obj.get("sys").getAsJsonObject().get("country").getAsString();
        longitude = obj.get("coord").getAsJsonObject().get("lon").getAsDouble();
        latitude = obj.get("coord").getAsJsonObject().get("lat").getAsDouble();
        timezone = obj.get("timezone").getAsLong();
        String APIresponse = APIQuery.QueryWithPos(longitude, latitude);
        weatherNow = new HourWeather(APIresponse);

        weatherPerHour = new ArrayList<>();
        JsonObject json = JsonParser.parseString(APIresponse).getAsJsonObject();
        JsonArray arr = json.getAsJsonArray("hourly");
        for (JsonElement e : arr) {
            weatherPerHour.add(new HourWeather(e.getAsJsonObject()));
        }

        weatherPerDay = new ArrayList<>();
        json = JsonParser.parseString(APIresponse).getAsJsonObject();
        arr = json.getAsJsonArray("daily");
        for (JsonElement e : arr) {
            weatherPerDay.add(new DayWeather(e.getAsJsonObject()));
        }
    }

    /**
     * Reads the String in parameter and initializes all attributes
     * Creates a Weather object weatherNow corresponding to city's current weather
     * @param data the string returned by an API query
     * @since 1.0
     */
    public City(String data) throws IOException {
        JsonObject obj = JsonParser.parseString(data).getAsJsonObject();
        id = obj.get("id").getAsString();
        name = obj.get("name").getAsString();
        country = obj.get("sys").getAsJsonObject().get("country").getAsString();
        longitude = obj.get("coord").getAsJsonObject().get("lon").getAsDouble();
        latitude = obj.get("coord").getAsJsonObject().get("lat").getAsDouble();
        timezone = obj.get("timezone").getAsLong();
        String APIresponse = APIQuery.QueryWithPos(longitude, latitude);
        weatherNow = new HourWeather(APIresponse);

        weatherPerHour = new ArrayList<>();
        JsonObject json = JsonParser.parseString(APIresponse).getAsJsonObject();
        JsonArray arr = json.getAsJsonArray("hourly");
        for (JsonElement e : arr) {
            weatherPerHour.add(new HourWeather(e.getAsJsonObject()));
        }

        weatherPerDay = new ArrayList<>();
        json = JsonParser.parseString(APIresponse).getAsJsonObject();
        arr = json.getAsJsonArray("daily");
        for (JsonElement e : arr) {
            weatherPerDay.add(new DayWeather(e.getAsJsonObject()));
        }
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getCountry() {
        return country;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getTimezone() {
        return timezone;
    }

    /**
     * @return the HourWeather object corresponding to city query's time
     * @since 1.0
     */
    public HourWeather getWeatherNow() {
        return weatherNow;
    }

    /**
     * @return an ArrayList of HourWeather for the next 48 hours
     * @since 1.1
     */
    public ArrayList<HourWeather> getWeatherPerHour() {
        return weatherPerHour;
    }

    /**
     * @return an ArrayList of DayWeather for the next 7 days
     * @since 1.1
     */
    public ArrayList<DayWeather> getWeatherPerDay() {
        return weatherPerDay;
    }
}
