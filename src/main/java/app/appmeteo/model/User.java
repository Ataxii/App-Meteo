package app.appmeteo.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    private UserQuery query;
    private final Scanner scanner;
    public FavouriteList favouriteList;

    public User(Scanner scanner) throws IOException {
        this.scanner = scanner;
        this.favouriteList = new FavouriteList();
        this.query = new UserQuery(new String[0]);
    }

    /**
     * @param query the commands line
     */
    public User(String[] query) throws IOException {
        this.query = new UserQuery(query);
        this.scanner = null;
        this.favouriteList = new FavouriteList();
    }

    public UserQuery getQuery() {
        return this.query;
    }

    public String[] getQuery(int from) {
        return Arrays.copyOfRange(this.query.getCommand(), from, this.query.getQueryLength());
    }

    public void setQuery(int from) {
        this.query.setCommand(Arrays.copyOfRange(this.query.getCommand(), from, this.query.getQueryLength()));
    }

    /**
     * This method is used to query a new command from the user thanks to the scanner instance
     */
    public void next(){
        String line = this.scanner.nextLine();
        this.query.setCommand(line.split(" "));
    }




}
