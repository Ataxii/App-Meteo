package app.appmeteo.model;

import com.google.gson.JsonObject;

/**
 * Represents a weather at a certain day
 * Instantiated by City's constructor
 * Dates are in seconds from 1970
 *
 * @version 1.1
 * @see City
 */
public class DayWeather {
    private final String id;
    private final String main;
    private final String description;
    private final String icon;
    private final long date;
    private final long sunrise;
    private final long sunset;
    private final int tempDay;
    private final int tempMin;
    private final int tempMax;
    private final int tempNight;
    private final int tempEvening;
    private final int tempMorning;
    private final int tempFeelsLikeDay;
    private final int tempFeelsLikeNight;
    private final int tempFeelsLikeEvening;
    private final int tempFeelsLikeMorning;
    private final int humidity;
    private final int pressure;
    private final int windSpeed;
    private final int windDeg;
    private final int pop;
    private final int cloudiness;

    /**
     * Called by City constructor, creates a DayWeather from the information in the JsonObject in parameter
     *
     * @param obj a JsonObject containing weather's information for a certain day
     * @since 1.0
     */
    public DayWeather(JsonObject obj) {
        id = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("id").getAsString();
        main = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
        description = obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        icon = "app/appmeteo/images/" + obj.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString() + ".png";
        date = obj.get("dt").getAsLong();
        sunrise = obj.get("sunrise").getAsLong();
        sunset = obj.get("sunset").getAsLong();
        tempDay = (int) Math.round(obj.get("temp").getAsJsonObject().get("day").getAsDouble());
        tempMin = (int) Math.round(obj.get("temp").getAsJsonObject().get("min").getAsDouble());
        tempMax = (int) Math.round(obj.get("temp").getAsJsonObject().get("max").getAsDouble());
        tempNight = (int) Math.round(obj.get("temp").getAsJsonObject().get("night").getAsDouble());
        tempEvening = (int) Math.round(obj.get("temp").getAsJsonObject().get("eve").getAsDouble());
        tempMorning = (int) Math.round(obj.get("temp").getAsJsonObject().get("morn").getAsDouble());
        tempFeelsLikeDay = (int) Math.round(obj.get("feels_like").getAsJsonObject().get("day").getAsDouble());
        tempFeelsLikeNight = (int) Math.round(obj.get("feels_like").getAsJsonObject().get("night").getAsDouble());
        tempFeelsLikeEvening = (int) Math.round(obj.get("feels_like").getAsJsonObject().get("eve").getAsDouble());
        tempFeelsLikeMorning = (int) Math.round(obj.get("feels_like").getAsJsonObject().get("morn").getAsDouble());
        humidity = obj.get("humidity").getAsInt();
        pressure = obj.get("pressure").getAsInt();
        windSpeed = (int) (obj.get("wind_speed").getAsDouble() * 3.6);
        windDeg = obj.get("wind_deg").getAsInt();
        pop = (int) (obj.get("pop").getAsDouble() * 100);
        cloudiness = obj.get("clouds").getAsInt();
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

    public int getWindSpeed() {
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

    public int getTempDay() {
        return tempDay;
    }

    public int getTempEvening() {
        return tempEvening;
    }

    public int getTempFeelsLikeDay() {
        return tempFeelsLikeDay;
    }

    public int getTempFeelsLikeEvening() {
        return tempFeelsLikeEvening;
    }

    public int getTempFeelsLikeMorning() {
        return tempFeelsLikeMorning;
    }

    public int getTempFeelsLikeNight() {
        return tempFeelsLikeNight;
    }

    public int getTempMorning() {
        return tempMorning;
    }

    public int getTempNight() {
        return tempNight;
    }

    public long getDate() {
        return date;
    }

    public String getMain() {
        return main;
    }

    public int getPop() {
        return pop;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public int getPressure() {
        return pressure;
    }

    public int getCloudiness() {
        return cloudiness;
    }
}
