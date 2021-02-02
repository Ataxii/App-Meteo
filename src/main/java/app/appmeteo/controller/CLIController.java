package app.appmeteo.controller;

import java.util.ArrayList;

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

    }

    private void treatWeatherQuery() {

    }

    private String getWindOrientation(int winddegree) {
        return "";
    }
}
