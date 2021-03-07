package app.appmeteo.model;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

public class FavouriteListTest {

    @Test
    public void TestFavList() throws IOException {
        File test_file = new File("./src/test/resources/fake_favourite.txt");
        String fake_favourite_txt_data = "paris FR\nmarseille\nlille FR";
        BufferedWriter wrt = new BufferedWriter(new FileWriter(test_file));
        wrt.write(fake_favourite_txt_data);
        wrt.close();

        // list creation test
        FavouriteList favouriteListTest = new FavouriteList(test_file);
        Favourite favourite = new Favourite("paris", "FR");
        Favourite favourite1 = new Favourite("lille", "FR");

        assertEquals(favourite, favouriteListTest.getFavouriteAtIndex(0));
        assertEquals(favourite1, favouriteListTest.getFavouriteAtIndex(1));
        assertFalse(favouriteListTest.getList().contains(new Favourite("marseille")));


        // list add test
            // cannot add a favourite with same name
        assertThrows(InvalidParameterException.class, () -> favouriteListTest.addFavourite("paris"));
            // can with different country code
        assertDoesNotThrow(() -> favouriteListTest.addFavourite("paris", "US"));
            // paris US should be added
        Favourite favourite2 = new Favourite("paris", "US");
        assertTrue(favouriteListTest.getList().contains(favourite2));
            // now should contain three favourites


        // list remove test
        assertThrows(IndexOutOfBoundsException.class, () -> favouriteListTest.removeFavourite(3));
        favouriteListTest.removeFavourite(2);
        assertFalse(favouriteListTest.getList().contains(favourite2));


        // list write test
        favouriteListTest.write();
        String expected_datas = "paris FR\nlille FR";
        BufferedReader rdr = new BufferedReader(new FileReader(test_file));
        String actual_datas = rdr.readLine()+ "\n" + rdr.readLine();
        rdr.close();
        assertEquals(expected_datas, actual_datas);

        // empty txt file
        wrt = new BufferedWriter(new FileWriter(test_file));
        wrt.write("");
        wrt.close();
    }
}