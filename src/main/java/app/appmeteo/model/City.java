package app.appmeteo.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Represents a city
 * @version 1.0
 */
public class City {
    private final String id;
    private final String name;
    private final String country;
    private final double longitude;
    private final double latitude;
    private final long timezone;
    private final Weather weatherNow;


    /**
     * Reads the JSON file in parameter and initializes all attributes
     * Creates a Weather object weatherNow corresponding to city's current weather
     * @param filename the name of a JSON file returned by an API query
     * @throws FileNotFoundException if wrong file name is passed in argument
     * @since 1.0
     */
    public City(String filename) throws FileNotFoundException {
        JsonObject obj = JsonParser.parseReader(new FileReader(filename)).getAsJsonObject();
        id = obj.get("id").getAsString();
        name = obj.get("name").getAsString();
        country = obj.get("sys").getAsJsonObject().get("country").getAsString();
        longitude = obj.get("coord").getAsJsonObject().get("lon").getAsDouble();
        latitude = obj.get("coord").getAsJsonObject().get("lat").getAsDouble();
        timezone = obj.get("timezone").getAsLong();
        weatherNow = new Weather(filename);
    }

    public City(File file) throws FileNotFoundException {
        this(file.getPath());
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
     * @return the Weather object corresponding to city query's time
     * @since 1.0
     */
    public Weather getWeatherNow() {
        return weatherNow;
    }
}
