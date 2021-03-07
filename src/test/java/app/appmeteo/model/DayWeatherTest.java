package app.appmeteo.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DayWeatherTest {
    @Test
    public void testJsonObjectConstructor() throws FileNotFoundException {
        JsonObject obj = JsonParser.parseReader(new FileReader(new File("src/test/resources/dayWeather.json"))).getAsJsonObject();
        DayWeather weather = new DayWeather(obj);
        String expectedId = "501";
        String expectedMain = "Rain" ;
        String expectedDescription = "moderate rain";
        String expectedIcon = "app/appmeteo/images/10d.png" ;
        long expectedDay = 1612267200;
        long expectedSunrise = 1612251430;
        long expectedSunset = 1612284673;
        int expectedTempDay = 284;
        int expectedTempMin = 277;
        int expectedTempMax = 286;
        int expectedTempNight = 282;
        int expectedTempEvening = 283;
        int expectedTempMorning = 281;
        int expectedTempFeelsLikeDay = 281;
        int expectedTempFeelsLikeNight = 277;
        int expectedTempFeelsLikeEvening = 279;
        int expectedTempFeelsLikeMorning = 279;
        int expectedPressure = 995;
        int expectedHumidity = 78;
        int expectedWindSpeed = (int) (3.25 * 3.6);
        int expectedWindDeg = 240;
        int expectedCloudiness = 75;
        int expectedPop = 100;

        assertEquals(expectedId, weather.getId());
        assertEquals(expectedMain, weather.getMain());
        assertEquals(expectedDescription, weather.getDescription());
        assertEquals(expectedIcon, weather.getIcon());
        assertEquals(expectedDay, weather.getDate());
        assertEquals(expectedSunrise, weather.getSunrise());
        assertEquals(expectedSunset, weather.getSunset());
        assertEquals(expectedTempDay, weather.getTempDay());
        assertEquals(expectedTempMin, weather.getTempMin());
        assertEquals(expectedTempMax, weather.getTempMax());
        assertEquals(expectedTempNight, weather.getTempNight());
        assertEquals(expectedTempEvening, weather.getTempEvening());
        assertEquals(expectedTempMorning, weather.getTempMorning());
        assertEquals(expectedTempFeelsLikeDay, weather.getTempFeelsLikeDay());
        assertEquals(expectedTempFeelsLikeNight, weather.getTempFeelsLikeNight());
        assertEquals(expectedTempFeelsLikeEvening, weather.getTempFeelsLikeEvening());
        assertEquals(expectedTempFeelsLikeMorning, weather.getTempFeelsLikeMorning());
        assertEquals(expectedHumidity,weather.getHumidity());
        assertEquals(expectedPressure,weather.getPressure());
        assertEquals(expectedWindSpeed,weather.getWindSpeed());
        assertEquals(expectedWindDeg,weather.getWindDeg());
        assertEquals(expectedCloudiness,weather.getCloudiness());
        assertEquals(expectedPop,weather.getPop());
    }
}
