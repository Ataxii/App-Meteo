package app.appmeteo.controller.CLI.session;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

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
        if(!this.user.hasDate() && !this.isOver) {
            try {
                City city = new City(APIQuery.QueryStringWithCity(user.getCommandType()));
                treatWeatherOptions(city, this.user.getQuery(1));
            } catch (IOException e) {
                e.printStackTrace();
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

    private void treatWeatherOptions(City city, String[] options){
        if(!this.user.hasDate()) {
            HourWeather weatherNow = city.getWeatherNow();
            if(options.length!=0){
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
            }else{
                CLIController.addDisplay(weatherNow.getMain());
                CLIController.addDisplay(String.valueOf(weatherNow.getTemp() - 273.15));
                CLIController.addDisplay(this.getWindOrientation(weatherNow.getWindDeg()) + " " + weatherNow.getWindSpeed());
            }
        } /*else{
            ArrayList<DayWeather> weathers = city.getWeatherPerDay();
            switch (option)$
            long date;
            for(DayWeather weather : weathers){
                if(weather.getTime()==date){

                }
            }
        }*/
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

    private long getProperDate(String dayTime) {
        Timestamp userDate;
        switch(dayTime){
            case "morning":
                userDate = new Timestamp(this.user.getDate().get(2),this.user.getDate().get(1),this.user.getDate().get(0),8,0,0,0);
                break;
            case "afternoon":
                userDate = new Timestamp(this.user.getDate().get(2),this.user.getDate().get(1),this.user.getDate().get(0),16,0,0,0);
                break;
            case "night":
                userDate = new Timestamp(this.user.getDate().get(2),this.user.getDate().get(1),this.user.getDate().get(0),21,0,0,0);
                break;
            default:
                userDate = new Timestamp(this.user.getDate().get(2),this.user.getDate().get(1),this.user.getDate().get(0),12,0,0,0);
                break;
        }
        return userDate.getTime();
    }
}
