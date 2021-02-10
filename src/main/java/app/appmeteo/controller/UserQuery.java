package app.appmeteo.controller;

import app.appmeteo.controller.CLI.CLIController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class UserQuery {

    private String[] command;
    private Date date;


    public UserQuery(String[] command) {
        this.command = command;
        this.date = null;
    }

    public void setCommand(String[] command) {
        this.command = command;
    }

    public String[] getCommand() {
        return command;
    }


    public String getCommandType() {
        return command[0];
    }

    public int getQueryLength() {
        return command.length;
    }

    public boolean hasDate() {
        return this.date!=null;
    }


    public Date getDate(){
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<String> getOptions(){
        ArrayList<String> options = new ArrayList<>();
        for(String s:this.command){
            if(s.charAt(0)=='-') options.add(s);
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
        this.fixCommandSelectors();
        this.fixDate();
    }

    // this [Los Angeles 25/10/21, -temp, -wind] become this: [Los Angeles, 25/10/21, -temp, -wind]
    public void fixDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for(int index = 0; index<command.length; index++){
            if(command[index].contains(String.valueOf('/'))){
                try {
                    this.setDate(sdf.parse(command[index]));
                    sdf.setLenient(false);
                } catch (ParseException e) {
                    CLIController.addDisplay("Oops... Date Invalid...  ");
                }
                command[index] = this.date.toString();
            }
        }

    }

    public void fixCommandSelectors(){
        ArrayList<String> newCommandLine = new ArrayList<>();
        String commandType = this.getCommandType();
        int prevIndex=0;
        for(int index = 0; index<commandType.length(); index++){
            if(commandType.charAt(index)==' ' && Character.isDigit(commandType.charAt(index+1))){
                newCommandLine.add(commandType.substring(prevIndex,index));
                prevIndex = index+1;
            }
        }
        newCommandLine.add(commandType.substring(prevIndex));
        newCommandLine.addAll(Arrays.asList(this.command).subList(1, this.command.length));
        this.command = newCommandLine.toArray(this.command);
    }



}
