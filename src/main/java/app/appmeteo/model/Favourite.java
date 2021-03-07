package app.appmeteo.model;

import app.appmeteo.controller.APIQuery;

import java.io.IOException;


public class Favourite {

    private final String name;
    private final String countryCode;

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Constructor of a favorite with a city in argument. Assuming city's data are correct, it does not check
     * the correctness of the info by doing an API query.
     * Used for GUI
     *
     * @param city
     */
    public Favourite(City city) {
        name = city.getName().toLowerCase().replace(" ", "_");
        countryCode = city.getCountry();
    }

    /**
     * @param cityName
     * @throws IOException if the city does not exist or the API is not responding
     */
    public Favourite(String cityName) throws IOException {
        City city = new City(APIQuery.QueryWithCity(cityName.replace("_", " ")));
        name = city.getName().toLowerCase().replace(" ", "_");
        countryCode = city.getCountry();
    }

    /**
     * @param cityName
     * @param country  - the country code of the city (FR, US, JP...)
     * @throws IOException if the city does not exist or the API is not responding
     */
    public Favourite(String cityName, String country) throws IOException {
        City city = new City((APIQuery.QueryWithCountryCode(cityName.replace("_", " "), country)));
        name = city.getName().toLowerCase().replace(" ", "_");
        countryCode = city.getCountry();
    }

    @Override
    public String toString() {
        return name.replace("_", " ") + " " + countryCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this.getClass())) return false;
        Favourite other = (Favourite) obj;
        return name.equals(other.name) && countryCode.equals(other.countryCode);
    }

}
