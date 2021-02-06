package app.appmeteo.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Represents a weather at a certain hour
 * Instantiated by City's constructor
 * @see City
 * @version 2.0
 */
public class HourWeather {
    private final String id;
    private final String main;
    private final String description;
    private final String icon;
    private final long time;
    private final double temp;
    private final double tempFeelsLike;
    private final int humidity;
    private final double windSpeed;
    private final int windDeg;


    /**
     * Only for tests, reads the JSON file in parameter and initializes all attributes
     * @param filename the name of a JSON file returned by an API query
     * @throws FileNotFoundException if wrong file name is passed in argument
     * @since 1.0
     */
    public HourWeather(File filename) throws FileNotFoundException {
        JsonObject obj = JsonParser.parseReader(new FileReader(filename)).getAsJsonObject();
        id = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("id").getAsString();
        main = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
        description = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        icon = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
        time = obj.get("dt").getAsLong();
        temp = obj.get("main").getAsJsonObject().get("temp").getAsDouble();
        tempFeelsLike = obj.get("main").getAsJsonObject().get("feels_like").getAsDouble();
        humidity = obj.get("main").getAsJsonObject().get("humidity").getAsInt();
        windSpeed = obj.get("wind").getAsJsonObject().get("speed").getAsDouble();
        windDeg = obj.get("wind").getAsJsonObject().get("deg").getAsInt();
    }

    /**
     * Called by City's constructor for current weather, parse the string in parameter and initializes all attributes
     * @param data the string returned by an API query
     * @since 1.0
     */
    public HourWeather(String data) {
        JsonObject obj = JsonParser.parseString(data).getAsJsonObject().get("current").getAsJsonObject();
        id = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("id").getAsString();
        main = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
        description = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        icon = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
        time = obj.get("dt").getAsLong();
        temp = obj.get("temp").getAsDouble();
        tempFeelsLike = obj.get("feels_like").getAsDouble();
        humidity = obj.get("humidity").getAsInt();
        windSpeed = obj.get("wind_speed").getAsDouble();
        windDeg = obj.get("wind_deg").getAsInt();
    }

    /**
     * Called by City constructor for 48hours forecast, creates a HourWeather from the information in the JsonObject in parameter
     * @param obj a JsonObject containing weather's information for a certain hour
     * @since 2.0
     */
    public HourWeather(JsonObject obj) {
        id = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("id").getAsString();
        main = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
        description = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        icon = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
        time = obj.get("dt").getAsLong();
        temp = obj.get("temp").getAsDouble();
        tempFeelsLike = obj.get("feels_like").getAsDouble();
        humidity = obj.get("humidity").getAsInt();
        windSpeed = obj.get("wind_speed").getAsDouble();
        windDeg = obj.get("wind_deg").getAsInt();
    }

    public String getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public double getTemp() {
        return temp;
    }

    public double getTempFeelsLike() {
        return tempFeelsLike;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getWindDeg() {
        return windDeg;
    }

    public long getTime() {
        return time;
    }
}
