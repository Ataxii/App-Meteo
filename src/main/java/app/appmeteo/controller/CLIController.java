package app.appmeteo.controller;

import java.util.ArrayList;

public class CLIController {

    // ATTRIBUTES
    private static ArrayList<String> displayableData = new ArrayList<>();

    private static boolean displayListExist() {
        return displayableData != null;
    }

    // ADD DATAS
    public static void addDisplay(String s) {
        if (displayListExist())
            displayableData.add(s);
    }

    // DISPLAY DATAS and clear it
    public static void displayData() {
        for (String s : displayableData) {
            System.out.println(s);
        }
        displayableData.clear();
    }


    // TODO DEPLACER AU BON ENDROIT
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
