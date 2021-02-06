package app.appmeteo.model;

import com.google.gson.JsonObject;

/**
 * Represents a weather at a certain day
 * Instantiated by City's constructor
 * @see City
 * @version 1.0
 */
public class DayWeather {
    private final String id;
    private final String main;
    private final String description;
    private final String icon;
    private final long time;
    private final double tempDay;
    private final double tempMin;
    private final double tempMax;
    private final double tempNight;
    private final double tempEvening;
    private final double tempMorning;
    private final double tempFeelsLikeDay;
    private final double tempFeelsLikeNight;
    private final double tempFeelsLikeEvening;
    private final double tempFeelsLikeMorning;
    private final int humidity;
    private final double windSpeed;
    private final int windDeg;

    /**
     * Called by City constructor, creates a DayWeather from the information in the JsonObject in parameter
     * @param obj a JsonObject containing weather's information for a certain day
     * @since 1.0
     */
    public DayWeather(JsonObject obj) {
        id = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("id").getAsString();
        main = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
        description = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        icon = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
        time = obj.get("dt").getAsLong();
        tempDay = obj.get("temp").getAsJsonObject().get("day").getAsDouble();
        tempMin = obj.get("temp").getAsJsonObject().get("min").getAsDouble();
        tempMax = obj.get("temp").getAsJsonObject().get("max").getAsDouble();
        tempNight = obj.get("temp").getAsJsonObject().get("night").getAsDouble();
        tempEvening = obj.get("temp").getAsJsonObject().get("eve").getAsDouble();
        tempMorning = obj.get("temp").getAsJsonObject().get("morn").getAsDouble();
        tempFeelsLikeDay = obj.get("feels_like").getAsJsonObject().get("day").getAsDouble();
        tempFeelsLikeNight = obj.get("feels_like").getAsJsonObject().get("night").getAsDouble();
        tempFeelsLikeEvening = obj.get("feels_like").getAsJsonObject().get("eve").getAsDouble();
        tempFeelsLikeMorning = obj.get("feels_like").getAsJsonObject().get("morn").getAsDouble();
        humidity = obj.get("humidity").getAsInt();
        windSpeed = obj.get("wind_speed").getAsDouble();
        windDeg = obj.get("wind_deg").getAsInt();
    }

    public String getId() {
        return id;
    }

    public int getWindDeg() {
        return windDeg;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getIcon() {
        return icon;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public String getDescription() {
        return description;
    }

    public double getTempDay() {
        return tempDay;
    }

    public double getTempEvening() {
        return tempEvening;
    }

    public double getTempFeelsLikeDay() {
        return tempFeelsLikeDay;
    }

    public double getTempFeelsLikeEvening() {
        return tempFeelsLikeEvening;
    }

    public double getTempFeelsLikeMorning() {
        return tempFeelsLikeMorning;
    }

    public double getTempFeelsLikeNight() {
        return tempFeelsLikeNight;
    }

    public double getTempMorning() {
        return tempMorning;
    }

    public double getTempNight() {
        return tempNight;
    }

    public long getTime() {
        return time;
    }

    public String getMain() {
        return main;
    }
}
