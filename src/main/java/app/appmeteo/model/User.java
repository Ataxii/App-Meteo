package app.appmeteo.model;

import app.appmeteo.controller.FavouriteList;
import app.appmeteo.controller.UserQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class User {

    private UserQuery query;
    private final Scanner scanner;
    public FavouriteList favouriteList;

    public User(Scanner scanner) throws IOException {
        this.scanner = scanner;
        this.favouriteList = new FavouriteList();
        this.query = new UserQuery(new String[0]);
    }

    public User(String[] query) throws IOException {
        this.query = new UserQuery(query);
        this.scanner = null;
        this.favouriteList = new FavouriteList();
    }

    // ACCESSEURS
    public UserQuery getQuery() {
        return query;
    }

    public String[] getCommand() {
        return query.getCommand();
    }

    public int getCommandLength() {
        return query.getCommandLength();
    }

    public String getCommandAtIndex(int index) {
        return query.getCommandAtIndex(index);
    }

    public String[] getCommandFrom(int from) {
        return Arrays.copyOfRange(getCommand(), from, this.query.getQueryLength());
    }

    public boolean dateHasToBeTreated() {
        return query.hasDate();
    }

    public void setCommandFrom(int from) {
        this.query.setCommand(Arrays.copyOfRange(this.query.getCommand(), from, this.query.getQueryLength()));
    }

    public FavouriteList getFavouriteList() {
        return favouriteList;
    }

    /**
     * This method is used to query a new command from the user thanks to the scanner instance
     */
    public void next() {
        String line = this.scanner.nextLine();
        this.query.setCommand(line.split(" "));
        this.query.setHasDate(false);
    }

}
