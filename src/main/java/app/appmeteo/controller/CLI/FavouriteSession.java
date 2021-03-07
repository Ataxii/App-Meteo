package app.appmeteo.controller.CLI;

import app.appmeteo.model.Commands.*;
import app.appmeteo.model.Favourite;
import app.appmeteo.model.User;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Arrays;

public class FavouriteSession extends Session {

    public FavouriteSession(User usr) {
        super(usr);
    }

    @Override
    public void treatQuery() {
        super.treatQuery();
        if (isOver) return;

        switch (user.getQuery().getCommandLineOption(0)) {
            case CommandType.FAV:
                break;
            case CommandType.WEATHER:
                if (user.getQuery().getCommandLineLength() > 1) {
                    String[] fastQuery = Arrays.copyOfRange(user.getQuery().getCommandLine(), 1, user.getQuery().getCommandLineLength());
                    user.getQuery().setCommandLine(fastQuery);
                    Session fastSession = new WeatherSession(user);
                    fastSession.treatQuery();
                } else if (user.getQuery().getCommandLineLength() == 1) {
                    isOver = true;
                    System.out.println("switching to weather session...");
                    new WeatherSession(user).launch();
                }
                break;
            case FavouritesCommands.WEATHER:
                treatWeatherQuery();
                break;
            case FavouritesCommands.ADD:
                // add by city name
                if (user.getQuery().getCommandLineLength() == 2) {
                    String cityName = user.getQuery().getCommandLineOption(1);
                    try {
                        user.getFavouriteList().addFavourite(cityName);
                        System.out.println(cityName + " was added into your favourites");
                    } catch (InvalidParameterException e) {
                        System.err.println(e.getMessage());
                    } catch (IOException e) {
                        System.err.println("Error : city not found / the API is not responding");
                    }
                }
                // add by city name and country code
                else if (user.getQuery().getCommandLineLength() == 3) {
                    String cityName = user.getQuery().getCommandLineOption(1);
                    String countryCode = user.getQuery().getCommandLineOption(2);
                    try {
                        user.getFavouriteList().addFavourite(cityName, countryCode);
                        System.out.println(cityName + " " + countryCode + " was added into your favourites");
                    } catch (InvalidParameterException e) {
                        System.err.println(e.getMessage());
                    } catch (IOException e) {
                        System.err.println("Error : city not found / the API is not responding");
                    }
                }
                break;
            case FavouritesCommands.DEL: {
                if (user.getQuery().getCommandLineLength() == 2) {
                    int index;
                    try {
                        index = Integer.parseInt(user.getQuery().getCommandLineOption(1)) - 1;
                    } catch (NumberFormatException e) {
                        System.err.println("Error : specified index is not a number.");
                        break;
                    }
                    try {
                        String cityName = user.getFavouriteList().getFavouriteAtIndex(index).getName();
                        user.getFavouriteList().removeFavourite(index);
                        System.out.println(cityName + " was removed from your favourites");
                    } catch (IndexOutOfBoundsException e) {
                        System.err.println(e.getMessage());
                    }
                }
                break;
            }
            case FavouritesCommands.LIST: {
                int index = 1;
                for (Favourite fav : user.getFavouriteList().getList()) {
                    System.out.println("(" + index + ") " + fav.toString());
                    index++;
                }
                if (index == 1) System.out.println("You're favourite list is currently empty");
                break;
            }
        }
    }

    private void treatWeatherQuery() {
        if (user.getQuery().getCommandLineLength() >= 2) {
            displaySelectedFavouriteWeather();
        } else {
            displayAllFavouritesWeather();
        }
    }

    private void displaySelectedFavouriteWeather() {
        // get index
        int index;
        try {
            index = Integer.parseInt(user.getQuery().getCommandLineOption(1)) - 1;
        } catch (NumberFormatException e) {
            System.err.println("Error : specified index is not a number.");
            return;
        }

        // change command line option to city name + country code + weather options
        String[] weatherCommand = new String[user.getQuery().getCommandLineLength()];
        try {
            weatherCommand[0] = user.getFavouriteList().getFavouriteAtIndex(index).getName();
            weatherCommand[1] = user.getFavouriteList().getFavouriteAtIndex(index).getCountryCode();
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        }
        for (int i = 2; i < weatherCommand.length; i++) {
            weatherCommand[i] = user.getQuery().getCommandLineOption(i);
        }

        // treat query
        user.setQuery(weatherCommand);
        new WeatherSession(user).treatQuery();
    }

    private void displayAllFavouritesWeather() {
        String[] weatherCommand = new String[2];
        Session fakeWeatherSession = new WeatherSession(user);
        for (Favourite fav : user.getFavouriteList().getList()) {
            weatherCommand[0] = fav.getName();
            weatherCommand[1] = fav.getCountryCode();
            fakeWeatherSession.user.setQuery(weatherCommand);
            fakeWeatherSession.treatQuery();
            System.out.println();
        }
    }

    @Override
    public String getHelp() {
        return "Use command:\n"
                + "'getw' + 'index' + [weather options] -- specific favourite weather\n"
                + "'getw'                               -- all favourites current weather\n"
                + "'add' + 'town name' + [country code] -- add favourite\n"
                + "'del' + 'index'                      -- del favourite\n"
                + "'list'                               -- display all favourites with corresponding index\n"
                + "'quit'                               -- to leave the app\n"
                + "'weather' + 'weather command'        -- process a weather query\n"
                + "'weather'                            -- enter a weather session\n";
    }

    @Override
    public String toString() {
        return "Favourite Session";
    }
}
