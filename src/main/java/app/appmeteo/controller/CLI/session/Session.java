package app.appmeteo.controller.CLI.session;

import java.io.IOException;
import java.util.Scanner;

import app.appmeteo.controller.CLI.CLIController;
import app.appmeteo.controller.Commands.*;
import app.appmeteo.controller.User;

public abstract class Session{

    protected User user;
    protected boolean isOver;

    protected Session(User usr) {
        user = usr;
        isOver = false;
    }

    protected Session(Scanner scan) throws IOException {
        user = new User(scan);
        isOver = false;
    }

    public void launch() throws IOException {
        while (!isOver) {
            System.out.println("\n" + toString() + " --- Please Input your command:");
            user.next();
            treatQuery();
            CLIController.displayData();
        }
    }

    public void treatQuery() throws IOException {
        switch (this.user.getQuery()[0]) {
            case CommandType.HELP: {
                CLIController.addDisplay(getHelp());
                break;
            }
            case CommandType.QUIT: {
                CLIController.addDisplay("Leaving " + toString() + "...");
                user.favouriteList.write();
                this.isOver = true;
                break;
            }
            case CommandType.FAV: {
                if (user.getQueryLength() > 1) {
                    user.setQuery(1);
                    Session fastSession = new FavouriteSession(user);
                    treatOtherSessionQuery(fastSession);
                    break;
                }
            }
            case CommandType.WEATHER: {
                if (user.getQueryLength() > 1) {
                    user.setQuery(1);
                    Session fastSession = new WeatherSession(user);
                    treatOtherSessionQuery(fastSession);
                    break;
                }
            }
        }
    }

    public void treatOtherSessionQuery(Session otherSession) throws IOException {
        otherSession.treatQuery();
        CLIController.displayData();
    }

    public abstract String getHelp();

    @Override
    public abstract String toString();
}
