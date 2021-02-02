package app.appmeteo.controller;

import java.io.File;
import java.util.Scanner;

public class Query {
    private String[] commandLine;
    private final Scanner scanner;

    public enum Command{
        HELP("help"),
        TEMP("temp"),
        WEATHER("weather"),
        WIND("wind"),
        STOP("quit");


        private final String commandName;

        Command(String commandName){
            this.commandName = commandName;
        }

        public String getCommandName(){return this.commandName;}
    }

    public Query(Scanner scanner) {
        this.scanner = scanner;
    }

    public String[] getCommandLine() {
        return commandLine;
    }

    public String getCommand(){
        return this.commandLine[0];
    }

    public String getParameter(){
        String parameter = "";
        if(this.commandLine.length>1){
            for(int index=1; index<commandLine.length;index++){
                if(commandLine[index].charAt(0)!='-'){
                    if(index>1) parameter += ' ';
                    parameter+=commandLine[index];
                }
            }
        }
        return parameter;
    }

    public boolean isValid(){
        for (Command command : Command.values()){
            if(this.getCommand().equals(command.getCommandName())) return true;
        }
        return false;
    }

    public void next(){
        String line = this.scanner.nextLine();
        this.commandLine =  line.split(" ");
    }


}
