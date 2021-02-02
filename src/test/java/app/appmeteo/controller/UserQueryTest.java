package app.appmeteo.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserQueryTest {

    private UserQuery query;

    // [Los, Angeles, -temp, -wind] -> [Los Angeles, -temp, -wind]
    @Test
    public void testFixCommandLine() {
        // with options (specified with a hyphen)
        String[] query = new String[4];
        query[0] = "Los";
        query[1] = "Angeles";
        query[2] = "-temp";
        query[3] = "-wind";

        this.query = new UserQuery(query);

        String expectedfirst = "Los Angeles";
        String expectedSecond = "-temp";
        String expectedThird = "-wind";

        this.query.fixCommandline();
        assertEquals(expectedfirst, this.query.getQuery()[0]);
        assertEquals(expectedSecond, this.query.getQuery()[1]);
        assertEquals(expectedThird, this.query.getQuery()[2]);


        // without option
        query = new String[1];
        query[0] = "Ville-finVille";

        this.query = new UserQuery(query);
        expectedfirst = "Ville-finVille";
        this.query.fixCommandline();
        assertEquals(expectedfirst, this.query.getQuery()[0]);

    }
}

