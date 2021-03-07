package app.appmeteo.model;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class FavouriteTest {

    @Test
    public void TestFav() throws IOException {
        // non existent city
        assertThrows(IOException.class, () -> new Favourite("dzgd"));

        // existent city
        Favourite fav = new Favourite("marseille");
        assertEquals("marseilleFR", fav.getName() + fav.getCountryCode());

        // existent city with country code
        Favourite fav1 = new Favourite("marseille","FR");
        assertEquals("marseilleFR",fav1.getName() + fav1.getCountryCode());
    }


}
