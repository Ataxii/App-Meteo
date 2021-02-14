package app.appmeteo.controller.CLI.session;

import app.appmeteo.controller.CLI.CLIController;
import app.appmeteo.controller.Commands.*;
import app.appmeteo.model.User;

import java.io.IOException;
import java.util.Scanner;

public class MainSession extends Session {

    protected MainSession(User usr) {
        super(usr);
    }

    public MainSession(Scanner scan) throws IOException {
        super(scan);
    }

    @Override
    public void treatQuery() throws IOException {
        super.treatQuery();
        switch (user.getQuery().getCommandLine()[0]) {
            case CommandType.FAV: {
                new FavouriteSession(user).launch();
                break;
            }
            case CommandType.WEATHER: {
                new WeatherSession(user).launch();
                break;
            }
        }
    }

    @Override
    public String getHelp() {
        return "Use command:\n"
                + "'fav'        to enter a fav session\n"
                + "'weather'    to enter a weather session\n"
                + "'quit'       to leave the program\n"
                + "'session' + 'session command'    to process another session specific query.\n"
                + "to illustrate this, 'fav -add lille' will add lille to your favourites and you will stay into this main session";
    }

    @Override
    public String toString() {
        return "Main Session";
    }
}
