package app.appmeteo.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

public class HourWeatherTest {
    String json = "{\n" +
            "  \"current\": {\n" +
            "    \"dt\": 1612274400,\n" +
            "    \"temp\": 286.05,\n" +
            "    \"feels_like\": 282.62,\n" +
            "    \"pressure\": 995,\n" +
            "    \"humidity\": 71,\n" +
            "    \"dew_point\": 280.93,\n" +
            "    \"uvi\": 0.51,\n" +
            "    \"clouds\": 75,\n" +
            "    \"visibility\": 10000,\n" +
            "    \"wind_speed\": 4.16,\n" +
            "    \"wind_deg\": 227,\n" +
            "    \"weather\": [\n" +
            "      {\n" +
            "        \"id\": 500,\n" +
            "        \"main\": \"Rain\",\n" +
            "        \"description\": \"light rain\",\n" +
            "        \"icon\": \"10d\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"pop\": 0.51,\n" +
            "    \"rain\": {\n" +
            "      \"1h\": 0.12\n" +
            "    }\n" +
            "  }\n" +
            "}";

    @Test
    public void testStringConstructor() {
        HourWeather weather = new HourWeather(json);
        String expectedId = "500";
        String expectedMain = "Rain" ;
        String expectedDescription = "light rain";
        String expectedIcon = "10d" ;
        long expectedTime = 1612274400;
        double expectedTemp = 286.05;
        double expectedTempFeelsLike = 282.62;
        int expectedHumidity = 71;
        int expectedWindSpeed = (int) (4.16 * 3.6);
        int expectedWindDeg = 227;
        int expectedCloudiness = 75;
        int expectedVisibility = 10000;
        int expectedPressure = 995;

        assertEquals(expectedId, weather.getId());
        assertEquals(expectedMain, weather.getMain());
        assertEquals(expectedDescription, weather.getDescription());
        assertEquals(expectedIcon, weather.getIcon());
        assertEquals(expectedTime, weather.getDate());
        assertEquals(expectedTemp, weather.getTemp());
        assertEquals(expectedTempFeelsLike, weather.getTempFeelsLike());
        assertEquals(expectedHumidity,weather.getHumidity());
        assertEquals(expectedWindSpeed,weather.getWindSpeed());
        assertEquals(expectedWindDeg,weather.getWindDeg());
        assertEquals(expectedCloudiness,weather.getCloudiness());
        assertEquals(expectedVisibility,weather.getVisibility());
        assertEquals(expectedPressure,weather.getPressure());
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
        int expectedWindSpeed = (int) (4.16 * 3.6);
        int expectedWindDeg = 227;
        int expectedCloudiness = 75;
        int expectedVisibility = 10000;
        int expectedPressure = 995;
        int expectedPop = 51;

        assertEquals(expectedId, weather.getId());
        assertEquals(expectedMain, weather.getMain());
        assertEquals(expectedDescription, weather.getDescription());
        assertEquals(expectedIcon, weather.getIcon());
        assertEquals(expectedTime, weather.getDate());
        assertEquals(expectedTemp, weather.getTemp());
        assertEquals(expectedTempFeelsLike, weather.getTempFeelsLike());
        assertEquals(expectedHumidity,weather.getHumidity());
        assertEquals(expectedWindSpeed,weather.getWindSpeed());
        assertEquals(expectedWindDeg,weather.getWindDeg());
        assertEquals(expectedCloudiness,weather.getCloudiness());
        assertEquals(expectedVisibility,weather.getVisibility());
        assertEquals(expectedPressure,weather.getPressure());
        assertEquals(expectedPop,weather.getPop());
    }
}
