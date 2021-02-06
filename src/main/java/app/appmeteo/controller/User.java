package app.appmeteo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class User {

    private String[] query;
    private boolean hasDate;
    private final Scanner scanner;
    public FavouriteList favouriteList;

    public User(Scanner scanner) throws IOException {
        this.scanner = scanner;
        this.favouriteList = new FavouriteList();
    }

    /**
     * @param query the commands line
     */
    public User(String[] query) throws IOException {
        this.query = query;
        this.scanner = null;
        this.favouriteList = new FavouriteList();
    }

    public String[] getQuery() {
        return query;
    }

    public String[] getQuery(int from) {
        return Arrays.copyOfRange(query, from, query.length);
    }

    /**
     * This method is used to query a new command from the user thanks to the scanner instance
     */
    public void next(){
        String line = this.scanner.nextLine();
        this.query =  line.split(" ");
    }


    /**
     * the goal of this function is to compact the String array received through the scanner into a logical new array
     * with command in each array element. for example, this : [Los, Angeles, -temp, -wind] would become [Los Angeles, -temp, -wind]
     * it is currently public for testing purpose
     */
    public void fixCommandline() {
        // before options
        ArrayList<String> newCommandLine = new ArrayList<String>();

        for (String s : query) {
            if (s.charAt(0) == '-') {
                newCommandLine.add(s);
            }
            else {
                if (newCommandLine.size() == 0) newCommandLine.add(s);
                else newCommandLine.set(0, newCommandLine.get(0) + " " + s);
            }
        }
        query = newCommandLine.toArray(new String[0]);
    }

    // this [Los Angeles 25/10/21, -temp, -wind] become this: [Los Angeles, 25/10/21, -temp, -wind]
    public void fixCommandDate() {
    }

    public String getCommandType() {
        return query[0];
    }

    public int getQueryLength() {
        return query.length;
    }

    public boolean hasDate() {
        return hasDate;
    }
}
