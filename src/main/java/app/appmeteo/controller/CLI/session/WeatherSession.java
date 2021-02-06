package app.appmeteo.controller.CLI.session;

import java.io.IOException;
import java.util.Scanner;

import app.appmeteo.controller.User;

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

    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public String toString() {
        return "Weather Session";
    }
}
