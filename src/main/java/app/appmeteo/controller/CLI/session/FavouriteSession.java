package app.appmeteo.controller.CLI.session;

import app.appmeteo.controller.CLI.CLIController;
import app.appmeteo.controller.Commands.*;
import app.appmeteo.model.Favourite;
import app.appmeteo.model.User;

import java.io.IOException;

public class FavouriteSession extends Session {

    protected FavouriteSession(User usr) {
        super(usr);
    }

    @Override
    public void treatQuery() throws IOException {
        super.treatQuery();
        if (isOver) return;

        switch (user.getQuery().getCommandLineOption(0)) {
            case FavouritesCommands.WEATHER: {
                treatWeatherQuery();
                break;
            }
            case FavouritesCommands.ADD: {
                if (user.getQuery().getCommandLineLength() == 2) {
                    user.getFavouriteList().addFavouriteByCityName(user.getQuery().getCommandLineOption(1));
                }
                if (user.getQuery().getCommandLineLength() == 3) {
                    user.getFavouriteList().addFavouriteByCityAndCountryCode(user.getQuery().getCommandLineOption(1),
                            user.getQuery().getCommandLineOption(2));
                }
                break;
            }
            case FavouritesCommands.DEL: {
                user.getFavouriteList().delFavouriteByName(user.getQuery().getCommandLineOption(1));
                break;
            }
            case FavouritesCommands.LIST: {
                int index = 1;
                for (Favourite fav : user.getFavouriteList().getList()) {
                    CLIController.addDisplay("(" + index + ") " + fav.toString());
                    index++;
                }
                break;
            }
        }
    }

    private void treatWeatherQuery() throws IOException {
        if (user.getQuery().getCommandLineLength() == 2) {
            displaySelectedFavouriteWeather();
        } else {
            displayAllFavouritesWeather();
        }
    }

    private void displaySelectedFavouriteWeather() throws IOException {

        int index = Integer.parseInt(user.getQuery().getCommandLineOption(1)) - 1;
        if (index > user.getFavouriteList().getList().size() || index < 0) {
            CLIController.addDisplay("Specified Index is wrong");
            return;
        }
        Session fakeWeatherSession = new WeatherSession(new User(new String[]{user.getFavouriteList().getFavouriteAtIndex(index).getName()}));
        treatOtherSessionQuery(fakeWeatherSession);
    }

    private void displayAllFavouritesWeather() throws IOException {
        Session fakeWeatherSession = new WeatherSession(new User(new String[1]));
        for (Favourite fav : user.getFavouriteList().getList()) {
            fakeWeatherSession.user.getQuery().setCommandLineOption(0, fav.getName());
            treatOtherSessionQuery(fakeWeatherSession);
            System.out.println();
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
