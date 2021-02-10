package app.appmeteo.controller.CLI.session;

import java.io.IOException;
import java.util.*;

import app.appmeteo.controller.APIQuery;
import app.appmeteo.controller.Commands;
import app.appmeteo.model.User;
import app.appmeteo.model.City;
import app.appmeteo.model.DayWeather;
import app.appmeteo.model.HourWeather;
import app.appmeteo.controller.CLI.CLIController;

public class WeatherSession extends Session {

    protected WeatherSession(User usr) {
        super(usr);
    }

    protected WeatherSession(Scanner scan) throws IOException {
        super(scan);
    }

    private City city;

    @Override
    public void treatQuery() throws IOException {
        super.treatQuery();
        if (isOver) return;
        if (user.getQuery().getCommandType().equals(Commands.CommandType.HELP)) return;

        user.getQuery().fixCommandline();
        ArrayList<String> options = user.getQuery().getOptions();
        city = new City(APIQuery.QueryStringWithCity(user.getQuery().getCommandType()));

        if (!this.user.getQuery().hasDate()) {
            CLIController.addDisplay(city.getName() + " Today : \n" );
            if (options.size() != 0) treatWeatherOptionsWoutDate(options);
            else treatQueryWoutDate();
        } else {
            CLIController.addDisplay(city.getName() + " " + user.getQuery().getDate() + " : \n");
            if (options.size() != 0) { treatWeatherOptionsWDate(options); }
            else { treatQueryWDate(); }
        }
    }

    @Override
    public String getHelp() {
        return "Use command:\n"
                + "  existing town name [date within the next 7 days] [-options]\n"
                + "  'quit'                           to get back to main session\n"
                + "  'session' + 'session command'    to process another session specific query\n"
                + "  'help'                           to get help about using this session\n"
                + "Options : \n"
                + "     '-temp' + [day time : -mng -eng -nght]    displays temperature in Celsius.\n"
                + "                                               If a date is provided, day time (morning, evening or night)\n"
                + "                                               option can be added \n "
                + "     '-wind'   displays wind speed and wind orientation\n";
    }

    @Override
    public String toString() {
        return "Weather Session";
    }

    private void treatQueryWDate() {
        ArrayList<DayWeather> weathers = city.getWeatherPerDay();
        for (DayWeather weather : weathers) {
            Date weatherDate = new Date(weather.getTime() * 1000);
            if (this.isSameDay(user.getQuery().getDate(), weatherDate)) {
                CLIController.addDisplay(weather.getMain());
                CLIController.addDisplay("Temperature : "
                        + weather.getTempDay() + "°C");
                CLIController.addDisplay("Wind : \n"
                        + "\tOrientation : " + this.getWindOrientation(weather.getWindDeg()) + "\n"
                        + "\tSpeed : " + weather.getWindSpeed() + " m/s");
            }
        }

    }

    private void treatQueryWoutDate() {
        HourWeather weatherNow = city.getWeatherNow();
        CLIController.addDisplay(weatherNow.getMain());
        CLIController.addDisplay("Temperature : "
                + weatherNow.getTemp() + "°C");
        CLIController.addDisplay("Wind : \n"
                + "\tOrientation : " + this.getWindOrientation(weatherNow.getWindDeg()) + "\n"
                + "\tSpeed : " + weatherNow.getWindSpeed() + " m/s");
    }


    private void treatWeatherOptionsWoutDate(ArrayList<String> options) {
        HourWeather weatherNow = city.getWeatherNow();
        for (String option : options) {
            switch (option) {
                case Commands.WeatherCommands.TEMP:
                    CLIController.addDisplay("Temperature : "
                            + weatherNow.getTemp() + "°C");
                    break;
                case Commands.WeatherCommands.WIND:
                    CLIController.addDisplay("Wind : \n"
                            + "\tOrientation : " + this.getWindOrientation(weatherNow.getWindDeg()) + "\n"
                            + "\tSpeed : " + weatherNow.getWindSpeed() + " m/s");
                    break;
            }
        }

    }

    private void treatWeatherOptionsWDate(ArrayList<String> options) {

        ArrayList<DayWeather> weathers = city.getWeatherPerDay();

        for (DayWeather weather : weathers) {
            Date weatherDate = new Date(weather.getTime() * 1000);

            if (this.isSameDay(user.getQuery().getDate(), weatherDate)) {
                for (String option : options) {
                    switch (option) {
                        case Commands.WeatherCommands.TEMP:
                            CLIController.addDisplay("Temperature : \n");
                            CLIController.addDisplay("Day : " + weather.getTempDay() + "°C");
                            break;
                        case Commands.WeatherCommands.WIND:
                            CLIController.addDisplay("Wind : \n"
                                    + "\tOrientation : " + this.getWindOrientation(weather.getWindDeg()) + "\n"
                                    + "\tSpeed : " + weather.getWindSpeed() + " m/s");
                            break;
                        case Commands.WeatherCommands.MORNING:
                            CLIController.addDisplay("It's the morning");
                            break;
                        case Commands.WeatherCommands.EVENING:
                            CLIController.addDisplay("It's the evening");
                            break;
                        case Commands.WeatherCommands.NIGHT:
                            CLIController.addDisplay("It's the night");
                            break;
                    }
                }
            }
        }
    }


    private String getWindOrientation(int winddegree) {

        if (winddegree >= 11 && winddegree < 34) {
            return "NNE";
        }
        if (winddegree >= 34 && winddegree < 57) {
            return "NE";
        }
        if (winddegree >= 57 && winddegree < 79) {
            return "ENE";
        }
        if (winddegree >= 79 && winddegree < 102) {
            return "E";
        }
        if (winddegree >= 102 && winddegree < 124) {
            return "ESE";
        }
        if (winddegree >= 124 && winddegree < 147) {
            return "SE";
        }
        if (winddegree >= 147 && winddegree < 169) {
            return "SSE";
        }
        if (winddegree >= 169 && winddegree < 192) {
            return "S";
        }
        if (winddegree >= 192 && winddegree < 214) {
            return "SSW";
        }
        if (winddegree >= 214 && winddegree < 237) {
            return "SW";
        }
        if (winddegree >= 237 && winddegree < 259) {
            return "WSW";
        }
        if (winddegree >= 259 && winddegree < 282) {
            return "W";
        }
        if (winddegree >= 282 && winddegree < 304) {
            return "WNW";
        }
        if (winddegree >= 304 && winddegree < 327) {
            return "NW";
        }
        if (winddegree >= 327 && winddegree < 349) {
            return "NNW";
        }
        return "N";
    }

    private boolean isSameDay(Date date, Date anotherDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar anotherCalendar = Calendar.getInstance();
        anotherCalendar.setTime(anotherDate);
        return calendar.get(Calendar.DAY_OF_MONTH) == anotherCalendar.get(Calendar.DAY_OF_MONTH);
    }
}
