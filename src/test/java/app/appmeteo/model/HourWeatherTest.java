package app.appmeteo.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

public class HourWeatherTest {
    @Test
    public void testFileConstructor() throws FileNotFoundException {
        HourWeather weather = new HourWeather(new File("src/test/resources/test.json"));
        String expectedId = "804";
        String expectedMain = "Clouds" ;
        String expectedDescription = "overcast clouds";
        String expectedIcon = "04d" ;
        long expectedTime = 1611475110;
        double expectedTemp = 272.65;
        double expectedTempFeelsLike = 268.16;
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
        assertEquals(expectedHumidity,weather.getHumidity());
        assertEquals(expectedWindSpeed,weather.getWindSpeed());
        assertEquals(expectedWindDeg,weather.getWindDeg());
    }

    @Test
    public void testJsonObjectConstructor() throws FileNotFoundException {
        JsonObject obj = JsonParser.parseReader(new FileReader(new File("src/test/resources/hourWeather.json"))).getAsJsonObject();
        HourWeather weather = new HourWeather(obj);
        String expectedId = "500";
        String expectedMain = "Rain" ;
        String expectedDescription = "light rain";
        String expectedIcon = "10d" ;
        long expectedTime = 1612274400;
        double expectedTemp = 286.05;
        double expectedTempFeelsLike = 282.62;
        int expectedHumidity = 71;
        double expectedWindSpeed = 4.16;
        int expectedWindDeg = 227;

        assertEquals(expectedId, weather.getId());
        assertEquals(expectedMain, weather.getMain());
        assertEquals(expectedDescription, weather.getDescription());
        assertEquals(expectedIcon, weather.getIcon());
        assertEquals(expectedTime, weather.getTime());
        assertEquals(expectedTemp, weather.getTemp());
        assertEquals(expectedTempFeelsLike, weather.getTempFeelsLike());
        assertEquals(expectedHumidity,weather.getHumidity());
        assertEquals(expectedWindSpeed,weather.getWindSpeed());
        assertEquals(expectedWindDeg,weather.getWindDeg());
    }
}
