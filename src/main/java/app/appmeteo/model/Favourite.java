package app.appmeteo.model;

import app.appmeteo.controller.APIQuery;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Locale;

public class Favourite {

    public String getName() {
        return name;
    }

    public String getCountryCode() { return countryCode; }


    private String name;
    private String countryCode;


    /**
     * Constructor of favourite
     * @param cityName name of city you want in Favourite
     */
    public Favourite(String cityName) {
        try {
            City city = new City(APIQuery.QueryWithCity(cityName));
            name = cityName.toLowerCase().replace(" ", "_");
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
            name = cityName.toLowerCase().replace(" ", "_");
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
        return name.equals(other.name) && countryCode.equals(other.countryCode);
    }

}
