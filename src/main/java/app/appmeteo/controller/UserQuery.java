package app.appmeteo.controller;

import app.appmeteo.controller.CLI.CLIController;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class UserQuery {

    private String[] commandLine;
    private Date date;
    private String countryCode;
    private int zipCode;


    public UserQuery(String[] commandLine) {
        this.commandLine = commandLine;
        this.date = null;
        this.countryCode = "";
        this.zipCode = 0;
    }


    public void setCommandLine(String[] commandLine) {
        this.commandLine = commandLine;
    }

    public void setCommandLineOption(int index, String newOption) {
        if (0 > index || index > commandLine.length) throw new InvalidParameterException();
        commandLine[index] = newOption;
    }

    public String[] getCommandLine() {
        return commandLine;
    }

    public String getCommandLineOption(int index) {
        if (0 > index || index > commandLine.length) throw new InvalidParameterException();
        return commandLine[index];
    }

    public int getCommandLineLength() {
        return commandLine.length;
    }

    public Date getDate() {
        return date;
    }

    public String getCountryCode() { return countryCode; }

    public int getZipCode() { return zipCode; }

    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

    public void setZipCode(int zipCode) { this.zipCode = zipCode; }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean hasCountryCode(){ return !this.countryCode.equals(""); }

    public boolean hasZipCode(){ return !(this.zipCode==0); }

    public boolean hasDate() {
        return date != null;
    }

    public ArrayList<String> getOptions() {
        ArrayList<String> options = new ArrayList<>();
        for (String s : commandLine) {
            if (s.charAt(0) == '-') options.add(s);
        }
        return options;
    }

    /**
     * the goal of this function is to compact the String array received through the scanner into a logical new array
     * with command in each array element. for example, this : [Los, Angeles, -temp, -wind] would become [Los Angeles, -temp, -wind]
     * it is currently public for testing purpose
     */
    public void fixCommandline() {
        for (int index = 0; index<getCommandLineLength(); index++) {
            String s = commandLine[index];
            if(s.contains(String.valueOf('_'))) commandLine[index] = s.replace('_',' ');
        }
        fixCommandSelectors();
        fixDate();
    }

    public void fixDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (int index = 0; index < commandLine.length; index++) {
            if (commandLine[index].contains(String.valueOf('/'))) {
                try {
                    setDate(sdf.parse(commandLine[index]));
                    sdf.setLenient(false);
                } catch (ParseException e) {
                    CLIController.addDisplay("Oops... Invalid Date...  ");
                }
                commandLine[index] = date.toString();
            }
        }

    }

    public void fixCommandSelectors() {
        for (int index = 0; index < this.getCommandLineLength(); index++) {
            String s = commandLine[index];
            if(s.length()==2){ this.setCountryCode(s); }
            if(Character.isDigit(s.charAt(0)) && !s.contains(String.valueOf('/'))){ this.setZipCode(Integer.parseInt(s)); }
        }
    }



}
