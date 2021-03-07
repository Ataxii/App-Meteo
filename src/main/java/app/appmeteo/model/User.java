package app.appmeteo.model;

import java.io.IOException;
import java.util.Scanner;

public class User {

    private UserQuery query;
    private final Scanner scanner;
    private FavouriteList favouriteList;

    /**
     * @param commandLine the commands line
     * @throws IOException
     */
    public User(String[] commandLine) throws IOException {
        this.query = new UserQuery(commandLine);
        this.scanner = new Scanner(System.in);
        this.favouriteList = new FavouriteList();
    }

    public UserQuery getQuery() {
        return this.query;
    }

    public void setQuery(String[] command) {
        query = new UserQuery(command);
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
        this.query.setZipCode(0);
        this.query.setCountryCode("");
    }

}

