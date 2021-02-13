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


    public UserQuery(String[] commandLine) {
        this.commandLine = commandLine;
        this.date = null;
    }


    public void setCommandLine(String[] commandLine) {
        this.commandLine = commandLine;
    }

    public void setCommandLineOption(int index, String newOption) {
        if (0 > index || index > commandLine.length) throw new InvalidParameterException();
        commandLine[index] = newOption;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String[] getCommandLine() {
        return commandLine;
    }

    public String getCommandLineOption(int index) {
        if (0 > index || index > commandLine.length) throw new InvalidParameterException();
        return commandLine[index];
    }

    public Date getDate() {
        return date;
    }

    public boolean hasDate() {
        return date != null;
    }

    public int getCommandLineLength() {
        return commandLine.length;
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
        // before options
        ArrayList<String> newCommandLine = new ArrayList<String>();

        for (String s : commandLine) {
            if (s.charAt(0) == '-') {
                newCommandLine.add(s);
            } else {
                if (newCommandLine.size() == 0) newCommandLine.add(s);
                else newCommandLine.set(0, newCommandLine.get(0) + " " + s);
            }
        }
        commandLine = newCommandLine.toArray(new String[0]);
        fixCommandSelectors();
        fixDate();
    }

    // this [Los Angeles 25/10/21, -temp, -wind] become this: [Los Angeles, 25/10/21, -temp, -wind]
    public void fixDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (int index = 0; index < commandLine.length; index++) {
            if (commandLine[index].contains(String.valueOf('/'))) {
                try {
                    setDate(sdf.parse(commandLine[index]));
                    sdf.setLenient(false);
                } catch (ParseException e) {
                    CLIController.addDisplay("Oops... Date Invalid...  ");
                }
                commandLine[index] = date.toString();
            }
        }

    }

    public void fixCommandSelectors() {
        ArrayList<String> newCommandLine = new ArrayList<>();
        String commandType = commandLine[0];
        int prevIndex = 0;
        for (int index = 0; index < commandType.length(); index++) {
            if (commandType.charAt(index) == ' ' && Character.isDigit(commandType.charAt(index + 1))) {
                newCommandLine.add(commandType.substring(prevIndex, index));
                prevIndex = index + 1;
            }
        }
        newCommandLine.add(commandType.substring(prevIndex));
        newCommandLine.addAll(Arrays.asList(commandLine).subList(1, commandLine.length));
        commandLine = newCommandLine.toArray(commandLine);
    }


}
