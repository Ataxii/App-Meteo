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

    /**
     * Constructor of favourite
     * @param name name of city you want in Favourite
     */
    public Favourite(String name) {
        try {
            City city = new City(APIQuery.QueryWithCity(name));
            this.name = city.getName().replace(' ', '_');
            this.LatLong = city.getLongitude() + " " + city.getLatitude() ;
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
            this.name = city.getName();

            this.LatLong = city.getLatitude() + " " + city.getLongitude();
        } catch (IOException e) {
            throw new InvalidParameterException();
        }
    }

    /**
     * Constructor of favourite
     * @param zip The postal address of city you want in Favourite
     * @param country The country code of city you want in Favourite
     */
    public Favourite(String zip, String country){
        try {
            City city = new City((APIQuery.QueryWithZip(zip, country)));
            this.name = city.getName();

            this.LatLong = city.getLatitude() + " " + city.getLongitude();
        } catch (IOException e) {
            throw new InvalidParameterException();
        }
    }

    @Override
    public String toString() {
            return name + " " + LatLong;
        }

    @Override
    public boolean equals(Object obj) {
        Favourite other = (Favourite) obj;
        if (name.equals(other.name) && !LatLong.equals(other.LatLong)) return false;
        if (LatLong.equals(other.LatLong)) return true;
        return super.equals(obj);
    }

}
