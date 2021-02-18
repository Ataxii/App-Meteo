package app.appmeteo.controller.CLI.session;

import app.appmeteo.controller.CLI.CLIController;
import app.appmeteo.controller.Commands.*;
import app.appmeteo.model.Favourite;
import app.appmeteo.model.User;

import java.io.IOException;
import java.util.Arrays;

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
        if (user.getQuery().getCommandLineLength() >= 2) {
            displaySelectedFavouriteWeather();
        } else {
            displayAllFavouritesWeather();
        }
    }

    private void displaySelectedFavouriteWeather() throws IOException {
        // get correct index
        int index = Integer.parseInt(user.getQuery().getCommandLineOption(1)) - 1;
        if (index > user.getFavouriteList().getList().size() || index < 0) {
            CLIController.addDisplay("Specified Index is wrong");
            return;
        }
        // change command line option to city name + country code
        String[] weatherCommand = new String[user.getQuery().getCommandLineLength()];
        weatherCommand[0] = user.getFavouriteList().getFavouriteAtIndex(index).getName();
        weatherCommand[1] = user.getFavouriteList().getFavouriteAtIndex(index).getCountryCode();

        for (int i = 2; i < weatherCommand.length; i++) {
            weatherCommand[i] = user.getQuery().getCommandLineOption(i);
        }

        // treat query
        Session fakeWeatherSession = new WeatherSession(new User(weatherCommand));
        treatOtherSessionQuery(fakeWeatherSession);
    }

    private void displayAllFavouritesWeather() throws IOException {
        String[] weatherCommand = new String[2];
        Session fakeWeatherSession = new WeatherSession(new User(weatherCommand));
        for (Favourite fav : user.getFavouriteList().getList()) {
            fakeWeatherSession.user.getQuery().setCommandLineOption(0, fav.getName());
            fakeWeatherSession.user.getQuery().setCommandLineOption(1, fav.getCountryCode());
            treatOtherSessionQuery(fakeWeatherSession);
            System.out.println();
        }
    }

    @Override
    public String getHelp() {
        return "Use command:\n"
                + "'-getw' + 'index' (use '-list' for infos) + [weather options (use 'weather help' for infos)]\n"
                + "                                 to get the weather of specified favourite\n"
                + "'-getw' to get the today weather of all your favourites\n"
                + "'-add' + 'existing town name' + [country code (FR, US...)]\n"
                + "                                 to add a town to your favorites\n"
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
