package app.appmeteo.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class City {
    private final String id;
    private final String name;
    private final String country;
    private final double longitude;
    private final double latitude;
    private final long timezone;
    private final Weather wheatherNow;

    public City(String filename) throws FileNotFoundException {
        JsonObject obj = JsonParser.parseReader(new FileReader(filename)).getAsJsonObject();
        id = obj.get("id").getAsString();
        name = obj.get("name").getAsString();
        country = obj.get("sys").getAsJsonObject().get("country").getAsString();
        longitude = obj.get("coord").getAsJsonObject().get("lon").getAsDouble();
        latitude = obj.get("coord").getAsJsonObject().get("lat").getAsDouble();
        timezone = obj.get("timezone").getAsLong();
        wheatherNow = new Weather(filename);
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

    public Weather getWheatherNow() {
        return wheatherNow;
    }
}
