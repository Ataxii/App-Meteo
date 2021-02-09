package app.appmeteo.controller;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserQuery {

    private String[] command;
    private boolean hasDate;

    public UserQuery(String[] command) {
        this.command = command;
        hasDate = false;
    }


    // ACCESSEURS
    public String[] getCommand() {
        return command;
    }

    public boolean hasDate() {
        return hasDate;
    }

    public String getCommandAtIndex(int index) {
        if (0 > index || index >= command.length) throw new InvalidParameterException();
        return command[index];
    }

    public String getCommandType() {
        return command[0];
    }

    public int getCommandLength() {
        return command.length;
    }

    public int getQueryLength() {
        return command.length;
    }

    public ArrayList<String> getOptions() {
        ArrayList<String> options = new ArrayList<>();
        for (String s : this.command) {
            if (s.charAt(0) == '-') options.add(s);
        }
        return options;
    }

    public Date getDate() {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(this.command[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // SETTEURS
    public void setCommand(String[] command) {
        this.command = command;
    }

    public void setHasDate(boolean hasDate) {
        this.hasDate = hasDate;
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
            } else {
                if (newCommandLine.size() == 0) newCommandLine.add(s);
                else newCommandLine.set(0, newCommandLine.get(0) + " " + s);
            }
        }
        command = newCommandLine.toArray(new String[0]);
        this.fixCommandDate();
    }

    // this [Los Angeles 25/10/21, -temp, -wind] become this: [Los Angeles, 25/10/21, -temp, -wind]
    public void fixCommandDate() {
        ArrayList<String> newCommandLine = new ArrayList<>();
        String commandType = this.getCommandType();
        Pattern dateFormat = Pattern.compile("(\\d{2})(\\/)(\\d{2})(\\/)\\d{4}");
        Matcher matcher = dateFormat.matcher(commandType);
        if (matcher.find()) {
            this.hasDate = true;
            newCommandLine.add(commandType.substring(0, matcher.start() - 1));
            newCommandLine.add(commandType.substring(matcher.start()));

        } else {
            int breakIndex = commandType.length();
            for (int index = 0; index < commandType.length(); index++) {
                if (Character.isDigit(commandType.charAt(index))) {
                    breakIndex = index - 1;
                    break;
                }
            }
            newCommandLine.add(commandType.substring(0, breakIndex));
        }
        newCommandLine.addAll(Arrays.asList(this.command).subList(1, this.command.length));
        this.command = newCommandLine.toArray(this.command);
    }


}
