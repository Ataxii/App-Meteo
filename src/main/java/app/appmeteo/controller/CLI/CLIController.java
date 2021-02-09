package app.appmeteo.controller.CLI;

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

}
