package app.appmeteo.controller;

import app.appmeteo.model.Favourite;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class FavouriteTest {

    @Test
    public void TestFav() throws IOException {

        Favourite fav = new Favourite("marseille");
        assertEquals("Arrondissement_de_Marseille", fav.getName());

        Favourite favZip = new Favourite("13140","FR");
        assertEquals("Miramas",favZip.getName());

        Favourite favLL = new Favourite(5.002927, 43.588271);
        assertEquals("Miramas",favLL.getName());
    }


}
