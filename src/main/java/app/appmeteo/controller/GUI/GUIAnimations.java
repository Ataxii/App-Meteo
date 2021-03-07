package app.appmeteo.controller.GUI;

import javafx.animation.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class GUIAnimations {
    private static TranslateTransition dragTranslation;

    public static void createFavouritePanelTranslation(BorderPane favouriteBorderPane) {
        dragTranslation = new TranslateTransition();
        dragTranslation.setDuration(Duration.millis(750));
        dragTranslation.setNode(favouriteBorderPane);
    }

    public static void showFavouritePanel() {
        dragTranslation.setByX(420);
        dragTranslation.play();
    }

    public static void hideFavouritePanel() {
        dragTranslation.setByX(-420);
        dragTranslation.play();
    }

    public static boolean isFavouritePanelAnimationOver() {
        return !dragTranslation.getStatus().equals(Animation.Status.RUNNING);
    }

    /**
     * Adds value passed in parameter to the ScrollPane's H value and smoothly scrolls the pane toward this value
     *
     * @param scrollPane the ScrollPane that will be animated
     * @param value      the value added to scrollPane's Hvalue
     */
    public static void HorizontalScroll(ScrollPane scrollPane, double value) {
        Animation scrollAnimation = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(scrollPane.hvalueProperty(), scrollPane.getHvalue() + value)));
        scrollAnimation.play();
    }

    public static void playSearchErrorAnimation(TextField searchBar, Text errorText) {
        searchBar.setStyle("-fx-text-box-border: #FF0000; -fx-focus-color: #FF0000; -fx-border-radius: 10px; -fx-background-radius: 50px;");
        errorText.setText("No results found");
        TranslateTransition errorAnimation = new TranslateTransition();
        errorAnimation.setNode(searchBar);
        errorAnimation.setDuration(Duration.millis(80));
        errorAnimation.setByX(-20);
        errorAnimation.play();

        errorAnimation.setOnFinished(actionEvent -> {
            TranslateTransition shakeAnimation = new TranslateTransition(Duration.millis(80));
            shakeAnimation.setNode(searchBar);
            shakeAnimation.setByX(40);
            shakeAnimation.play();

            shakeAnimation.setOnFinished(actionEvent1 -> {
                TranslateTransition shakeAnimation1 = new TranslateTransition(Duration.millis(80));
                shakeAnimation1.setNode(searchBar);
                shakeAnimation1.setByX(-20);
                shakeAnimation1.play();

                shakeAnimation1.setOnFinished(actionEvent2 -> {
                    searchBar.setStyle("-fx-text-box-border: #616161; -fx-focus-color: #616161; -fx-border-radius: 10px; -fx-background-radius: 50px;");
                });
            });
        });
    }
}
