package app.appmeteo.controller.CLI.session;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.*;

import app.appmeteo.controller.APIQuery;
import app.appmeteo.controller.Commands;
import app.appmeteo.controller.User;
import app.appmeteo.model.City;
import app.appmeteo.model.DayWeather;
import app.appmeteo.model.HourWeather;
import  app.appmeteo.controller.CLI.CLIController;

public class WeatherSession extends Session {

    protected WeatherSession(User usr) throws IOException {
        super(usr);
    }

    protected WeatherSession(Scanner scan) throws IOException {
        super(scan);
    }

    @Override
    public void treatQuery() throws IOException {
        super.treatQuery();
        user.fixCommandline();
        user.fixCommandDate();
        if(!isOver) {
            if (!this.user.hasDate()) {
                try {
                    City city = new City(APIQuery.QueryStringWithCity(user.getCommandType()));
                    ArrayList<String> options = new ArrayList<>(Arrays.asList(this.user.getQuery(1)));
                    if(options.size()!=0) treatWeatherOptionsWoutDate(city, options);
                    else treatQueryWoutDate(city);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                try{
                    City city = new City(APIQuery.QueryStringWithCity(user.getCommandType()));
                    ArrayList<String> options = new ArrayList<>(Arrays.asList(this.user.getQuery(2)));
                    if(options.size()!=0) {treatWeatherOptionsWDate(city, options);}
                    else{treatQueryWDate(city);}
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public String toString() {
        return "Weather Session";
    }

    private void treatQueryWDate(City city) {
        ArrayList<DayWeather> weathers = city.getWeatherPerDay();
        for(DayWeather weather:weathers){
            Date weatherDate = new Date(weather.getTime()*1000);
            if (this.isSameDay(user.getDate(),weatherDate)){
                CLIController.addDisplay(weather.getMain());
                CLIController.addDisplay(String.valueOf(weather.getTempDay() - 273.15));
                CLIController.addDisplay(this.getWindOrientation(weather.getWindDeg()) + " " + weather.getWindSpeed());
            }
        }

    }

    private void treatQueryWoutDate(City city) {
        HourWeather weatherNow = city.getWeatherNow();
        CLIController.addDisplay(weatherNow.getMain());
        CLIController.addDisplay(String.valueOf(weatherNow.getTemp() - 273.15));
        CLIController.addDisplay(this.getWindOrientation(weatherNow.getWindDeg()) + " " + weatherNow.getWindSpeed());
    }



    private void treatWeatherOptionsWoutDate(City city, ArrayList<String> options){
        HourWeather weatherNow = city.getWeatherNow();
        for(String option :options){
            switch (option) {
                case Commands.WeatherCommands.TEMP:
                    CLIController.addDisplay(String.valueOf(weatherNow.getTemp() - 273.15));
                    break;
                case Commands.WeatherCommands.WIND:
                    CLIController.addDisplay(this.getWindOrientation(weatherNow.getWindDeg()) + " " + weatherNow.getWindSpeed());
                    break;
            }
        }

    }

    private void treatWeatherOptionsWDate(City city, ArrayList<String> options){

        ArrayList<DayWeather> weathers = city.getWeatherPerDay();

        for(DayWeather weather:weathers){
            Date weatherDate = new Date(weather.getTime()*1000);

            if (this.isSameDay(user.getDate(),weatherDate)){
                for(String option : options){
                    switch (option) {
                        case Commands.WeatherCommands.TEMP:
                            if(options.contains(Commands.WeatherCommands.MORNING)){
                                CLIController.addDisplay(String.valueOf(weather.getTempMorning() - 273.15));
                            } else if(options.contains(Commands.WeatherCommands.EVENING)){
                                CLIController.addDisplay(String.valueOf(weather.getTempEvening() - 273.15));
                            } else if(options.contains(Commands.WeatherCommands.NIGHT)){
                                CLIController.addDisplay(String.valueOf(weather.getTempNight() - 273.15));
                            } else CLIController.addDisplay(String.valueOf(weather.getTempDay() - 273.15));
                            break;
                        case Commands.WeatherCommands.WIND:
                            CLIController.addDisplay(this.getWindOrientation(weather.getWindDeg()) + " " + weather.getWindSpeed());
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

    private boolean isSameDay(Date date, Date anotherDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar anotherCalendar = Calendar.getInstance();
        anotherCalendar.setTime(anotherDate);
        return calendar.get(Calendar.DAY_OF_MONTH) == anotherCalendar.get(Calendar.DAY_OF_MONTH);
    }
}
