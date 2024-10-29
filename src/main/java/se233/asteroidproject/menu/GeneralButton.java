package se233.asteroidproject.menu;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class GeneralButton extends Button {

    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: blue;-fx-font-size:20;-fx-text-fill:white";
    private final String BUTTON_FREE_STYLE = "-fx-background-color: black;-fx-font-size:22;-fx-text-fill:white";


    public GeneralButton(String text) {
        setText(text);
        setPrefWidth(150);
        setPrefHeight(30);
        setStyle(BUTTON_FREE_STYLE);
        initializeButtonListeners();

    }

    public void setButtonPressedStyle() {
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(34);
        setPrefWidth(160);
    }

    public void setButtonReleasedStyle() {
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(30);
        setPrefWidth(150);
    }

    public void initializeButtonListeners() {

        setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonPressedStyle();
                }

            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonReleasedStyle();
                }

            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow());

            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setEffect(null);

            }
        });
    }
}
