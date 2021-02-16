package app.appmeteo.model;

import app.appmeteo.controller.APIQuery;

import java.io.IOException;
import java.security.InvalidParameterException;

public class Favourite {

    public String getName() {
        return name;
    }

    public String getLatLong() {
        return LatLong;
    }


    private String name;
    private String LatLong;
    private String countryCode;

    /**
     * Constructor of favourite
     * @param name name of city you want in Favourite
     */
    public Favourite(String name) {
        try {
            City city = new City(APIQuery.QueryWithCity(name));
            this.name = city.getName().replace(" ", "_");
            LatLong = city.getLongitude() + " " + city.getLatitude() ;
            countryCode = city.getCountry();
        } catch (IOException e) {
            throw new InvalidParameterException();
        }
    }

    /**
     * Constructor of favourite
     * @param longitude longitude of city you want in Favourite
     * @param latitude  latitude of city you want in Favourite
     */
    public Favourite(Double longitude, Double latitude){
        try {
            City city = new City((APIQuery.QueryWeatherWithPos(longitude, latitude)));
            name = city.getName().replace(" ", "_");
            LatLong = city.getLatitude() + " " + city.getLongitude();
            countryCode = city.getCountry();
        } catch (IOException e) {
            throw new InvalidParameterException();
        }
    }

    /**
     * Constructor of favourite
     * @param cityName The postal address of city you want in Favourite
     * @param country The country code of city you want in Favourite
     */
    public Favourite(String cityName, String country){
        try {
            City city = new City((APIQuery.QueryWithCountryCode(cityName, country)));
            name = city.getName().replace(" ", "_");
            LatLong = city.getLatitude() + " " + city.getLongitude();
            countryCode = city.getCountry();
        } catch (IOException e) {
            throw new InvalidParameterException();
        }
    }

    @Override
    public String toString() {
            return name.replace("_", " ") + " " + countryCode;
        }

    @Override
    public boolean equals(Object obj) {
        Favourite other = (Favourite) obj;
        if (name.equals(other.name) && !LatLong.equals(other.LatLong)) return false;
        if (LatLong.equals(other.LatLong)) return true;
        return super.equals(obj);
    }

}
