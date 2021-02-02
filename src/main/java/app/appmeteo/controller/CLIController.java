package app.appmeteo.controller;

import app.appmeteo.model.Weather;

import java.sql.Timestamp;
import java.util.ArrayList;
import app.appmeteo.controller.Commands.*;
import java.util.Date;

public class CLIController {

    // place each thing to display into that. it will be send to AppMeteoCLI for display on console
    private ArrayList<String> displayableData;
    private UserQuery query;

    public CLIController(UserQuery query) {
        this.query = query;
    }

    public void treatQuery() {
        if (!isWeatherQuery()) {
            treatCommandQuery();
        }
        else treatWeatherQuery();
    }

    private boolean isWeatherQuery() {
        return false;
    }

    // fav help quit, YOAN
    private void treatCommandQuery() {
        String command = query.getCommandType();

        switch (command) {
            case Commands.CommandType.FAV:

        }
    }

    private void treatWeatherQuery() {

    }

    private Date getDate(long time){
        return new Timestamp(time);
    }

    private String getWindOrientation(int winddegree) {

        if(winddegree >= 11 && winddegree < 34){
            return "NNE";
        }
        if(winddegree >= 34 && winddegree < 57){
            return "NE";
        }
        if(winddegree >= 57 && winddegree < 79){
            return "ENE";
        }
        if(winddegree >= 79 && winddegree < 102){
            return "E";
        }
        if(winddegree >= 102 && winddegree < 124){
            return "ESE";
        }
        if(winddegree >= 124 && winddegree < 147){
            return "SE";
        }
        if(winddegree >= 147 && winddegree < 169){
            return "SSE";
        }
        if(winddegree >= 169 && winddegree < 192){
            return "S";
        }
        if(winddegree >= 192 && winddegree < 214){
            return "SSW";
        }
        if(winddegree >= 214 && winddegree < 237){
            return "SW";
        }
        if(winddegree >= 237 && winddegree < 259){
            return "WSW";
        }
        if(winddegree >= 259 && winddegree < 282){
            return "W";
        }
        if(winddegree >= 282 && winddegree < 304){
            return "WNW";
        }
        if(winddegree >= 304 && winddegree < 327){
            return "NW";
        }
        if(winddegree >= 327 && winddegree < 349){
            return "NNW";
        }
        return "N";
    }
}
