package app.appmeteo.controller.CLI.session;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import app.appmeteo.controller.APIQuery;
import app.appmeteo.controller.Commands;
import app.appmeteo.model.User;
import app.appmeteo.model.City;
import app.appmeteo.model.DayWeather;
import app.appmeteo.model.HourWeather;
import app.appmeteo.controller.CLI.CLIController;

/**
 * A type of Session to display weather conditions corresponding to user's query
 */
public class WeatherSession extends Session {

    protected WeatherSession(User usr) {
        super(usr);
    }

    protected WeatherSession(Scanner scan) throws IOException {
        super(scan);
    }

    private City city; // city of user's query, initialised in treatQuery

    /**
     * Initialises City object and calls for correct function depending on user's query
     */
    @Override
    public void treatQuery() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        super.treatQuery();
        if (isOver) return;
        if (user.getQuery().getCommandLineOption(0).equals(Commands.CommandType.HELP)) return;

        user.getQuery().fixCommandline();
        ArrayList<String> options = user.getQuery().getOptions();
        city = new City(APIQuery.QueryStringWithCity(user.getQuery().getCommandLineOption(0)));

        if (!user.getQuery().hasDate()) {
            CLIController.addDisplay("Weather in " + city.getName() + " :\n");
            displayTodayWeather(options);
        } else {
            CLIController.addDisplay("Weather in " + city.getName() + " on " + sdf.format(user.getQuery().getDate()) + " : \n");
            displayDateWeather(options);
        }
    }

    /**
     * @return a String containing help on how to use a weather session
     */
    @Override
    public String getHelp() {
        return "Use command:\n"
                + "  City Name [-options]             to know how's the weather like right now\n"
                + "  City Name DD/MM/YYYY [-options]  to specify a certain date within the next 7 days\n"
                + "  'quit'                           to get back to main session\n"
                + "  'session' + 'session command'    to process another session specific query\n"
                + "Options : \n"
                + "     '-temp'      displays temperature for every part of the day\n"
                + "     '-wind'      displays wind speed and wind orientation\n"
                + "     '-morning'   displays weather during the morning\n"
                + "     '-evening'   displays weather during the evening\n"
                + "     '-night'     displays weather during the night\n";
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

        // Raw Call
        if (options.size() == 0) {
            CLIController.addDisplay("\t" + weatherNow.getMain());
            CLIController.addDisplay("Temperature : " + weatherNow.getTemp() + "°C");
            CLIController.addDisplay("Feels Like : " + weatherNow.getTempFeelsLike() + "°C");
            CLIController.addDisplay("Cloud cover : " + cloudinessAppreciation(weatherNow.getCloudiness()));
            CLIController.addDisplay("Probability of Precipitation : " + city.getWeatherPerHour().get(0).getPop() + "%");
            CLIController.addDisplay("Wind : \n"
                    + "\tOrientation : " + getWindOrientation(weatherNow.getWindDeg()) + "\n"
                    + "\tSpeed : " + weatherNow.getWindSpeed() + " km/h");
        } else {
            // Options' info display
            for (String option : options) {
                switch (option) {
                    case Commands.WeatherCommands.TEMP:
                        CLIController.addDisplay("Temperature : \n"
                                + "\tMin : " + weatherToday.getTempMin() + "°C   Max : " + weatherToday.getTempMax() + "°C\n"
                                + "\tMorning : " + weatherToday.getTempMorning() + "°C (Feels like " + weatherToday.getTempFeelsLikeMorning() + "°C)\n"
                                + "\tEvening : " + weatherToday.getTempEvening() + "°C (Feels like " + weatherToday.getTempFeelsLikeEvening() + "°C)\n"
                                + "\tNight : " + weatherToday.getTempNight() + "°C (Feels like " + weatherToday.getTempFeelsLikeNight() + "°C)\n");
                        break;
                    case Commands.WeatherCommands.WIND:
                        CLIController.addDisplay("Wind : \n"
                                + "\tOrientation : " + getWindOrientation(weatherNow.getWindDeg()) + "\n"
                                + "\tSpeed : " + weatherNow.getWindSpeed() + " km/h");
                        break;
                    case Commands.WeatherCommands.MORNING:
                        CLIController.addDisplay("Morning \n");
                        CLIController.addDisplay("\t" + weatherToday.getMain());
                        CLIController.addDisplay("Temperature : " + weatherToday.getTempMorning() + "°C");
                        CLIController.addDisplay("Feels Like : " + weatherToday.getTempFeelsLikeMorning() + "°C");
                        CLIController.addDisplay("Cloud cover : " + cloudinessAppreciation(weatherToday.getCloudiness()));
                        CLIController.addDisplay("Probability of Precipitation : " + weatherToday.getPop() + "%");
                        CLIController.addDisplay("Wind : \n"
                                + "\tOrientation : " + getWindOrientation(weatherToday.getWindDeg()) + "\n"
                                + "\tSpeed : " + weatherToday.getWindSpeed() + " km/h");
                        return;
                    case Commands.WeatherCommands.EVENING:
                        CLIController.addDisplay("Evening \n");
                        CLIController.addDisplay("\t" + weatherToday.getMain());
                        CLIController.addDisplay("Temperature : " + weatherToday.getTempEvening() + "°C");
                        CLIController.addDisplay("Feels Like : " + weatherToday.getTempFeelsLikeEvening() + "°C");
                        CLIController.addDisplay("Cloud cover : " + cloudinessAppreciation(weatherToday.getCloudiness()));
                        CLIController.addDisplay("Probability of Precipitation : " + weatherToday.getPop() + "%");
                        CLIController.addDisplay("Wind : \n"
                                + "\tOrientation : " + getWindOrientation(weatherToday.getWindDeg()) + "\n"
                                + "\tSpeed : " + weatherToday.getWindSpeed() + " km/h");
                        return;
                    case Commands.WeatherCommands.NIGHT:
                        CLIController.addDisplay("Night \n");
                        CLIController.addDisplay("\t" + weatherToday.getMain());
                        CLIController.addDisplay("Temperature : " + weatherToday.getTempNight() + "°C");
                        CLIController.addDisplay("Feels Like : " + weatherToday.getTempFeelsLikeNight() + "°C");
                        CLIController.addDisplay("Cloud cover : " + cloudinessAppreciation(weatherToday.getCloudiness()));
                        CLIController.addDisplay("Probability of Precipitation : " + weatherToday.getPop() + "%");
                        CLIController.addDisplay("Wind : \n"
                                + "\tOrientation : " + getWindOrientation(weatherToday.getWindDeg()) + "\n"
                                + "\tSpeed : " + weatherToday.getWindSpeed() + " km/h");
                        return;
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

        // Search for correct DayWeather
        for (DayWeather w : city.getWeatherPerDay()) {
            Date weatherDate = new Date(w.getDate() * 1000);
            if (isSameDay(user.getQuery().getDate(), weatherDate)) {
                weather = w;
            }
        }

        // If user's date is not valid
        if (weather == null) {
            CLIController.addDisplay("Sorry, no information about how's the weather like in :\n" +
                    city.getName() + " on " + sdf.format(user.getQuery().getDate()) + "\n" +
                    "The date must be between " + sdf.format(new Date(city.getWeatherPerDay().get(0).getDate() * 1000)) +
                    " and " + sdf.format(new Date(city.getWeatherPerDay().get(7).getDate() * 1000)));
            return;
        }

        // Raw call
        if (options.size() == 0) {
            CLIController.addDisplay("\t" + weather.getMain());
            CLIController.addDisplay("Temperature : \n"
                    + "\tMorning :" + weather.getTempMorning() + "°C (Feels like " + weather.getTempFeelsLikeMorning() + "°C)\n"
                    + "\tEvening :" + weather.getTempEvening() + "°C (Feels like " + weather.getTempFeelsLikeEvening() + "°C)\n"
                    + "\tNight :" + weather.getTempNight() + "°C (Feels like " + weather.getTempFeelsLikeNight() + "°C)");
            sdf = new SimpleDateFormat("HH:mm");
            CLIController.addDisplay("Sunrise : " + sdf.format(new Date(weather.getSunrise() * 1000)));
            CLIController.addDisplay("Sunset : " + sdf.format(new Date(weather.getSunset() * 1000)));
            CLIController.addDisplay("Cloud cover : " + cloudinessAppreciation(weather.getCloudiness()));
            CLIController.addDisplay("Probability of Precipitation : " + weather.getPop() + "%");
            CLIController.addDisplay("Wind : \n"
                    + "\tOrientation : " + getWindOrientation(weather.getWindDeg()) + "\n"
                    + "\tSpeed : " + weather.getWindSpeed() + " km/h");
        } else {
            // Options' info display
            for (String option : options) {
                switch (option) {
                    case Commands.WeatherCommands.TEMP:
                        CLIController.addDisplay("Temperature : \n"
                                + "\tMin : " + weather.getTempMin() + "°C   Max : " + weather.getTempMax() + "°C\n"
                                + "\tMorning : " + weather.getTempMorning() + "°C (Feels like " + weather.getTempFeelsLikeMorning() + "°C)\n"
                                + "\tEvening : " + weather.getTempEvening() + "°C (Feels like " + weather.getTempFeelsLikeEvening() + "°C)\n"
                                + "\tNight : " + weather.getTempNight() + "°C (Feels like " + weather.getTempFeelsLikeNight() + "°C)\n");
                        break;
                    case Commands.WeatherCommands.WIND:
                        CLIController.addDisplay("Wind : \n"
                                + "\tOrientation : " + getWindOrientation(weather.getWindDeg()) + "\n"
                                + "\tSpeed : " + weather.getWindSpeed() + " m/s");
                        break;
                    case Commands.WeatherCommands.MORNING:
                        CLIController.addDisplay("Morning \n");
                        CLIController.addDisplay("\t" + weather.getMain());
                        CLIController.addDisplay("Temperature : " + weather.getTempMorning() + "°C");
                        CLIController.addDisplay("Feels Like : " + weather.getTempFeelsLikeMorning() + "°C");
                        CLIController.addDisplay("Cloud cover : " + cloudinessAppreciation(weather.getCloudiness()));
                        CLIController.addDisplay("Probability of Precipitation : " + weather.getPop() + "%");
                        CLIController.addDisplay("Wind : \n"
                                + "\tOrientation : " + getWindOrientation(weather.getWindDeg()) + "\n"
                                + "\tSpeed : " + weather.getWindSpeed() + " km/h");
                        return;
                    case Commands.WeatherCommands.EVENING:
                        CLIController.addDisplay("Evening \n");
                        CLIController.addDisplay("\t" + weather.getMain());
                        CLIController.addDisplay("Temperature : " + weather.getTempEvening() + "°C");
                        CLIController.addDisplay("Feels Like : " + weather.getTempFeelsLikeEvening() + "°C");
                        CLIController.addDisplay("Cloud cover : " + cloudinessAppreciation(weather.getCloudiness()));
                        CLIController.addDisplay("Probability of Precipitation : " + weather.getPop() + "%");
                        CLIController.addDisplay("Wind : \n"
                                + "\tOrientation : " + getWindOrientation(weather.getWindDeg()) + "\n"
                                + "\tSpeed : " + weather.getWindSpeed() + " km/h");
                        return;
                    case Commands.WeatherCommands.NIGHT:
                        CLIController.addDisplay("Night \n");
                        CLIController.addDisplay("\t" + weather.getMain());
                        CLIController.addDisplay("Temperature : " + weather.getTempNight() + "°C");
                        CLIController.addDisplay("Feels Like : " + weather.getTempFeelsLikeNight() + "°C");
                        CLIController.addDisplay("Cloud cover : " + cloudinessAppreciation(weather.getCloudiness()));
                        CLIController.addDisplay("Probability of Precipitation : " + weather.getPop() + "%");
                        CLIController.addDisplay("Wind : \n"
                                + "\tOrientation : " + getWindOrientation(weather.getWindDeg()) + "\n"
                                + "\tSpeed : " + weather.getWindSpeed() + " km/h");
                        return;
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
    private String getWindOrientation(int degree) {

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
    private String cloudinessAppreciation(int percentage) {
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
     * Returns weather or not two dates are on the same day
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
}
