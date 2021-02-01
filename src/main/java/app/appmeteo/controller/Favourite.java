package app.appmeteo.controller;

import java.io.*;
import java.nio.file.Paths;

public class Favourite {

    public static void addFavourite(String favourite) throws IOException {
        String data = reader();
        data += favourite;
        String[] dataString = data.split("\n");
        writter(dataString);
    }

    public static void deleteFavourite(String favourite) throws IOException {
        String data = reader();
        String[] dataString = data.split("\n");
        for (int i = 0; i < dataString.length; i++) {
            if (dataString[i].equals(favourite)){
                dataString[i] = "";
            }
        }
        writter(dataString);
    }


    public static void writter (String[] data) throws IOException {
        //write the new favourite with data
        BufferedWriter w = new BufferedWriter(new FileWriter("Favourite.txt"));
        for (int i = 0; i < data.length; i++) {
            if (!data[i].isEmpty()){
                w.append(data[i]);
                w.newLine();
            }
        }
    }

    public static String reader () throws IOException {
        //recuperation of data in new reload program
        BufferedReader r = new BufferedReader(new FileReader("favourite.txt"));
        String lineRead;
        String data = "";
        while ((lineRead = r.readLine()) != null){
            data = data + lineRead + "\n";
        }
        return data;
    }
}
