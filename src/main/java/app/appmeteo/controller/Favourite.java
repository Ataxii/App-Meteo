package app.appmeteo.controller;

import java.io.*;

public class Favourite {

    public static boolean addFavourite(String favourite) throws IOException {
       try{
           APIQuery.QueryStringWithCity(favourite);
       }
       catch (FileNotFoundException e){
           return false;
       }
       if (isFavourite(favourite)){
           System.out.println("Is already in your favourite");
       }else {
           String data = reader();
           data += favourite;
           String[] dataString = data.split("\n");
           writter(dataString);
       }
       return true;
    }

    public static void deleteFavourite(String favourite) throws IOException {
        if (!isFavourite(favourite)){
            System.out.println("Is not already in your favourite");
        }
        else {
            String data = reader();
            String[] dataString = data.split("\n");
            for (int i = 0; i < dataString.length; i++) {
                if (dataString[i].equals(favourite)){
                    dataString[i] = "";
                }
            }
            writter(dataString);
        }
    }

    public static boolean isFavourite (String fav) throws IOException {
        String data = reader();
        String[] dataString = data.split("\n");
        for (String s : dataString) {
            if (s.equals(fav)) {
                return true;
            }
        }
        return false;
    }

    private static void writter (String[] data) throws IOException {
        //write the new favourite with data
        BufferedWriter w = new BufferedWriter(new FileWriter("Favourite.txt"));
        for (String datum : data) {
            if (!datum.isEmpty()) {
                w.append(datum);
                w.newLine();
            }
        }
    }

    private static String reader() throws IOException {
        //recuperation of data in new reload program
        BufferedReader r = new BufferedReader(new FileReader("favourite.txt"));
        String lineRead;
        String data = "";
        while ((lineRead = r.readLine()) != null){
            data = data + lineRead + "\n";
        }
        return data;
    }

    public static String[] FavouriteList () throws IOException {
        return reader().split("\n");
    }
}
