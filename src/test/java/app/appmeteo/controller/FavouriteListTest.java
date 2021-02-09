package app.appmeteo.controller;

import app.appmeteo.model.Favourite;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class FavouriteListTest {

    @Test
    public void TestFavList() throws IOException {

        String data = "Miramas 5.0 43.5833\n" +
                "Paris 2.3488 48.8534\n";
        File file = new File("bonsoir");
        BufferedWriter w = new BufferedWriter(new FileWriter(file));
        w.write(data);
        w.close();
        FavouriteList favList = new FavouriteList(file);
        assertEquals("Miramas", favList.getFavouriteList().get(0).getName());
        assertEquals("5.0 43.5833", favList.getFavouriteList().get(0).getLatLong());
        assertEquals("Paris", favList.getFavouriteList().get(1).getName());
        assertEquals("2.3488 48.8534", favList.getFavouriteList().get(1).getLatLong());

        favList.addFavourite("Marseille");
        assertEquals(3, favList.getFavouriteList().size());
        assertEquals("Arrondissement_de_Marseille", favList.getFavouriteList().get(2).getName());
        assertEquals("5.5 43.3333", favList.getFavouriteList().get(2).getLatLong());

        favList.write();
        BufferedReader r = new BufferedReader(new FileReader(file));
        String expectedData = data + "Arrondissement_de_Marseille " + "5.5 43.3333";
        String actualData = r.readLine()+ "\n" + r.readLine()+ "\n" + r.readLine();
        assertEquals(expectedData, actualData);

        favList.delFavouriteByName("Arrondissement_de_Marseille");
        assertEquals(2, favList.getFavouriteList().size());
        favList.write();
    }


}