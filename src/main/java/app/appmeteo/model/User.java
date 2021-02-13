package app.appmeteo.model;

import app.appmeteo.controller.FavouriteList;
import app.appmeteo.controller.UserQuery;

import java.io.IOException;
import java.util.Scanner;

public class User {

    private UserQuery query;
    private final Scanner scanner;
    private FavouriteList favouriteList;

    public User(Scanner scanner) throws IOException {
        this.scanner = scanner;
        this.favouriteList = new FavouriteList();
        this.query = new UserQuery(new String[0]);
    }

    /**
     * @param commandLine the commands line
     */
    public User(String[] commandLine) throws IOException {
        this.query = new UserQuery(commandLine);
        this.scanner = null;
        this.favouriteList = new FavouriteList();
    }

    public UserQuery getQuery() {
        return this.query;
    }

    public void setQuery(UserQuery query) {
        this.query = query;
    }

    public FavouriteList getFavouriteList() {
        return favouriteList;
    }

    public void setFavouriteList(FavouriteList favouriteList) {
        this.favouriteList = favouriteList;
    }

    /**
     * This method is used to query a new command from the user thanks to the scanner instance
     */
    public void next() {
        String line = this.scanner.nextLine();
        this.query.setCommandLine(line.split(" "));
        this.query.setDate(null);
    }

}

