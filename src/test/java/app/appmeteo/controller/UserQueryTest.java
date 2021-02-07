package app.appmeteo.controller;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class UserQueryTest {

    private User query;

    // [Los, Angeles, -temp, -wind] -> [Los Angeles, -temp, -wind]
    @Test
    public void testFixCommandLine() throws IOException {
        // with options (specified with a hyphen)
        String[] testQuery = new String[4];
        testQuery[0] = "Los";
        testQuery[1] = "Angeles";
        testQuery[2] = "-temp";
        testQuery[3] = "-wind";

        this.query = new User(testQuery);

        String expectedfirst = "Los Angeles";
        String expectedSecond = "-temp";
        String expectedThird = "-wind";

        this.query.fixCommandline();
        assertEquals(expectedfirst, this.query.getQuery()[0]);
        assertEquals(expectedSecond, this.query.getQuery()[1]);
        assertEquals(expectedThird, this.query.getQuery()[2]);


        // without option
        testQuery = new String[1];
        testQuery[0] = "Ville-finVille";

        this.query = new User(testQuery);
        expectedfirst = "Ville-finVille";
        this.query.fixCommandline();
        assertEquals(expectedfirst, this.query.getQuery()[0]);

    }

    @Test
    public void testFixCommandDate() throws IOException {
        String[] testQuery = new String[3];
        testQuery[0] = "Los Angeles 31/02/2001";
        testQuery[1] = "-temp";
        testQuery[2] = "-wind";

        this.query = new User(testQuery);

        String expectedfirst = "Los Angeles";
        String expectedSecond = "31/02/2001";
        String expectedThird = "-temp";
        String expectedFourth = "-wind";

        this.query.fixCommandDate();
        assertEquals(expectedfirst, this.query.getQuery()[0]);
        assertEquals(expectedSecond, this.query.getQuery()[1]);
        assertEquals(expectedThird, this.query.getQuery()[2]);
        assertEquals(expectedFourth, this.query.getQuery()[3]);



        // without option
        testQuery = new String[1];
        testQuery[0] = "Ville-finVille 31/02/2001";

        this.query = new User(testQuery);
        expectedfirst = "Ville-finVille";
        expectedSecond = "31/02/2001";
        this.query.fixCommandDate();
        assertEquals(expectedfirst, this.query.getQuery()[0]);
        assertEquals(expectedSecond, this.query.getQuery()[1]);

        //without proper date but respecting format
        testQuery = new String[3];
        testQuery[0] = "Los Angeles 12/45/6000";
        testQuery[1] = "-temp";
        testQuery[2] = "-wind";


        this.query = new User(testQuery);
        expectedfirst = "Los Angeles";
        expectedSecond = "12/45/6000";
        expectedThird = "-temp";
        expectedFourth = "-wind";

        this.query.fixCommandDate();
        assertEquals(expectedfirst, this.query.getQuery()[0]);
        assertEquals(expectedSecond, this.query.getQuery()[1]);
        assertEquals(expectedThird, this.query.getQuery()[2]);
        assertEquals(expectedFourth, this.query.getQuery()[3]);

        //without date
        testQuery = new String[3];
        testQuery[0] = "Los Angeles";
        testQuery[1] = "-temp";
        testQuery[2] = "-wind";


        this.query = new User(testQuery);
        expectedfirst = "Los Angeles";
        expectedSecond = "-temp";
        expectedThird = "-wind";

        this.query.fixCommandDate();
        assertEquals(expectedfirst, this.query.getQuery()[0]);
        assertEquals(expectedSecond, this.query.getQuery()[1]);
        assertEquals(expectedThird, this.query.getQuery()[2]);

        //without proper date not respecting format
        testQuery = new String[3];
        testQuery[0] = "Los Angeles 2/4/600";
        testQuery[1] = "-temp";
        testQuery[2] = "-wind";


        this.query = new User(testQuery);
        expectedfirst = "Los Angeles";
        expectedSecond = "-temp";
        expectedThird = "-wind";

        this.query.fixCommandDate();
        assertEquals(expectedfirst, this.query.getQuery()[0]);
        assertEquals(expectedSecond, this.query.getQuery()[1]);
        assertEquals(expectedThird, this.query.getQuery()[2]);


    }
}

