package app.appmeteo.controller;

import app.appmeteo.model.City;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ResourceBundle;
import java.util.regex.PatternSyntaxException;

public class AppMeteoController implements Initializable {

    @FXML private Button helloWorldButton;
    @FXML private Button goodByeWorldButton;
    @FXML private Label label;

    private static boolean stopControl;

    //    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {}

    @FXML
    private void displayHelloWorld() {
        label.setText("Hello World");
        helloWorldButton.setVisible(false);
        if (!goodByeWorldButton.isVisible())
            goodByeWorldButton.setVisible(true);
    }

    @FXML
    private void goodByeWorld() {
        label.setText("");
        goodByeWorldButton.setVisible(false);
        if (!helloWorldButton.isVisible())
            helloWorldButton.setVisible(true);
    }

    public static void TreatQuery(Query query) throws PatternSyntaxException, IOException, InvalidParameterException {
        if (query.isValid()){
            if(query.getParameter().isEmpty()) {
                switch(query.getCommand()) {
                    case "help":
                        System.out.println("Here's some help : try weather + city name");
                    case "quit":
                        setStopControl(true);
                        System.out.println("Good bye ! ");
                        break;

                }
            }else {
                APIQuery.QueryWithCity(query.getParameter());
                City city = new City("Data.json");
                System.out.println(city.getName());
                switch (query.getCommand()) {
                    case "temp":
                        System.out.println("Temperature : " + (city.getWheatherNow().getTemp() - 273.15));
                        break;
                    case "weather":
                        // Prediction time
                        System.out.println("Time : " + LocalDateTime.ofEpochSecond(
                                city.getWheatherNow().getTime() + city.getTimezone(),
                                0, ZoneOffset.UTC));
                        // Weather
                        System.out.println("Weather : " + city.getWheatherNow().getMain());
                        // Temperature Celsius
                        System.out.println("Temperature : " + (city.getWheatherNow().getTemp() - 273.15));
                        // Wind speed meter/sec
                        System.out.println("Wind speed (m/s) : " + city.getWheatherNow().getWindSpeed());
                        break;
                    case "wind":
                        System.out.println("Wind speed (m/s) : " + city.getWheatherNow().getWindSpeed());
                        break;

                }
            }
        }else{
            throw new InvalidParameterException();
        }

    }

    public static void setStopControl(boolean value){
        stopControl = value;
    }

    public static boolean hasToStop() {
        return stopControl;
    }
}
