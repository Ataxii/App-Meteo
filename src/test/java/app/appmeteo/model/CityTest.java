package app.appmeteo.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CityTest {
    @Test
    public void testInit() throws IOException {
        City city = new City(new File("src/test/resources/test.json"));
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
        assertEquals(48, city.getWeatherPerHour().size());
        assertEquals(8, city.getWeatherPerDay().size());
    }
}
