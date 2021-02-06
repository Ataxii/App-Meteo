package app.appmeteo.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class APIQueryTest {
    @Test
    public void queryTest() throws IOException {
        String response = APIQuery.QueryWithCountryCode("Paris", "US");
        JsonObject obj = JsonParser.parseString(response).getAsJsonObject();
        int code = obj.get("cod").getAsInt();
        assertEquals(code, 200);

        response = APIQuery.QueryWithID("2172797");
        obj = JsonParser.parseString(response).getAsJsonObject();
        code = obj.get("cod").getAsInt();
        assertEquals(code, 200);

        response = APIQuery.QueryOneCallWithPos(3.1195, 43.238);
        System.out.println(response);
        obj = JsonParser.parseString(response).getAsJsonObject();
        double lat = obj.get("lat").getAsDouble();
        assertEquals(lat, 43.238);

        response = APIQuery.QueryWeatherWithPos(3.1195, 43.238);
        System.out.println(response);
        obj = JsonParser.parseString(response).getAsJsonObject();
        code = obj.get("cod").getAsInt();
        assertEquals(code, 200);

        response = APIQuery.QueryWithZip("13005", "FR");
        obj = JsonParser.parseString(response).getAsJsonObject();
        code = obj.get("cod").getAsInt();
        assertEquals(code, 200);

        response = APIQuery.QueryStringWithCity("London");
        obj = JsonParser.parseString(response).getAsJsonObject();
        code = obj.get("cod").getAsInt();
        assertEquals(code, 200);
    }
}
