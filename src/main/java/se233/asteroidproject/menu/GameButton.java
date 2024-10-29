package se233.asteroidproject.menu;

public class GameButton extends GeneralButton{

    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: blue;-fx-font-size:30;-fx-text-fill:white";
    private final String BUTTON_FREE_STYLE = "-fx-background-color: black;-fx-font-size:32;-fx-text-fill:white";

    public GameButton(String text) {
        super(text);
        setText(text);
        setPrefWidth(250);
        setPrefHeight(49);
        setStyle(BUTTON_FREE_STYLE);
        initializeButtonListeners();

    }

    public void setButtonPressedStyle() {
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(44);
        setPrefWidth(220);
    }

    public void setButtonReleasedStyle() {
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(49);
        setPrefWidth(250);
    }
}