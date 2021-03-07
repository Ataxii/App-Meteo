package app.appmeteo.controller.CLI;

import java.io.IOException;

import app.appmeteo.model.Commands.*;
import app.appmeteo.model.User;

/**
 * A Session is a comprehensive abstract class that aims to logically merge and divide the work of this app.<br>
 * Each Session can and should :
 * <ul>
 *     <li>give insight of how it works with the abstract getHelp() function</li>
 *     <li>give its naming  with the abstract getHelp() function</li>
 *     <li>be able to treat a query with its own specifications with the not abstract function treatQuery()</li>
 *     <li>treat other Session's specific queries!</li>
 * </ul>
 */
public abstract class Session {

    protected User user;
    protected boolean isOver;

    protected Session(User usr) {
        user = usr;
        isOver = false;
    }

    /**
     * the CLI loop. While the user does not use the command quit, a two step while loop runs. First it ask the user its next
     * command. Secondly it treat it with {@link #treatQuery()}. The treatment includes the display functions.
     */
    public void launch() {
        while (!isOver) {
            System.out.println(toString() + " --- Please Input your command:");
            user.next();
            treatQuery();
            System.out.println();
        }
    }

    public void treatQuery() {
        switch (user.getQuery().getCommandLineOption(0)) {
            case CommandType.HELP: {
                System.out.println(getHelp());
                break;
            }
            case CommandType.QUIT: {
                System.out.println("Leaving " + toString() + "...");
                try {
                    user.getFavouriteList().write();
                } catch (IOException e) {
                    System.out.println("Error : failed to write into your favourites");
                }
                isOver = true;
                break;
            }
        }
    }

    public abstract String getHelp();

    @Override
    public abstract String toString();
}
