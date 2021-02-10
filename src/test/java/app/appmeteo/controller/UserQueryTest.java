package app.appmeteo.controller;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserQueryTest {

    private UserQuery query;

    // [Los, Angeles, -temp, -wind] -> [Los Angeles, -temp, -wind]
    @Test
    public void testFixCommandLine() throws IOException {
        // with options (specified with a hyphen) and no date or codes
        String[] testQuery = new String[4];
        testQuery[0] = "Los";
        testQuery[1] = "Angeles";
        testQuery[2] = "-temp";
        testQuery[3] = "-wind";

        this.query = new UserQuery(testQuery);

        String expectedfirst = "Los Angeles";
        String expectedSecond = "-temp";
        String expectedThird = "-wind";

        this.query.fixCommandline();
        assertEquals(expectedfirst, this.query.getCommand()[0]);
        assertEquals(expectedSecond, this.query.getCommand()[1]);
        assertEquals(expectedThird, this.query.getCommand()[2]);


        // with options and date
        testQuery = new String[6];
        testQuery[0] = "Los";
        testQuery[1] = "Angeles";
        testQuery[2] = "12569";
        testQuery[3] = "12/02/2021";
        testQuery[4] = "-temp";
        testQuery[5] = "-wind";

        this.query = new UserQuery(testQuery);

        expectedfirst = "Los Angeles";
        expectedSecond = "12569";
        expectedThird = "Fri Feb 12 00:00:00 CET 2021";
        String expectedFourth = "-temp";
        String expectedFifth = "-wind";

        this.query.fixCommandline();
        assertEquals(expectedfirst, this.query.getCommand()[0]);
        assertEquals(expectedSecond, this.query.getCommand()[1]);
        assertEquals(expectedThird, this.query.getCommand()[2]);
        assertEquals(expectedFourth, this.query.getCommand()[3]);
        assertEquals(expectedFifth, this.query.getCommand()[4]);


    }

}

