package app.appmeteo.model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserTest {

    @Test
    public void TestUser() throws IOException {

        String[] testQuery = new String[4];
        testQuery[0] = "Los";
        testQuery[1] = "Angeles";
        testQuery[2] = "-temp";
        testQuery[3] = "-wind";

        User user = new User(testQuery);

        user.setFavouriteList(new FavouriteList(new File("bonsoir")));
        assertEquals(user.getFavouriteList().getList(), new FavouriteList(new File("bonsoir")).getList());
        assertEquals(user.getQuery().getCommandLine(), new UserQuery(testQuery).getCommandLine());
    }
}
