package app.appmeteo.controller.GUI;

// app imports

import app.appmeteo.controller.APIQuery;
import app.appmeteo.model.FavouriteList;
import app.appmeteo.model.City;
import app.appmeteo.model.DayWeather;

// javafx imports
import app.appmeteo.model.Favourite;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

// java utilities import
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

public class GUIController implements Initializable {

    public AnchorPane background;
    public SimpleDateFormat hourOnly;
    public City currentCity;

    /**
     * Called on app's launch, initialises every grid with weather information of the last research's city
     * If it's the first time the app is launched, initialises with Paris' information
     *
     * @param location  ignored
     * @param resources ignored
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hourOnly = new SimpleDateFormat("HH:mm");
        hourOnly.setTimeZone(TimeZone.getTimeZone("LONDON"));
        initCurrentCity();
        updateWeather();
        initFavourite();
        initView();
    }

    /**
     * the current city will either be the last research that was made before leaving the application, or the default
     * city, paris.
     */
    private void initCurrentCity() {
        String[] lastResearch = GUIUtilities.getAppState()[0].split(" ");
        String currentCityName;
        String currentCityCountryCode;
        if (lastResearch.length == 1) {
            currentCityName = "paris";
            currentCityCountryCode = "FR";
        } else {
            currentCityName = lastResearch[0].replace('_', ' ');
            currentCityCountryCode = lastResearch[1];
        }
        if (currentCityName.length() == 1) currentCityName = "paris";
        try {
            String response = APIQuery.QueryWithCountryCode(currentCityName, currentCityCountryCode);
            currentCity = new City(response);
        } catch (IOException e) {
            System.out.println("Error : the API is either not responding or blocking the key, check account");
            System.exit(1);
        }
    }

    private void initView() {
        darkModeOn = Boolean.parseBoolean(GUIUtilities.getAppState()[1]);
        updateBackground();
        updateTheme();
    }


    /**
     * the current city will either be the last research that was made before leaving the application, or the default
     * city, paris.
     */

    public void initFavourite() {
        try {
            favouriteList = new FavouriteList();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error : either make sure favourites.txt is not corrupted or delete it");
            System.exit(-1);
        }
        initFavouriteGrid();
        GUIAnimations.createFavouritePanelTranslation(favouriteBorderPane);
        favouritePanelIsDragged = false;
        updateFavouriteIndicator();
    }

