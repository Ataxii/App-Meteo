package app.appmeteo.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Represents a weather at a certain hour
 * Instantiated by City's constructor
 *
 * @version 2.1
 * @see City
 */
public class HourWeather {
    private final String id;
    private final String main;
    private final String description;
    private final String icon;
    private final long date; // Date of forecast in seconds from 1970
    private final int temp;
    private final int tempFeelsLike;
    private final int humidity;
    private final int windSpeed;
    private final int windDeg;
    private final int pressure;
    private final int visibility;
    private final int cloudiness;
    private int pop; // Probability of Precipitation in %

    /**
     * Called by City's constructor for current weather, parse the string in parameter and initializes all attributes
     *
     * @param data the string returned by an API query
     * @since 1.0
     */
    public HourWeather(String data) {
        JsonObject obj = JsonParser.parseString(data).getAsJsonObject().get("current").getAsJsonObject();
        id = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("id").getAsString();
        main = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
        description = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        icon = "app/appmeteo/images/" + obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString() + ".png";
        date = obj.get("dt").getAsLong();
        temp = (int) Math.round(obj.get("temp").getAsDouble());
        tempFeelsLike = (int) Math.round(obj.get("feels_like").getAsDouble());
        humidity = obj.get("humidity").getAsInt();
        pressure = obj.get("pressure").getAsInt();
        visibility = obj.get("visibility").getAsInt();
        windSpeed = (int) (obj.get("wind_speed").getAsDouble() * 3.6);
        windDeg = obj.get("wind_deg").getAsInt();
        cloudiness = obj.get("clouds").getAsInt();
    }

    /**
     * Called by City constructor for 48hours forecast, creates a HourWeather from the information in the JsonObject in parameter
     *
     * @param obj a JsonObject containing weather's information for a certain hour
     * @since 2.0
     */
    public HourWeather(JsonObject obj) {
        id = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("id").getAsString();
        main = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
        description = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        icon = "app/appmeteo/images/" + obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString() + ".png";
        date = obj.get("dt").getAsLong();
        temp = (int) Math.round(obj.get("temp").getAsDouble());
        tempFeelsLike = (int) Math.round(obj.get("feels_like").getAsDouble());
        humidity = obj.get("humidity").getAsInt();
        pressure = obj.get("pressure").getAsInt();
        visibility = obj.get("visibility").getAsInt();
        windSpeed = (int) (obj.get("wind_speed").getAsDouble() * 3.6);
        windDeg = obj.get("wind_deg").getAsInt();
        pop = (int) (obj.get("pop").getAsDouble() * 100);
        cloudiness = obj.get("clouds").getAsInt();
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

    public int getTemp() {
        return temp;
    }

    public int getTempFeelsLike() {
        return tempFeelsLike;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getWindDeg() {
        return windDeg;
    }

    public long getDate() {
        return date;
    }

    public int getPop() {
        return pop;
    }

    public int getPressure() {
        return pressure;
    }

    public int getCloudiness() {
        return cloudiness;
    }

    public int getVisibility() {
        return visibility;
    }
}
