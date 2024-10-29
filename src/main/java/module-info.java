module se233.asteroidproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens se233.asteroidproject.main to javafx.fxml;
    exports se233.asteroidproject.main;
    exports se233.asteroidproject.controllers;
    opens se233.asteroidproject.controllers to javafx.fxml;
    opens se233.asteroidproject.characters.builds to javafx.fxml;
    exports se233.asteroidproject.characters.builds;
    exports se233.asteroidproject.characters;
    exports se233.asteroidproject.config;
    exports se233.asteroidproject.menu;
    exports se233.asteroidproject.interfaces;
}