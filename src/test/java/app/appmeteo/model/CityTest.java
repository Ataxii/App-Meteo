package app.appmeteo.model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class CityTest {

    private City city = new City(new File("test.json"));

    public CityTest() throws FileNotFoundException {
    }

    @Test
    public void testInit() {
        String expectedId = "2643743";
        String expectedName = "London";
        String expectedCountry = "GB";
        double expectedLongitude = -0.1257;
        double expectedLatitude = 51.5085;
        long expectedTimeZone = 0;

        assertEquals(expectedId, city.getId());
        assertEquals(expectedName, city.getName());
        assertEquals(expectedCountry, city.getCountry());
        assertEquals(expectedLongitude, city.getLongitude());
        assertEquals(expectedLatitude, city.getLatitude());
        assertEquals(expectedTimeZone, city.getTimezone());
    }
}
