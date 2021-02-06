package app.appmeteo.model;

import app.appmeteo.controller.APIQuery;

import java.io.IOException;
import java.security.InvalidParameterException;

public class Favourite {

    // TODO ATTRIBUTS LONG LAT
    // TODO SUPPRIMER ATTRRIBUT ID
    // TODO COSNTRUCTEUR COUNTRY CODE + ZIP CODE
    // TODO CONSTRUCTEUR COUNTRY CODE + CITY NAME


    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    private String name;
    private String id;

    public Favourite(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Favourite(String name) {
        try {
            this.name = name;
            City city = new City((APIQuery.QueryStringWithCity(name)));
            this.id = city.getId();
        } catch (IOException e) {
            throw new InvalidParameterException();
        }
    }

    @Override
    public String toString() {
        return name + " " + id;
    }

    @Override
    public boolean equals(Object obj) {
        Favourite other = (Favourite) obj;
        if (name.equals(other.name) && !id.equals(other.id)) return false;
        if (id.equals(other.id)) return true;
        return super.equals(obj);
    }
}
