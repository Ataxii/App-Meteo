package app.appmeteo.controller;

import app.appmeteo.controller.Favourite;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class FavouriteTest {
    //for this test, modifie the path in Favourite with : src/test/java/app/appmeteo/controller/FavouriteTest/origin.txt
    //and for the origin.txt :
    // marseille
    //paris
    //lille
    //salon

    @Test
    public void TestFav() throws IOException {

        //test for favourite list
        String[] originFile = new String[4];
        originFile[0] = "marseille";
        originFile[1] = "paris";
        originFile[2] = "lille";
        originFile[3] = "salon";

        String[] reader = Favourite.FavouriteList();
        for (int i = 0; i < originFile.length; i++) {
            assertEquals(reader[i], originFile[i]);
        }


        //test isFavourite
        assertTrue(Favourite.isFavourite("lille"));
        assertFalse(Favourite.isFavourite("lill"));

        //test addFavourite

        String[] originFile2 = new String[5];
        originFile2[0] = "marseille";
        originFile2[1] = "paris";
        originFile2[2] = "lille";
        originFile2[3] = "salon";
        originFile2[4] = "aubagne";

        assertTrue(Favourite.addFavourite("aubagne"));

        String[] reader2 = Favourite.FavouriteList();
        for (int i = 0; i < originFile2.length; i++) {
            assertEquals(reader2[i], originFile2[i]);
        }

        //test deleteFavourite
        String[] originFile3 = new String[4];
        originFile3[0] = "marseille";
        originFile3[1] = "lille";
        originFile3[2] = "salon";
        originFile3[3] = "aubagne";

        Favourite.deleteFavourite("paris");

        String[] reader3 = Favourite.FavouriteList();
        for (int i = 0; i < originFile3.length; i++) {
            assertEquals(reader3[i], originFile3[i]);
        }
    }
}
