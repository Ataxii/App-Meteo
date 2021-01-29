package app.appmeteo.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Represents a weather at a certain time
 * Instantiated by City's constructor
 * @see City
 * @version 1.0
 * @since 1.0
 */
public class Weather {
    private final String id;
    private final String main;
    private final String description;
    private final String icon;
    private final long time;
    private final double temp;
    private final double tempFeelsLike;
    private final double tempMin;
    private final double tempMax;
    private final int humidity;
    private final double windSpeed;
    private final int windDeg;


    /**
     * Called by City's constructor, reads the JSON file in parameter and initializes all attributes
     * @param filename the name of a JSON file returned by an API query
     * @throws FileNotFoundException if wrong file name is passed in argument
     */
    public Weather(File filename) throws FileNotFoundException {
        JsonObject obj = JsonParser.parseReader(new FileReader(filename)).getAsJsonObject();
        id = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("id").getAsString();
        main = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
        description = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        icon = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
        time = obj.get("dt").getAsLong();
        temp = obj.get("main").getAsJsonObject().get("temp").getAsDouble();
        tempFeelsLike = obj.get("main").getAsJsonObject().get("feels_like").getAsDouble();
        tempMin = obj.get("main").getAsJsonObject().get("temp_min").getAsDouble();
        tempMax = obj.get("main").getAsJsonObject().get("temp_max").getAsDouble();
        humidity = obj.get("main").getAsJsonObject().get("humidity").getAsInt();
        windSpeed = obj.get("wind").getAsJsonObject().get("speed").getAsDouble();
        windDeg = obj.get("wind").getAsJsonObject().get("deg").getAsInt();
    }

    /**
     * Called by City's constructor, parse the string in parameter and initializes all attributes
     * @param datas the string returned by an API query
     */
    public Weather(String datas) {
        JsonObject obj = JsonParser.parseString(datas).getAsJsonObject();
        id = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("id").getAsString();
        main = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
        description = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        icon = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
        time = obj.get("dt").getAsLong();
        temp = obj.get("main").getAsJsonObject().get("temp").getAsDouble();
        tempFeelsLike = obj.get("main").getAsJsonObject().get("feels_like").getAsDouble();
        tempMin = obj.get("main").getAsJsonObject().get("temp_min").getAsDouble();
        tempMax = obj.get("main").getAsJsonObject().get("temp_max").getAsDouble();
        humidity = obj.get("main").getAsJsonObject().get("humidity").getAsInt();
        windSpeed = obj.get("wind").getAsJsonObject().get("speed").getAsDouble();
        windDeg = obj.get("wind").getAsJsonObject().get("deg").getAsInt();
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

    public double getTempMax() {
        return tempMax;
    }

    public double getTempMin() {
        return tempMin;
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
