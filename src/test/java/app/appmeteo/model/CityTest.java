package app.appmeteo.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CityTest {
    String json = "{\n" +
            "  \"coord\": {\n" +
            "    \"lon\":-0.1257,\"lat\":51.5085\n" +
            "  },\n" +
            "  \"weather\": [\n" +
            "    {\n" +
            "      \"id\":804,\n" +
            "      \"main\":\"Clouds\",\n" +
            "      \"description\":\"overcast clouds\",\n" +
            "      \"icon\":\"04d\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"base\":\"stations\",\n" +
            "  \"main\": {\n" +
            "    \"temp\":272.65,\n" +
            "    \"feels_like\":268.16,\n" +
            "    \"temp_min\":272.04,\n" +
            "    \"temp_max\":273.15,\n" +
            "    \"pressure\":999,\n" +
            "    \"humidity\":86\n" +
            "  },\n" +
            "  \"visibility\":10000,\n" +
            "  \"wind\": {\n" +
            "    \"speed\":3.09,\n" +
            "    \"deg\":90\n" +
            "  },\n" +
            "  \"clouds\": {\n" +
            "    \"all\":100\n" +
            "  },\n" +
            "  \"dt\":1611475110,\n" +
            "  \"sys\": {\n" +
            "    \"type\":1,\n" +
            "    \"id\":1414,\n" +
            "    \"country\":\"GB\",\n" +
            "    \"sunrise\":1611474590,\n" +
            "    \"sunset\":1611506106\n" +
            "  },\n" +
            "  \"timezone\":0,\n" +
            "  \"id\":2643743,\n" +
            "  \"name\":\"London\",\n" +
            "  \"cod\":200\n" +
            "}";
    @Test
    public void testInit() throws IOException {
        City city = new City(json);
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
