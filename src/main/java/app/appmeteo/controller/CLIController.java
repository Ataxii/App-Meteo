package app.appmeteo.controller;

import javax.management.Query;

public class CLIController {

    // place each thing to display into that. it will be send to AppMeteoCLI for display on console
    private String[] displayableDatas;

    public static void treatQuery(Query query) {

    }

    private boolean isWeatherQuery(Query query) {
        return false;
    }

    // fav help quit, YOAN
    private static void treatCommandQuery(Query query) {

    }

    private static void treatWeatherQuery(Query query) {

    }
}
