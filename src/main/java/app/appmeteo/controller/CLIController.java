package app.appmeteo.controller;

import java.io.IOException;
import java.util.ArrayList;

import app.appmeteo.controller.Commands.*;

public class CLIController {

    // ATTRIBUTES
    private static ArrayList<String> displayableData;
    private UserQuery query;

    private boolean hasToStop;
    private Favourite favourite;

    // CONSTRUCTOR
    public CLIController(UserQuery query) throws IOException {
        this.query = query;
        displayableData = new ArrayList<>();
        hasToStop = false;
        favourite = new Favourite();
    }


    // ACCESSORS
    public boolean hasToStop() {
        return hasToStop;
    }

    private static boolean displayListExist() {
        return displayableData != null;
    }

    // MODIFY DATAS
    public static void addDisplay(String s) {
        if (displayListExist())
            displayableData.add(s);
    }

    // DISPLAY DATAS
    public void displayData() {
        for (String s : displayableData) {
            System.out.println(s);
        }
        System.out.println();
    }


    // TREAT USER INPUTS
    public void treatQuery() {
        displayableData.clear();
        if (!isWeatherQuery()) treatCommandQuery();
        else treatWeatherQuery();
    }


    private boolean isWeatherQuery() {
        return false;
    }


    private void treatCommandQuery() {
        String command = query.getCommandType();

        switch (command) {
            case CommandType.FAV: {
                treatFavCase();
                return;
            }
            case CommandType.HELP: {

            }
            case CommandType.QUIT: {
                treatQuitCase();
                return;
            }
        }
    }

    private void treatFavCase() {
        // case only fav -> display favorites list. at this point, it is asserted that query.getQuery[0] is "fav"
        if (query.getQuery().length == 1) {
            displayableData.addAll(favourite.getFavourites());
            return;
        }
        String command = query.getQuery()[1];                               // get command

        if (query.getQueryLength() == 2) {
            displayableData.add("Sorry but you specified no town");
            return;
        }
        String town = query.getQuery()[2];                                  // get town to add/del to/from favs

        switch (command) {
            case FavouritesCommands.ADD: {
                favourite.addFavourite(town);
                return;
            }                                                               // process command
            case FavouritesCommands.DEL: {
                favourite.delFavourite(town);
                return;
            }
        }
    }

    private void treatQuitCase() {
        // call write favourites
        hasToStop = true;
    }

    private void treatWeatherQuery() {

    }

    private String getWindOrientation(int winddegree) {

        if (winddegree >= 11 && winddegree < 34) {
            return "NNE";
        }
        if (winddegree >= 34 && winddegree < 57) {
            return "NE";
        }
        if (winddegree >= 57 && winddegree < 79) {
            return "ENE";
        }
        if (winddegree >= 79 && winddegree < 102) {
            return "E";
        }
        if (winddegree >= 102 && winddegree < 124) {
            return "ESE";
        }
        if (winddegree >= 124 && winddegree < 147) {
            return "SE";
        }
        if (winddegree >= 147 && winddegree < 169) {
            return "SSE";
        }
        if (winddegree >= 169 && winddegree < 192) {
            return "S";
        }
        if (winddegree >= 192 && winddegree < 214) {
            return "SSW";
        }
        if (winddegree >= 214 && winddegree < 237) {
            return "SW";
        }
        if (winddegree >= 237 && winddegree < 259) {
            return "WSW";
        }
        if (winddegree >= 259 && winddegree < 282) {
            return "W";
        }
        if (winddegree >= 282 && winddegree < 304) {
            return "WNW";
        }
        if (winddegree >= 304 && winddegree < 327) {
            return "NW";
        }
        if (winddegree >= 327 && winddegree < 349) {
            return "NNW";
        }
        return "N";
    }
}
