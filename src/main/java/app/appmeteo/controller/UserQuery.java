package app.appmeteo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class UserQuery {

    private String[] query;
    private boolean hasDate;
    private final Scanner scanner;

    public UserQuery(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * for testing purpose
     * @param query
     */
    public UserQuery(String[] query) {
        this.query = query;
        this.scanner = null;
    }

    public String[] getQuery() {
        return query;
    }

    public void next(){
        String line = this.scanner.nextLine();
        this.query =  line.split(" ");
    }


    /**
     * the goal of this function is to compact the String array received through the scanner into a logical new array
     * with command in each array element. for example, this : [Los, Angeles, -temp, -wind] would become [Los Angeles, -temp, -wind]
     * it is public for testing purpose
     */
    public void fixCommandline() {
        // before options
        String com = "";
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
        query = newCommandLine.toArray(query);
    }

    public void fixCommandDate() {

    }

    public String getCommandType() {
        return query[0];
    }

    public boolean hasDate() {
        return hasDate;
    }
}
