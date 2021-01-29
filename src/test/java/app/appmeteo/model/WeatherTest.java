package app.appmeteo.model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherTest {

    private Weather weather = new Weather(new File("test.json"));

    public WeatherTest() throws FileNotFoundException {
    }

    @Test
    public void testInit() {
        String expectedId = "804";
        String expectedMain = "Clouds" ;
        String expectedDescription = "overcast clouds";
        String expectedIcon = "04d" ;
        long expectedTime = 1611475110;
        double expectedTemp = 272.65;
        double expectedTempFeelsLike = 268.16;
        double expectedTempMin = 272.04;
        double expectedTempMax = 273.15;
        int expectedHumidity = 86;
        double expectedWindSpeed = 3.09;
        int expectedWindDeg = 90;

        assertEquals(expectedId, weather.getId());
        assertEquals(expectedMain, weather.getMain());
        assertEquals(expectedDescription, weather.getDescription());
        assertEquals(expectedIcon, weather.getIcon());
        assertEquals(expectedTime, weather.getTime());
        assertEquals(expectedTemp, weather.getTemp());
        assertEquals(expectedTempFeelsLike, weather.getTempFeelsLike());
        assertEquals(expectedTempMin, weather.getTempMin());
        assertEquals(expectedTempMax, weather.getTempMax());
        assertEquals(expectedHumidity,weather.getHumidity());
        assertEquals(expectedWindSpeed,weather.getWindSpeed());
        assertEquals(expectedWindDeg,weather.getWindDeg());
    }
}
