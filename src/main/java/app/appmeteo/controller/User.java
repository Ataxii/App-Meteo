package app.appmeteo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    private String[] command;
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
        this.command = query;
        this.scanner = null;
        this.favouriteList = new FavouriteList();
    }

    public String[] getQuery() {
        return command;
    }

    public String[] getQuery(int from) {
        return Arrays.copyOfRange(command, from, command.length);
    }

    public void setQuery(int from) {
        command = Arrays.copyOfRange(command, from, command.length);
    }

    /**
     * This method is used to query a new command from the user thanks to the scanner instance
     */
    public void next(){
        String line = this.scanner.nextLine();
        this.command =  line.split(" ");
    }


    /**
     * the goal of this function is to compact the String array received through the scanner into a logical new array
     * with command in each array element. for example, this : [Los, Angeles, -temp, -wind] would become [Los Angeles, -temp, -wind]
     * it is currently public for testing purpose
     */
    public void fixCommandline() {
        // before options
        ArrayList<String> newCommandLine = new ArrayList<String>();

        for (String s : command) {
            if (s.charAt(0) == '-') {
                newCommandLine.add(s);
            }
            else {
                if (newCommandLine.size() == 0) newCommandLine.add(s);
                else newCommandLine.set(0, newCommandLine.get(0) + " " + s);
            }
        }
        command = newCommandLine.toArray(new String[0]);
    }

    // this [Los Angeles 25/10/21, -temp, -wind] become this: [Los Angeles, 25/10/21, -temp, -wind]
    public void fixCommandDate() {
        ArrayList<String> newCommandLine = new ArrayList<>();
        String commandType = this.getCommandType();
        Pattern dateFormat = Pattern.compile("(\\d{2})(\\/)(\\d{2})(\\/)\\d{4}");
        Matcher matcher = dateFormat.matcher(commandType);
        if(matcher.find()){
            this.hasDate = true;
            newCommandLine.add(commandType.substring(0,matcher.start()-1));
            newCommandLine.add(commandType.substring(matcher.start()));

        } else {
            int breakIndex = commandType.length();
            for(int index = 0; index<commandType.length(); index++){
                if(Character.isDigit(commandType.charAt(index))){
                    breakIndex = index-1;
                    break;
                }
            }
            newCommandLine.add(commandType.substring(0,breakIndex));
        }
        newCommandLine.addAll(Arrays.asList(this.command).subList(1, this.command.length));

        System.out.println(Arrays.deepToString(newCommandLine.toArray(this.command)));
        this.command = newCommandLine.toArray(this.command);



    }

    public String getCommandType() {
        return command[0];
    }

    public int getQueryLength() {
        return command.length;
    }

    public boolean hasDate() {
        return hasDate;
    }

    public ArrayList<Integer> getDate(){
        ArrayList<Integer> date = new ArrayList<>();
        if(hasDate()){
           for(String data : this.command[1].split("/")){
               date.add(Integer.parseInt(data));
           }
        }
        return date;
    }

}
