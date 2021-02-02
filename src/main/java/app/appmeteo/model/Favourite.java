package app.appmeteo.model;

import java.io.*;
import java.nio.file.Paths;

public class Favourite {

    public static void addFavourite(String favourite) throws IOException {
        BufferedWriter w = new BufferedWriter(new FileWriter("Favourite.txt"));
        w.append(favourite);
        w.newLine();
        w.close();
    }

    public static void deleteFavourite(String favourite) throws IOException {
        String lineRead;
        File newFile = new File("newfavourite.txt");
        File oldFile = new File("Favourite.txt");
        BufferedWriter w = new BufferedWriter(new FileWriter(newFile));
        BufferedReader r = new BufferedReader(new FileReader(oldFile));
        while ((lineRead = r.readLine()) != null){
            if (!lineRead.equals(favourite)){
                w.append(lineRead);
            }
        }
        w.close();
        r.close();
        oldFile.delete();
        newFile.renameTo(oldFile);
        newFile.delete();
    }
}
