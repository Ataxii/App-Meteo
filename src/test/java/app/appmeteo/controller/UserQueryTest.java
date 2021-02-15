package app.appmeteo.controller;


import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserQueryTest {

    private UserQuery query;

    // [Los, Angeles, -temp, -wind] -> [Los Angeles, -temp, -wind]
    @Test
    public void testFixCommandLine() throws IOException {
        // with options (specified with a hyphen) and no date or codes
        String[] testQuery = new String[3];
        testQuery[0] = "Los_Angeles";
        testQuery[1] = "-temp";
        testQuery[2] = "-wind";

        this.query = new UserQuery(testQuery);

        String expectedfirst = "Los Angeles";
        String expectedSecond = "-temp";
        String expectedThird = "-wind";

        this.query.fixCommandline();
        assertEquals(expectedfirst, this.query.getCommandLine()[0]);
        assertEquals(expectedSecond, this.query.getCommandLine()[1]);
        assertEquals(expectedThird, this.query.getCommandLine()[2]);


        // with options and date
        testQuery = new String[5];
        testQuery[0] = "Los_Angeles";
        testQuery[1] = "12569";
        testQuery[2] = "12/02/2021";
        testQuery[3] = "-temp";
        testQuery[4] = "-wind";

        this.query = new UserQuery(testQuery);

        expectedfirst = "Los Angeles";
        expectedSecond = "12569";
        expectedThird = "Fri Feb 12 00:00:00 CET 2021";
        String expectedFourth = "-temp";
        String expectedFifth = "-wind";

        this.query.fixCommandline();
        assertEquals(expectedfirst, this.query.getCommandLine()[0]);
        assertEquals(expectedSecond, this.query.getCommandLine()[1]);
        assertEquals(expectedThird, this.query.getCommandLine()[2]);
        assertEquals(expectedFourth, this.query.getCommandLine()[3]);
        assertEquals(expectedFifth, this.query.getCommandLine()[4]);

        testQuery = new String[5];
        testQuery[0] = "Los_Angeles";
        testQuery[1] = "US";
        testQuery[2] = "12/02/2021";
        testQuery[3] = "-temp";
        testQuery[4] = "-wind";

        this.query = new UserQuery(testQuery);

        expectedfirst = "Los Angeles";
        expectedSecond = "US";
        expectedThird = "Fri Feb 12 00:00:00 CET 2021";
        expectedFourth = "-temp";
        expectedFifth = "-wind";

        this.query.fixCommandline();

        assertEquals(expectedfirst, this.query.getCommandLine()[0]);
        assertEquals(expectedSecond, this.query.getCommandLine()[1]);
        assertEquals(expectedThird, this.query.getCommandLine()[2]);
        assertEquals(expectedFourth, this.query.getCommandLine()[3]);
        assertEquals(expectedFifth, this.query.getCommandLine()[4]);



    }

}

