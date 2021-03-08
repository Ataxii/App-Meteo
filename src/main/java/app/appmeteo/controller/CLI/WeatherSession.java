package app.appmeteo.controller.CLI;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import app.appmeteo.controller.APIQuery;
import app.appmeteo.model.Commands.*;
import app.appmeteo.controller.GUI.GUIUtilities;
import app.appmeteo.model.UserQuery;
import app.appmeteo.model.User;
import app.appmeteo.model.City;
import app.appmeteo.model.DayWeather;
import app.appmeteo.model.HourWeather;

/**
 * A type of Session to display weather conditions corresponding to user's query
 */
public class WeatherSession extends Session {

    public WeatherSession(User usr) {
        super(usr);
    }

    private City city; // city of user's query, initialised in treatQuery
    SimpleDateFormat hourOnly = new SimpleDateFormat("HH:mm");

    /**
     * Initialises City object and calls for correct function depending on user's query
     */
    @Override
    public void treatQuery() {
        super.treatQuery();
        if (isOver) return;

        if (user.getQuery().getCommandLineOption(0).equals(CommandType.FAV)) {
            if (user.getQuery().getCommandLineLength() > 1) {
                String[] fastQuery = Arrays.copyOfRange(user.getQuery().getCommandLine(), 1, user.getQuery().getCommandLineLength());
                user.getQuery().setCommandLine(fastQuery);
                Session fastSession = new FavouriteSession(user);
                fastSession.treatQuery();
            } else if (user.getQuery().getCommandLineLength() == 1) {
                isOver = true;
                System.out.println("switching to favourite session...");
                new FavouriteSession(user).launch();
            }
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (user.getQuery().getCommandLineOption(0).equals(CommandType.WEATHER)) return;
        if (user.getQuery().getCommandLineOption(0).equals(CommandType.HELP)) return;
        if (user.getQuery().getCommandLineOption(0).equals(FavouritesCommands.LIST)) return;
        if (user.getQuery().getCommandLineOption(0).equals(FavouritesCommands.WEATHER)) return;
        if (user.getQuery().getCommandLineOption(0).equals(FavouritesCommands.ADD)) return;
        if (user.getQuery().getCommandLineOption(0).equals(FavouritesCommands.DEL)) return;

        user.getQuery().fixCommandline();
        ArrayList<String> options = user.getQuery().getOptions();

        try {
            city = getCity();
            GUIUtilities.saveAppState(city, false);
        } catch (IOException ex) {
            System.err.println("Error : requested city not found");
            return;
        }

        if (!user.getQuery().hasDate()) {
            System.out.println("Weather in " + city.getName() + " " + city.getCountry() + " :\n");
            displayTodayWeather(options);
        } else {
            System.out.println("Weather in " + city.getName() + " " + city.getCountry() + " on " + sdf.format(user.getQuery().getDate()) + " :\n");
            displayDateWeather(options);
        }
    }

    /**
     * @return a String containing help on how to use a weather session
     */
    @Override
    public String getHelp() {
        return "Use command:\n"
                + "City Name [country code] [-options]               to know how's the weather like right now\n"
                + "City Name [country code] DD/MM/YYYY [-options]    to specify a certain date within the next 7 days\n"
                + "'ZIP Code' 'Country Code' [DD/MM/YYYY] [-options] to search a city from its zip code and country code \n"
                + "'quit'                     to leave the app\n"
                + "'fav'                      to switch to a favourite session\n"
                + "'fav' 'favourite command'  to process a favourite session query\n"
                + "Options : \n"
                + "\t'-temp'      displays temperature for every part of the day\n"
                + "\t'-wind'      displays wind speed and wind orientation\n"
                + "\t'-morning'   displays weather during the morning\n"
                + "\t'-evening'   displays weather during the evening\n"
                + "\t'-night'     displays weather during the night\n"
                + "\t'-precise'   displays more info such as humidity and pressure\n";
    }

    @Override
    public String toString() {
        return "Weather Session";
    }

    /**
     * Displays weather information asked by the user. Only called if the user didn't ask for a specific date
     * See displayDayWeather to know what happens when the user asks for a specific date
     *
     * @param options ArrayList of query options
     */
    private void displayTodayWeather(ArrayList<String> options) {
        HourWeather weatherNow = city.getWeatherNow();
        DayWeather weatherToday = city.getWeatherPerDay().get(0);
        hourOnly.setTimeZone(TimeZone.getTimeZone("London"));

        // Raw Call
        if (options.size() == 0) {
            System.out.print(
                    "Main Info :\n"
                            + "\t" + "Main : " + weatherNow.getMain() + "\n"
                            + "\t" + "Cloud cover : " + cloudinessAppreciation(weatherNow.getCloudiness()) + "\n"
                            + "\t" + "Probability of precipitation : " + city.getWeatherPerHour().get(0).getPop() + "%" + "\n"
                            + "\t" + "Sunrise : " + hourOnly.format(new Date(weatherToday.getSunrise() * 1000 + city.getTimezone() * 1000)) + "\n"
                            + "\t" + "Sunset : " + hourOnly.format(new Date(weatherToday.getSunset() * 1000 + city.getTimezone() * 1000)) + "\n"
                            + "Wind Info :\n"
                            + "\t" + "Orientation : " + getWindOrientation(weatherNow.getWindDeg()) + "\n"
                            + "\t" + "Speed : " + weatherNow.getWindSpeed() + " km/h" + "\n"
                            + "Temperature Info :\n"
                            + "\t" + "Day :\n"
                            + "\t\t" + "Temperature : " + weatherNow.getTemp() + "°C" + "\n"
                            + "\t\t" + "Feels like : " + weatherNow.getTempFeelsLike() + "°C" + "\n"
            );
        } else {
            // Options' info display
            for (String option : options) {
                switch (option) {
                    case WeatherCommands.TEMP:
                        System.out.print(
                                "Temperature Info :\n"
                                        + "\t" + "Day Temperature :\n"
                                        + "\t\t" + "Temperature Min : " + weatherToday.getTempMin() + "°C" + "\n"
                                        + "\t\t" + "Temperature Max : " + weatherToday.getTempMax() + "°C" + "\n"
                                        + "\t" + "Morning Temperature :\n"
                                        + "\t\t" + "Temperature : " + weatherToday.getTempMorning() + "°C" + "\n"
                                        + "\t\t" + "Feels like : " + weatherToday.getTempFeelsLikeMorning() + "°C" + "\n"
                                        + "\t" + "Evening Temperature :\n"
                                        + "\t\t" + "Temperature : " + weatherToday.getTempEvening() + "°C" + "\n"
                                        + "\t\t" + "Feels like : " + weatherToday.getTempFeelsLikeEvening() + "°C" + "\n"
                                        + "\t" + "Night Temperature :\n"
                                        + "\t\t" + "Temperature : " + weatherToday.getTempNight() + "°C" + "\n"
                                        + "\t\t" + "Feels like : " + weatherToday.getTempFeelsLikeNight() + "°C" + "\n"
                        );
                        break;
                    case WeatherCommands.WIND:
                        System.out.print(
                                "Wind Info :\n"
                                        + "\t" + "Orientation : " + getWindOrientation(weatherNow.getWindDeg()) + "\n"
                                        + "\t" + "Speed : " + weatherNow.getWindSpeed() + " km/h" + "\n"
                        );
                        break;
                    case WeatherCommands.MORNING:
                        System.out.print(
                                "Main Info :\n"
                                        + "\t" + "Main : " + weatherToday.getMain() + "\n"
                                        + "\t" + "Cloud cover : " + cloudinessAppreciation(weatherToday.getCloudiness()) + "\n"
                                        + "\t" + "Probability of precipitation : " + city.getWeatherPerHour().get(0).getPop() + "%" + "\n"
                                        + "Wind Info :\n"
                                        + "\t" + "Orientation : " + getWindOrientation(weatherToday.getWindDeg()) + "\n"
                                        + "\t" + "Speed : " + weatherToday.getWindSpeed() + " km/h" + "\n"
                                        + "Temperature Info :\n"
                                        + "\t" + "Morning :\n"
                                        + "\t\t" + "Temperature : " + weatherToday.getTempMorning() + "°C" + "\n"
                                        + "\t\t" + "Feels like : " + weatherToday.getTempFeelsLikeMorning() + "°C" + "\n"
                        );
                        break;
                    case WeatherCommands.EVENING:
                        System.out.print(
                                "Main Info :\n"
                                        + "\t" + "Main : " + weatherToday.getMain() + "\n"
                                        + "\t" + "Cloud cover : " + cloudinessAppreciation(weatherToday.getCloudiness()) + "\n"
                                        + "\t" + "Probability of precipitation : " + city.getWeatherPerHour().get(0).getPop() + "%" + "\n"
                                        + "Wind Info :\n"
                                        + "\t" + "Orientation : " + getWindOrientation(weatherToday.getWindDeg()) + "\n"
                                        + "\t" + "Speed : " + weatherToday.getWindSpeed() + " km/h" + "\n"
                                        + "Temperature Info :\n"
                                        + "\t" + "Evening :\n"
                                        + "\t\t" + "Temperature : " + weatherToday.getTempEvening() + "°C" + "\n"
                                        + "\t\t" + "Feels like : " + weatherToday.getTempFeelsLikeEvening() + "°C" + "\n"
                        );
                        break;
                    case WeatherCommands.NIGHT:
                        System.out.print(
                                "Main Info :\n"
                                        + "\t" + "Main : " + weatherToday.getMain() + "\n"
                                        + "\t" + "Cloud cover : " + cloudinessAppreciation(weatherToday.getCloudiness()) + "\n"
                                        + "\t" + "Probability of precipitation : " + city.getWeatherPerHour().get(0).getPop() + "%" + "\n"
                                        + "Wind Info :\n"
                                        + "\t" + "Orientation : " + getWindOrientation(weatherToday.getWindDeg()) + "\n"
                                        + "\t" + "Speed : " + weatherToday.getWindSpeed() + " km/h" + "\n"
                                        + "Temperature Info :\n"
                                        + "\t" + "Night :\n"
                                        + "\t\t" + "Temperature : " + weatherToday.getTempNight() + "°C" + "\n"
                                        + "\t\t" + "Feels like : " + weatherToday.getTempFeelsLikeNight() + "°C" + "\n"
                        );
                        break;
                    case WeatherCommands.PRECISE:
                        System.out.print(
                                "Main Info :\n"
                                        + "\t" + "Description : " + weatherNow.getDescription() + "\n"
                                        + "\t" + "Cloud cover : " + cloudinessAppreciation(weatherNow.getCloudiness()) + "\n"
                                        + "\t" + "Probability of precipitation : " + city.getWeatherPerHour().get(0).getPop() + "%" + "\n"
                                        + "\t" + "Humidity : " + weatherNow.getHumidity() + "%" + "\n"
                                        + "\t" + "Pressure : " + weatherNow.getPressure() + " hPa" + "\n"
                                        + "\t" + "Visibility : " + weatherNow.getVisibility() + "m" + "\n"
                                        + "\t" + "Sunrise : " + hourOnly.format(new Date(weatherToday.getSunrise() * 1000 + city.getTimezone() * 1000)) + "\n"
                                        + "\t" + "Sunset : " + hourOnly.format(new Date(weatherToday.getSunset() * 1000 + city.getTimezone() * 1000)) + "\n"
                                        + "Wind Info :\n"
                                        + "\t" + "Orientation : " + getWindOrientation(weatherNow.getWindDeg()) + "\n"
                                        + "\t" + "Speed : " + weatherNow.getWindSpeed() + " km/h" + "\n"
                                        + "Temperature Info :\n"
                                        + "\t" + "Day :\n"
                                        + "\t\t" + "Temperature : " + weatherNow.getTemp() + "°C" + "\n"
                                        + "\t\t" + "Feels like : " + weatherNow.getTempFeelsLike() + "°C" + "\n"
                                        + "\t\t" + "Temperature Min : " + weatherToday.getTempMin() + "°C" + "\n"
                                        + "\t\t" + "Temperature Max : " + weatherToday.getTempMax() + "°C" + "\n"
                                        + "\t" + "Morning :\n"
                                        + "\t\t" + "Temperature : " + weatherToday.getTempMorning() + "°C" + "\n"
                                        + "\t\t" + "Feels like : " + weatherToday.getTempFeelsLikeMorning() + "°C" + "\n"
                                        + "\t" + "Evening :\n"
                                        + "\t\t" + "Temperature : " + weatherToday.getTempEvening() + "°C" + "\n"
                                        + "\t\t" + "Feels like : " + weatherToday.getTempFeelsLikeEvening() + "°C" + "\n"
                                        + "\t" + "Night :\n"
                                        + "\t\t" + "Temperature : " + weatherToday.getTempNight() + "°C" + "\n"
                                        + "\t\t" + "Feels like : " + weatherToday.getTempFeelsLikeNight() + "°C" + "\n"
                        );
                        break;
                }
            }
        }
    }

    /**
     * Displays weather information asked by the user. Only called if the user asked for the weather at a specific Date
     * See displayTodayWeather to know what happens when the user doesn't ask for a specific date
     *
     * @param options ArrayList of query options
     */
    private void displayDateWeather(ArrayList<String> options) {
        DayWeather weather = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        hourOnly.setTimeZone(TimeZone.getTimeZone("London"));

        // Search for correct DayWeather
        for (DayWeather w : city.getWeatherPerDay()) {
            Date weatherDate = new Date(w.getDate() * 1000);
            if (isSameDay(user.getQuery().getDate(), weatherDate)) {
                weather = w;
            }
        }

        // If user's date is not valid
        if (weather == null) {
            System.err.println(
                    "Sorry, no information about how's the weather like in " + city.getName() + " on " + sdf.format(user.getQuery().getDate()) + "\n"
                            + "The date must be between " + sdf.format(new Date(city.getWeatherPerDay().get(0).getDate() * 1000))
                            + " and " + sdf.format(new Date(city.getWeatherPerDay().get(7).getDate() * 1000))
            );
            return;
        }

        // Raw call
        if (options.size() == 0) {
            System.out.print(
                    "Main Info :\n"
                            + "\t" + "Main : " + weather.getMain() + "\n"
                            + "\t" + "Cloud cover : " + cloudinessAppreciation(weather.getCloudiness()) + "\n"
                            + "\t" + "Probability of precipitation : " + weather.getPop() + "%" + "\n"
                            + "\t" + "Sunrise : " + hourOnly.format(new Date(weather.getSunrise() * 1000 + city.getTimezone() * 1000)) + "\n"
                            + "\t" + "Sunset : " + hourOnly.format(new Date(weather.getSunset() * 1000 + city.getTimezone() * 1000)) + "\n"
                            + "Wind Info :\n"
                            + "\t" + "Orientation : " + getWindOrientation(weather.getWindDeg()) + "\n"
                            + "\t" + "Speed : " + weather.getWindSpeed() + " km/h" + "\n"
                            + "Temperature Info :\n"
                            + "\t" + "Morning :\n"
                            + "\t\t" + "Temperature : " + weather.getTempMorning() + "°C" + "\n"
                            + "\t\t" + "Feels like : " + weather.getTempFeelsLikeMorning() + "°C" + "\n"
                            + "\t" + "Evening :\n"
                            + "\t\t" + "Temperature : " + weather.getTempEvening() + "°C" + "\n"
                            + "\t\t" + "Feels like : " + weather.getTempFeelsLikeEvening() + "°C" + "\n"
                            + "\t" + "Night :\n"
                            + "\t\t" + "Temperature : " + weather.getTempNight() + "°C" + "\n"
                            + "\t\t" + "Feels like : " + weather.getTempFeelsLikeNight() + "°C" + "\n"
            );
        } else {
            // Options' info display
            for (String option : options) {
                switch (option) {
                    case WeatherCommands.TEMP:
                        System.out.print(
                                "Temperature Info :\n"
                                        + "\t" + "Day Temperature :\n"
                                        + "\t\t" + "Temperature Min : " + weather.getTempMin() + "°C" + "\n"
                                        + "\t\t" + "Temperature Max : " + weather.getTempMax() + "°C" + "\n"
                                        + "\t" + "Morning :\n"
                                        + "\t\t" + "Temperature : " + weather.getTempMorning() + "°C" + "\n"
                                        + "\t\t" + "Feels like : " + weather.getTempFeelsLikeMorning() + "\n"
                                        + "\t" + "Evening :\n"
                                        + "\t\t" + "Temperature : " + weather.getTempEvening() + "°C" + "\n"
                                        + "\t\t" + "Feels like : " + weather.getTempFeelsLikeEvening() + "°C" + "\n"
                                        + "\t" + "Night :\n"
                                        + "\t\t" + "Temperature : " + weather.getTempNight() + "°C" + "\n"
                                        + "\t\t" + "Feels like : " + weather.getTempFeelsLikeNight() + "°C" + "\n"
                        );
                        break;
                    case WeatherCommands.WIND:
                        System.out.print(
                                "Wind Info :\n"
                                        + "\t" + "Orientation : " + getWindOrientation(weather.getWindDeg()) + "\n"
                                        + "\t" + "Speed : " + weather.getWindSpeed() + " km/h" + "\n"
                        );
                        break;
                    case WeatherCommands.MORNING:
                        System.out.print(
                                "Main Info :\n"
                                        + "\t" + "Main : " + weather.getMain() + "\n"
                                        + "\t" + "Cloud cover : " + cloudinessAppreciation(weather.getCloudiness()) + "\n"
                                        + "\t" + "Probability of precipitation : " + weather.getPop() + "%"
                                        + "Wind Info :\n"
                                        + "\t" + "Orientation : " + getWindOrientation(weather.getWindDeg()) + "\n"
                                        + "\t" + "Speed : " + weather.getWindSpeed() + " km/h" + "\n"
                                        + "Temperature Info :\n"
                                        + "\t" + "Morning :\n"
                                        + "\t\t" + "Temperature : " + weather.getTempMorning() + "°C" + "\n"
                                        + "\t\t" + "Feels like : " + weather.getTempFeelsLikeMorning() + "°C" + "\n"
                        );
                        break;
                    case WeatherCommands.EVENING:
                        System.out.print(
                                "Main Info :\n"
                                        + "\t" + "Main : " + weather.getMain() + "\n"
                                        + "\t" + "Cloud cover : " + cloudinessAppreciation(weather.getCloudiness()) + "\n"
                                        + "\t" + "Probability of precipitation : " + weather.getPop() + "%" + "\n"
                                        + "Wind Info :\n"
                                        + "\t" + "Orientation : " + getWindOrientation(weather.getWindDeg()) + "\n"
                                        + "\t" + "Speed : " + weather.getWindSpeed() + " km/h" + "\n"
                                        + "Temperature Info :\n"
                                        + "\t" + "Evening :\n"
                                        + "\t\t" + "Temperature : " + weather.getTempEvening() + "°C" + "\n"
                                        + "\t\t" + "Feels like : " + weather.getTempFeelsLikeEvening() + "°C" + "\n"
                        );
                        break;
                    case WeatherCommands.NIGHT:
                        System.out.print(
                                "Main Info :\n"
                                        + "\t" + "Main : " + weather.getMain() + "\n"
                                        + "\t" + "Cloud cover : " + cloudinessAppreciation(weather.getCloudiness()) + "\n"
                                        + "\t" + "Probability of precipitation : " + weather.getPop() + "%" + "\n"
                                        + "Wind Info :\n"
                                        + "\t" + "Orientation : " + getWindOrientation(weather.getWindDeg()) + "\n"
                                        + "\t" + "Speed : " + weather.getWindSpeed() + " km/h" + "\n"
                                        + "Temperature Info :\n"
                                        + "\t" + "Night :\n"
                                        + "\t\t" + "Temperature : " + weather.getTempNight() + "°C" + "\n"
                                        + "\t\t" + "Feels like : " + weather.getTempFeelsLikeNight() + "°C" + "\n"
                        );
                        break;
                    case WeatherCommands.PRECISE:
                        System.out.print(
                                "Main Info :\n"
                                        + "\t" + "Description : " + weather.getDescription() + "\n"
                                        + "\t" + "Cloud cover : " + cloudinessAppreciation(weather.getCloudiness()) + "\n"
                                        + "\t" + "Probability of precipitation : " + weather.getPop() + "%" + "\n"
                                        + "\t" + "Humidity : " + weather.getHumidity() + "%" + "\n"
                                        + "\t" + "Pressure : " + weather.getPressure() + " hPa" + "\n"
                                        + "\t" + "Sunrise : " + hourOnly.format(new Date(weather.getSunrise() * 1000 + city.getTimezone() * 1000)) + "\n"
                                        + "\t" + "Sunset : " + hourOnly.format(new Date(weather.getSunset() * 1000 + city.getTimezone() * 1000)) + "\n"
                                        + "Wind Info :\n"
                                        + "\t" + "Orientation : " + getWindOrientation(weather.getWindDeg()) + "\n"
                                        + "\t" + "Speed : " + weather.getWindSpeed() + " km/h" + "\n"
                                        + "Temperature Info :\n"
                                        + "\t" + "Day Temperature :\n"
                                        + "\t\t" + "Temperature Min : " + weather.getTempMin() + "°C" + "\n"
                                        + "\t\t" + "Temperature Max : " + weather.getTempMax() + "°C" + "\n"
                                        + "\t" + "Morning :\n"
                                        + "\t\t" + "Temperature : " + weather.getTempMorning() + "°C" + "\n"
                                        + "\t\t" + "Feels like : " + weather.getTempFeelsLikeMorning() + "°C" + "\n"
                                        + "\t" + "Evening :\n"
                                        + "\t\t" + "Temperature : " + weather.getTempEvening() + "°C" + "\n"
                                        + "\t\t" + "Feels like : " + weather.getTempFeelsLikeEvening() + "°C" + "\n"
                                        + "\t" + "Night :\n"
                                        + "\t\t" + "Temperature : " + weather.getTempNight() + "°C" + "\n"
                                        + "\t\t" + "Feels like : " + weather.getTempFeelsLikeNight() + "°C" + "\n"
                        );
                        break;
                }
            }
        }
    }

    /**
     * Transform a wind degree into a more likeable direction
     *
     * @param degree the wind degree
     * @return a simple String describing wind's orientation
     */
    public static String getWindOrientation(int degree) {

        if (degree >= 11 && degree < 34) {
            return "NNE";
        }
        if (degree >= 34 && degree < 57) {
            return "NE";
        }
        if (degree >= 57 && degree < 79) {
            return "ENE";
        }
        if (degree >= 79 && degree < 102) {
            return "E";
        }
        if (degree >= 102 && degree < 124) {
            return "ESE";
        }
        if (degree >= 124 && degree < 147) {
            return "SE";
        }
        if (degree >= 147 && degree < 169) {
            return "SSE";
        }
        if (degree >= 169 && degree < 192) {
            return "S";
        }
        if (degree >= 192 && degree < 214) {
            return "SSW";
        }
        if (degree >= 214 && degree < 237) {
            return "SW";
        }
        if (degree >= 237 && degree < 259) {
            return "WSW";
        }
        if (degree >= 259 && degree < 282) {
            return "W";
        }
        if (degree >= 282 && degree < 304) {
            return "WNW";
        }
        if (degree >= 304 && degree < 327) {
            return "NW";
        }
        if (degree >= 327 && degree < 349) {
            return "NNW";
        }
        return "N";
    }

    /**
     * Appreciates the weather's cloudiness based on a percentage
     *
     * @param percentage the percentage of cloudiness
     * @return a simple String describing the weather's cloudiness
     */
    public static String cloudinessAppreciation(int percentage) {
        if (percentage < 5) {
            return "Clear sky";
        } else if (percentage < 25) {
            return "Few clouds";
        } else if (percentage < 50) {
            return "Moderate Clouds";
        } else if (percentage < 75) {
            return "Cloudy";
        } else {
            return "Very Cloudy";
        }
    }

    /**
     * Returns whether two dates are on the same day
     *
     * @param date        of type Date
     * @param anotherDate of type Date
     * @return true if the two dates are on the same day
     */
    private boolean isSameDay(Date date, Date anotherDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar anotherCalendar = Calendar.getInstance();
        anotherCalendar.setTime(anotherDate);
        return calendar.get(Calendar.DAY_OF_MONTH) == anotherCalendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Return the result of an API query depending on User query properties
     *
     * @return an instance of city
     * @throws IOException if API query is not valid
     */
    private City getCity() throws IOException {
        UserQuery query = user.getQuery();
        if (query.hasCountryCode() && query.hasZipCode()) {
            city = new City(APIQuery.QueryWithZip(String.valueOf(query.getZipCode()), query.getCountryCode()));
        } else if (!query.hasZipCode() && query.hasCountryCode()) {
            city = new City(APIQuery.QueryWithCountryCode(query.getCommandLine()[0], query.getCountryCode()));
        } else if (!query.hasZipCode() && !query.hasCountryCode()) {
            city = new City(APIQuery.QueryWithCity(query.getCommandLine()[0]));
        }
        return city;
    }
}
