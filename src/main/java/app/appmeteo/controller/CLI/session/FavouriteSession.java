package app.appmeteo.controller.CLI.session;
import app.appmeteo.controller.CLI.CLIController;
import app.appmeteo.controller.Commands.*;
import app.appmeteo.model.Favourite;
import app.appmeteo.model.User;

import java.io.IOException;

public class FavouriteSession extends Session {

    protected FavouriteSession(User usr) throws IOException {
        super(usr);
    }

    @Override
    public void treatQuery() throws IOException {
        super.treatQuery();
        if (isOver) return;

        switch (user.getQuery()[0]) {
            case FavouritesCommands.WEATHER: {
                treatWeatherQuery();
                break;
            }
            case FavouritesCommands.ADD: {
                user.favouriteList.addFavourite(user.getQuery().getCommand()[1]);
                break;
            }
            case FavouritesCommands.DEL: {
                user.favouriteList.delFavouriteByName(user.getQuery().getCommand()[1]);
                break;
            }
            case FavouritesCommands.LIST: {
                int index = 1;
                for (Favourite fav: user.favouriteList.getFavouriteList()) {
                    CLIController.addDisplay( "(" + index + ") " + fav.toString());
                    index++;
                }
                break;
            }
        }
    }

    private void treatWeatherQuery() throws IOException {
        Session fakeWeatherSession = new WeatherSession(new User(new String[1]));

        if (user.getQueryLength() == 2) {
            int index = Integer.parseInt(user.getQuery()[1]) - 1;
            if (index > user.favouriteList.getFavouriteList().size() || index <= 0) {
                CLIController.addDisplay("Specified Index is wrong");
                return;
            }
            fakeWeatherSession.user.getQuery()[0] = user.favouriteList.getFavouriteList().get(index).getName();
            treatOtherSessionQuery(fakeWeatherSession);
        } else {
            for (Favourite fav: user.favouriteList.getFavouriteList()) {
                fakeWeatherSession.user.getQuery()[0] = fav.getName();
                treatOtherSessionQuery(fakeWeatherSession);
            }
        }
    }

    @Override
    public String getHelp() {
        return "Use command:\n"
                + "'-getW' + [index]                to get the weather of all your favourites or a specific one\n"
                + "'-add' + 'existing town name'    to add a town to your favorites\n"
                + "'-del' + 'favourite name'        to delete a town from your favorites\n"
                + "'-list'                          to get a display of all your current favourites\n"
                + "'quit'                           to get back to main session\n"
                + "'session' + 'session command'    to process another session specific query";
    }

    @Override
    public String toString() {
        return "Favourite Session";
    }
}