    public void initFavouriteGrid() {
        favouriteGrid.getRowConstraints().clear();
        for (Favourite favourite : favouriteList.getList()) {
            try {
                // get related city
                City favouriteCity = new City(APIQuery.QueryWithCountryCode(favourite.getName().replace('_', ' ')
                        , favourite.getCountryCode()));
                // add row into the grid
                addRowToFavouriteGrid(favouriteCity);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error : one of your favourite (" + favourite.getName().replace('_', ' ') + ") failed to load");
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////VIEW RELATED/////////////////////////////////////////////////////
    public ImageView backgroundImage;
    public boolean darkModeOn;
    public ImageView themeButton;

    public void onThemeModeButtonClicked() {
        darkModeOn = !darkModeOn;
        updateTheme();
        if (darkModeOn) {
            themeButton.setImage(new Image("app/appmeteo/images/luminosity-dark.png"));
        } else themeButton.setImage(new Image("app/appmeteo/images/luminosity.png"));
    }

    /**
     * depending on the current city icon, update the background to an appropriate one. Called after updating the current
     * city in {@link #searchForCity(KeyEvent)}.
     */
    public void updateBackground() {
        switch (currentCity.getWeatherNow().getIcon().substring(20, 23)) {
            case "01d":
            case "02d":
                backgroundImage.setImage(new Image("app/appmeteo/images/day.png"));
                break;
            case "01n":
            case "02n":
                backgroundImage.setImage(new Image("app/appmeteo/images/night.png"));
                break;
            case "03d":
            case "03n":
            case "04d":
            case "04n":
                backgroundImage.setImage(new Image("app/appmeteo/images/cloud.png"));
                break;
            case "09d":
            case "09n":
                backgroundImage.setImage(new Image("app/appmeteo/images/rain.png"));
                break;
            case "10d":
            case "10n":
            case "11d":
            case "11n":
                backgroundImage.setImage(new Image("app/appmeteo/images/thunderstorm.png"));
                break;
            case "13d":
            case "13n":
                backgroundImage.setImage(new Image("app/appmeteo/images/snow.png"));
                break;
            case "50d":
            case "50n":
                backgroundImage.setImage(new Image("app/appmeteo/images/fog.png"));
                break;
        }
    }

    public void updateTheme() {
        if (darkModeOn) activateDarkMode();
        else activateLightMode();
        updateWeather();
        updateFavouriteIndicator();
        GUIUtilities.saveAppState(currentCity, darkModeOn);
    }

    private void activateDarkMode() {
        // panes dark mode
        dayWeatherPane.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;-fx-background-color: #0c0e14");
        hourWeatherPane.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;-fx-background-color: #0c0e14");
        hourWeatherGrid.setStyle("-fx-background-color: #0c0e14");
        background.setStyle("-fx-background-color: #000000");
    }

    private void activateLightMode() {
        // panes light mode
        dayWeatherPane.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;-fx-background-color: #ebddd2");
        hourWeatherPane.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;-fx-background-color: #ebddd2");
        hourWeatherGrid.setStyle("-fx-background-color: #ebddd2");
        background.setStyle("-fx-background-color: #f3eae4");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////WEATHER RELATED//////////////////////////////////////////////////

    public AnchorPane dayWeatherPane;
    public GridPane dayWeatherGrid;
    public AnchorPane hourWeatherPane;
    public ScrollPane hourWeatherScrollPane;
    public GridPane hourWeatherGrid;
    public GridPane weatherInfoGrid;
    public Pane moreInfoPane;

    public TextField searchBar;
    public Text errorText;

    public ImageView hourWeatherGridLeftArrow;
    public ImageView hourWeatherGridRightArrow;

    /**
     * When a key is pressed on the keyboard, check if it is the ENTER key, then take what's inside the search bar and
     * try to interpret it. On success, all weather infos will be updated, as well as the background image. On fail, play
     * an error animation.
     *
     * @param k the key event, containing key pressed info.
     */
    public void searchForCity(KeyEvent k) {
        if (!k.getCode().equals(KeyCode.ENTER)) return;

        // interpretation
        String[] userInput = searchBar.getText().split(" ");
        String cityName = null;
        String countryCode = null;
        String zipCode = null;
        for (String currentInput : userInput) {
            currentInput = new String(currentInput.getBytes(StandardCharsets.UTF_8));
            if (zipCode == null && Character.isDigit(currentInput.charAt(0))) zipCode = currentInput;
            else if (countryCode == null && currentInput.length() == 2) countryCode = currentInput;
            else if (cityName == null) cityName = currentInput;
            else cityName += " " + currentInput;
        }
        // update the GUI
        try {
            String response = GUIUtilities.getQueryResponse(cityName, countryCode, zipCode);
            currentCity = new City(response);
            errorText.setText("");
            searchBar.setText("");
            updateWeather();
            updateBackground();
            updateFavouriteIndicator();
            GUIUtilities.saveAppState(currentCity, darkModeOn);
        } catch (IOException e) {
            GUIAnimations.playSearchErrorAnimation(searchBar, errorText);
        }
    }

    /**
     * Update weather information with the current city. weather info include : main temp, wind, country code..;
     * a hourly weather grid, from 0 pm to 12 am; a daily weather grid, from the current day to 7 days after.
     */
    public void updateWeather() {
        clearGrids();
        fillWeatherMainInfos();
        fillWeatherHourGrid();
        fillWeatherDayGrid();
    }

    /**
     * Clears every weather grid. Doesn't clear the favourites grid
     */
    public void clearGrids() {
        dayWeatherGrid.getChildren().clear();
        hourWeatherGrid.getChildren().clear();
        hourWeatherGrid.getColumnConstraints().clear();
        weatherInfoGrid.getChildren().clear();
    }

    /**
     * Fills the weatherInfoGrid corresponding to current weather of the city
     */
    private void fillWeatherMainInfos() {
        // City Name
        Text cityName = new Text(currentCity.getName());
        weatherInfoGrid.add(cityName, 0, 0);
        GridPane.setHalignment(cityName, HPos.LEFT);
        cityName.setFont(Font.font("Rockwell", 32));

        // Current Weather Image
        ImageView currentWeatherImg = new ImageView(new Image(currentCity.getWeatherNow().getIcon()));
        currentWeatherImg.setFitWidth(130);
        currentWeatherImg.setPreserveRatio(true);
        weatherInfoGrid.add(currentWeatherImg, 0, 1);
        GridPane.setHalignment(currentWeatherImg, HPos.RIGHT);

        // Current Temperature
        Text currentTemp = new Text(currentCity.getWeatherNow().getTemp() + " \u00b0C");
        currentTemp.setFont(Font.font("Rockwell", 32));
        weatherInfoGrid.add(currentTemp, 1, 1);
        GridPane.setHalignment(currentTemp, HPos.LEFT);
        GridPane.setValignment(currentTemp, VPos.TOP);

        // Current Hour
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        Text currentHour = new Text(hourOnly.format((new Date(now.toEpochSecond() * 1000 + currentCity.getTimezone() * 1000))));
        currentHour.setFont(Font.font("Rockwell", 32));
        weatherInfoGrid.add(currentHour, 4, 0);
        GridPane.setHalignment(currentHour, HPos.RIGHT);

        // Wind Image
        ImageView windImg;
        if (darkModeOn) {
            windImg = new ImageView(new Image("app/appmeteo/images/wind.png"));
        } else windImg = new ImageView(new Image("app/appmeteo/images/wind-black.png"));
        windImg.setFitWidth(50);
        windImg.setPreserveRatio(true);
        weatherInfoGrid.add(windImg, 2, 1);
        GridPane.setHalignment(windImg, HPos.CENTER);
        GridPane.setValignment(windImg, VPos.CENTER);

        // WindText
        Text windText = new Text(currentCity.getWeatherNow().getWindSpeed() + " km/h");
        windText.setFont(Font.font("Arial", 18));
        weatherInfoGrid.add(windText, 3, 1);
        GridPane.setHalignment(windText, HPos.LEFT);
        GridPane.setValignment(windText, VPos.CENTER);

        // Cardinal Points
        ImageView cardinalPts = null;
        if (darkModeOn) {
            cardinalPts = new ImageView(new Image("app/appmeteo/images/orientation-white.png"));
        } else cardinalPts = new ImageView(new Image("app/appmeteo/images/cardinalPts.png"));
        cardinalPts.setFitWidth(100);
        cardinalPts.setPreserveRatio(true);
        weatherInfoGrid.add(cardinalPts, 4, 1);
        GridPane.setHalignment(cardinalPts, HPos.LEFT);
        GridPane.setValignment(cardinalPts, VPos.CENTER);

        // Wind Arrow
        ImageView windArrow = new ImageView(new Image("app/appmeteo/images/windArrow.png"));
        windArrow.setFitWidth(100);
        windArrow.setPreserveRatio(true);
        windArrow.setRotate(currentCity.getWeatherNow().getWindDeg());
        weatherInfoGrid.add(windArrow, 4, 1);
        GridPane.setHalignment(windArrow, HPos.LEFT);
        GridPane.setValignment(windArrow, VPos.CENTER);

        // Water Drop
        ImageView waterDrop;
        if (currentCity.getWeatherNow().getHumidity() < 25)
            waterDrop = new ImageView(new Image("app/appmeteo/images/humidity0-25.png"));
        else if (currentCity.getWeatherNow().getHumidity() < 50) {
            waterDrop = new ImageView(new Image("app/appmeteo/images/humidity25-50.png"));
        } else if (currentCity.getWeatherNow().getHumidity() < 75) {
            waterDrop = new ImageView(new Image("app/appmeteo/images/humidity50-75.png"));
        } else waterDrop = new ImageView(new Image("app/appmeteo/images/humidity75-100.png"));
        waterDrop.setFitWidth(30);
        waterDrop.setPreserveRatio(true);
        weatherInfoGrid.add(waterDrop, 2, 2);
        GridPane.setHalignment(waterDrop, HPos.CENTER);
        GridPane.setValignment(waterDrop, VPos.TOP);

        // Humidity Text
        Text humidityText = new Text("\n" + currentCity.getWeatherNow().getHumidity() + " %");
        humidityText.setFont(Font.font("Arial", 18));
        weatherInfoGrid.add(humidityText, 3, 2);
        GridPane.setHalignment(humidityText, HPos.LEFT);
        GridPane.setValignment(humidityText, VPos.TOP);

        // Fill paint
        if (darkModeOn) {
            cityName.setFill(Paint.valueOf("#F5F5DC"));
            currentTemp.setFill(Paint.valueOf("#F5F5DC"));
            currentHour.setFill(Paint.valueOf("#91919C"));
            windText.setFill(Paint.valueOf("#F5F5DC"));
            humidityText.setFill(Paint.valueOf("#F5F5DC"));
        } else {
            cityName.setFill(Paint.valueOf("#222225"));
            currentTemp.setFill(Paint.valueOf("#222225"));
            currentHour.setFill(Paint.valueOf("#272728"));
            windText.setFill(Paint.valueOf("#222225"));
            humidityText.setFill(Paint.valueOf("#222225"));
        }
    }

    /**
     * Fills the weatherHourGrid with the currentCity.getWeatherPerHour()'s info
     */
    private void fillWeatherHourGrid() {
        for (int k = 0; k < 25; k++) {
            // create new nodes
            Text hour = new Text(hourOnly.format(new Date(currentCity.getWeatherPerHour().get(k).getDate() * 1000 + currentCity.getTimezone() * 1000)) + "");
            hour.setFont(Font.font("Coolvetica", 16));
            Text tempHour = new Text(currentCity.getWeatherPerHour().get(k).getTemp() + " \u00b0C");
            tempHour.setFont(Font.font("sans serif", 14));
            ImageView hourIcon = new ImageView(new Image(currentCity.getWeatherPerHour().get(k).getIcon()));
            hourIcon.setFitWidth(75);
            hourIcon.setPreserveRatio(true);
            // Fill paint
            if (darkModeOn) {
                hour.setFill(Paint.valueOf("#91919C"));
                tempHour.setFill(Paint.valueOf("#F5F5DC"));
            } else {
                hour.setFill(Paint.valueOf("#222225"));
                tempHour.setFill(Paint.valueOf("#252528"));
            }
            // add column constraint
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPrefWidth(150);
            colConstraints.setMinWidth(100);
            hourWeatherGrid.getColumnConstraints().add(colConstraints);
            // add nodes into the grid
            hourWeatherGrid.add(hour, k, 0);
            GridPane.setHalignment(hour, HPos.CENTER);
            hourWeatherGrid.add(hourIcon, k, 1);
            GridPane.setHalignment(hourIcon, HPos.CENTER);
            hourWeatherGrid.add(tempHour, k, 2);
            GridPane.setHalignment(tempHour, HPos.CENTER);
        }
    }

    private void fillWeatherDayGrid() {
        int rowIndex = 0;
        for (DayWeather dayWeather : currentCity.getWeatherPerDay()) {
            // the name of the day
            Text dayName;
            if (rowIndex == 0) {
                dayName = new Text("Today");
            } else if (rowIndex == 1) {
                dayName = new Text("Tomorrow");
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("EE MMM d", Locale.ENGLISH);
                dayName = new Text(dateFormat.format(new Date(dayWeather.getDate() * 1000)));
            }
            // the temp of the day
            Text tempDay = new Text(dayWeather.getTempDay() + " \u00b0C");
            dayName.setFont(Font.font("Coolvetica", 16));
            tempDay.setFont(Font.font("sans serif", 14));
            // Fill paint
            if (darkModeOn) {
                dayName.setFill(Paint.valueOf("#F5F5DC"));
                tempDay.setFill(Paint.valueOf("#F5F5DC"));
            } else {
                dayName.setFill(Paint.valueOf("#222225"));
                tempDay.setFill(Paint.valueOf("#222225"));
            }
            // the corresponding weather image
            ImageView icon = new ImageView(new Image(dayWeather.getIcon()));
            icon.setFitHeight(70);
            icon.setFitWidth(49);
            icon.setPreserveRatio(true);
            // Event handling for more infos
            dayName.setOnMouseEntered(entered -> {
                moreInfoPane.setTranslateY(entered.getSceneY() - moreInfoPane.getPrefHeight());
                moreInfoPane.setLayoutX(dayWeatherPane.getLayoutX() - moreInfoPane.getPrefWidth());
                moreInfoPane.toFront();
                moreInfoPane.setVisible(true);
                SimpleDateFormat dateFormat = new SimpleDateFormat("EE MMM d", Locale.ENGLISH);
                Text date = new Text("\n\n  " + dateFormat.format(new Date(dayWeather.getDate() * 1000)));
                Text info = new Text(
                        "\n\n\n\tTemperature Min : " + dayWeather.getTempMin() + " \u00b0C\n"
                                + "\tTemperature Max : " + dayWeather.getTempMax() + " \u00b0C\n"
                                + "\tTemperature Morning : " + dayWeather.getTempMorning() + " \u00b0C\n"
                                + "\tTemperature Evening : " + dayWeather.getTempEvening() + " \u00b0C\n"
                                + "\tTemperature Night : " + dayWeather.getTempNight() + " \u00b0C\n"
                                + "\tWind speed : " + dayWeather.getWindSpeed() + " km/h\n"
                                + "\tHumidity : " + dayWeather.getHumidity() + " %"
                );
                info.setFont(new Font("Tahoma", 12));
                date.setFont(new Font("Tahoma", 12));
                if (darkModeOn) {
                    moreInfoPane.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-background-color: #061C4A");
                    info.setFill(Paint.valueOf("WHITE"));
                    date.setFill(Paint.valueOf("WHITE"));
                } else {
                    moreInfoPane.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-background-color: #EFD4B3");
                    info.setFill(Paint.valueOf("#222225"));
                    date.setFill(Paint.valueOf("#222225"));
                }
                info.setLineSpacing(5);
                moreInfoPane.getChildren().add(date);
                moreInfoPane.getChildren().add(info);
            });
            dayName.setOnMouseExited(exited -> {
                moreInfoPane.setVisible(false);
                moreInfoPane.getChildren().clear();
            });

            // add elements into the row
            dayWeatherGrid.add(dayName, 0, rowIndex);
            GridPane.setHalignment(dayName, HPos.LEFT);
            dayWeatherGrid.add(tempDay, 0, rowIndex);
            GridPane.setHalignment(tempDay, HPos.RIGHT);
            dayWeatherGrid.add(icon, 1, rowIndex);
            GridPane.setHalignment(icon, HPos.CENTER);
            rowIndex++;
        }
    }

    private boolean canHourlyPaneScrollLeft = false;
    private boolean canHourlyPaneScrollRight = true;

    /**
     * Called when the user clicks on the left arrow of the hourly weathers
     * scrolls the hourlyScrollPane to the left if possible and sets correct arrow image
     */
    public void onHourWeatherPaneLeftArrowClicked() {
        double value = scrollDistance();
        if (hourWeatherScrollPane.getHvalue() - value <= 0) {
            showCursorDefault();
            canHourlyPaneScrollLeft = false;
        }
        GUIAnimations.HorizontalScroll(hourWeatherScrollPane, -value);
        canHourlyPaneScrollRight = true;
        updateHourWeatherArrows();
    }

    /**
     * Called when the user clicks on the right arrow of the hourly weathers
     * scrolls the hourlyScrollPane to the right if possible and sets correct arrow image
     */
    public void onHourWeatherPaneRightArrowClicked() {
        double value = scrollDistance();
        if (hourWeatherScrollPane.getHvalue() + value >= 1) {
            showCursorDefault();
            canHourlyPaneScrollRight = false;
        }
        GUIAnimations.HorizontalScroll(hourWeatherScrollPane, value);
        canHourlyPaneScrollLeft = true;
        updateHourWeatherArrows();
    }

    /**
     * Sets the correct arrow image based on whether or not the hourly pane can be scrolled
     */
    public void updateHourWeatherArrows() {
        if (canHourlyPaneScrollLeft) {
            hourWeatherGridLeftArrow.setImage(new Image("app/appmeteo/images/larrowon.png"));
        } else {
            hourWeatherGridLeftArrow.setImage(new Image("app/appmeteo/images/larrowoff.png"));
        }
        if (canHourlyPaneScrollRight) {
            hourWeatherGridRightArrow.setImage(new Image("app/appmeteo/images/rarrowon.png"));
        } else {
            hourWeatherGridRightArrow.setImage(new Image("app/appmeteo/images/rarrowoff.png"));
        }
    }

    /**
     * Calculates the distance value to be scrolled based on an affine function
     *
     * @return distance value that needs to be scrolled
     */
    private double scrollDistance() {
        double a = (0.25 - 0.515) / (515 - 966);
        double b = -(a * 515) + 0.25;
        return a * hourWeatherScrollPane.getWidth() + b;
    }

    /**
     * Shows Hand Cursor if the hourlyScrollPane can be scrolled to the left
     */
    public void onHourWeatherPaneLeftArrowMouseEntered() {
        if (canHourlyPaneScrollLeft) {
            showCursorHand();
        }
    }

    /**
     * Shows Hand Cursor if the hourlyScrollPane can be scrolled to the righttttttttttttttttt
     */
    public void onHourWeatherPaneRightArrowMouseEntered() {
        if (canHourlyPaneScrollRight) {
            showCursorHand();
        }
    }


    public void showCursorHand() {
        background.setCursor(Cursor.HAND);
    }

    public void showCursorDefault() {
        background.setCursor(Cursor.DEFAULT);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////FAVOURITE RELATED////////////////////////////////////////////////
    public AnchorPane favouriteAnchorPane;
    public Polygon favouriteDragger;
    public BorderPane favouriteBorderPane;
    public ImageView favouriteIndicator;
    public GridPane favouriteGrid;
    public ImageView downFavouriteArrow;
    public ImageView upFavouriteArrow;

    public boolean favouritePanelIsDragged;
    public FavouriteList favouriteList;

    /**
     * update the favourite indicator (the star at the top-right of the weather pane). !Calls must be synced with the
     * city currently displayed!
     */
    public void updateFavouriteIndicator() {
        Favourite currentFav = new Favourite(currentCity);
        // is fav
        if (favouriteList.getList().contains(currentFav))
            favouriteIndicator.setImage(new Image("app/appmeteo/images/fav.png"));
            // not fav and darkmode
        else if (darkModeOn)
            favouriteIndicator.setImage(new Image("app/appmeteo/images/addFav.png"));
            // not fav and not darkmode
        else favouriteIndicator.setImage(new Image("app/appmeteo/images/addFav-black.png"));
    }

    /**
     * When the user click on the favourite indicator, create a favourite from current city and add or delete it from
     * the user's favourite list. !Calls must be synced with the city currently displayed!
     */
    public void updateFavouriteFromCurrentCityOnClick() {
        Favourite favourite = new Favourite(currentCity);
        if (favouriteList.getList().contains(favourite)) delFavourite(favourite);
        else addFavourite(favourite);
        updateFavouriteIndicator();
    }

    public void delFavourite(Favourite favourite) {
        // del corresponding row and heapify the grid
        int index = favouriteList.getList().indexOf(favourite);
        favouriteGrid.getChildren().remove(index * 3, index * 3 + 3);
        favouriteGrid.getRowConstraints().remove(index);
        for (Node child : favouriteGrid.getChildren()) {
            int childIndex = GridPane.getRowIndex(child);
            if (childIndex > index) GridPane.setRowIndex(child, childIndex - 1);
        }
        // del the favourite
        favouriteList.removeFavourite(index);
        try {
            favouriteList.write();
        } catch (IOException e) {
            System.out.println("Error : failed to write into your favourites. Your changes will not be changed\n"
                    + "Try to quit the app, delete favourites.txt and relaunch the app");
        }
    }

    public void addFavourite(Favourite favourite) {
        // add favourite into the list
        favouriteList.addFavourite(favourite);
        try {
            favouriteList.write();
        } catch (IOException e) {
            System.out.println("Error : failed to write into your favourites. Your changes will not be changed\n"
                    + "Try to quit the app, delete favourites.txt and relaunch the app");
        }
        // add corresponding row
        addRowToFavouriteGrid(currentCity);
    }

    /**
     * add a row into the favourite grid for the favourite related to the specified city.
     *
     * @param city - the city related to the new favourite.
     */
    public void addRowToFavouriteGrid(City city) {
        // get content row
        ArrayList<String> favouriteWeather = new ArrayList<>();
        favouriteWeather.add(city.getName().replace('_', ' ') + " " + city.getCountry());
        favouriteWeather.add(city.getWeatherNow().getTemp() + "\u00b0C");
        favouriteWeather.add(city.getWeatherNow().getIcon());
        // add it into the grid
        addRowToFavouriteGrid(city, favouriteWeather, favouriteGrid.getRowCount());
    }

    /**
     * Add a row into the favourite grid. Do not use this, call {@link #addRowToFavouriteGrid(City)} instead. Indeed it
     * does not insert the favourite at specified index. It will overwrite it.
     * Add an event listener so that when the user click on the city name, weather infos
     * are displayed.
     *
     * @param city         - the related city of the new favourite
     * @param favouriteRow - the content to add
     * @param rowIndex     - the row index of the new favourite
     */
    public void addRowToFavouriteGrid(City city, ArrayList<String> favouriteRow, int rowIndex) {
        // add row constraints
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPrefHeight(130);
        rowConstraints.setMinHeight(100);
        favouriteGrid.getRowConstraints().add(rowConstraints);
        // get content
        String cityName = favouriteRow.get(0).substring(0, 1).toUpperCase()
                + favouriteRow.get(0).substring(1); // first letter with uppercase
        String cityTemp = favouriteRow.get(1);
        String cityIcon = favouriteRow.get(2);
        // get Nodes from content
        Text cityNameText = new Text(cityName);
        // add listener
        cityNameText.setOnMouseEntered(event -> {
            cityNameText.setFill(Paint.valueOf("#B8B8B8"));
            showCursorHand();
        });
        cityNameText.setOnMouseExited(event -> {
            cityNameText.setFill(Paint.valueOf("WHITE"));
            showCursorDefault();
        });
        cityNameText.setOnMouseClicked(event -> {
            if (!currentCity.equals(city)) {
                currentCity = city;
                errorText.setText("");
                searchBar.setText("");
                updateWeather();
                updateBackground();
                updateFavouriteIndicator();
                GUIUtilities.saveAppState(currentCity, darkModeOn);
            }
            translateFavouritePanelOnClick(event);
        });
        Text cityTempText = new Text(cityTemp);
        ImageView cityIconImage = new ImageView(new Image(cityIcon));
        // set nodes properties
        cityNameText.setFont(new Font("Tahoma", 20));
        cityTempText.setFont(new Font("Tahoma", 20));
        cityNameText.setFill(Paint.valueOf("WHITE"));
        cityTempText.setFill(Paint.valueOf("WHITE"));
        cityNameText.setWrappingWidth(favouriteGrid.getPrefWidth() / favouriteGrid.getColumnCount());
        cityIconImage.setPreserveRatio(true);
        cityIconImage.setFitWidth(50);
        // add nodes into the grid
        addChildrenToFavouriteGrid(cityNameText, 0, rowIndex);
        addChildrenToFavouriteGrid(cityTempText, 1, rowIndex);
        addChildrenToFavouriteGrid(cityIconImage, 2, rowIndex);
    }

    public void addChildrenToFavouriteGrid(Node child, int column, int row) {
        favouriteGrid.add(child, column, row);
    }

    public void makeFavouriteArrowVisible() {
        upFavouriteArrow.setOpacity(1);
        downFavouriteArrow.setOpacity(1);
    }

    public void makeFavouriteArrowInvisible(MouseEvent e) {
        if (favouriteDragger.contains(e.getX(), e.getY())) return; // counter when mouse over favouriteDragger children
        upFavouriteArrow.setOpacity(0);
        downFavouriteArrow.setOpacity(0);
    }

    public void translateFavouritePanelOnClick(MouseEvent click) {
        if (GUIAnimations.isFavouritePanelAnimationOver() && !favouritePanelIsDragged) {
            GUIAnimations.showFavouritePanel();
            favouritePanelIsDragged = true;
            upFavouriteArrow.setRotate(180);
            downFavouriteArrow.setRotate(180);
            for (Node child : background.getChildren()) {
                if (child.getId() != null && child.getId().equals("favouriteAnchorPane")) continue;
                child.setEffect(new GaussianBlur());
            }
        } else if (GUIAnimations.isFavouritePanelAnimationOver() && favouritePanelIsDragged) {
            GUIAnimations.hideFavouritePanel();
            favouritePanelIsDragged = false;
            upFavouriteArrow.setRotate(0);
            downFavouriteArrow.setRotate(0);
            for (Node child : background.getChildren()) {
                if (child.getId() != null && child.getId().equals("favouriteAnchorPane")) continue;
                child.setEffect(new GaussianBlur(0));
            }
        }
        click.consume();
    }

}
