package com.soleclipsado.soleclipsado.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

/**
 * Game home screen controller
 * Handles the initial screen where the secret word is entered to start the game
 * @author Juan Gomez & Camilo Pineda
 * @version 1.0
 */
public class StartController {

    /** Text field for entering the secret word*/
    @FXML
    private TextField secretWordField;

    /**
     * Initializes the controller automatically after FXML loading
     * Configures input validation allows only letters with a maximum length of 12 characters
     */
    @FXML
    private void initialize() {
        secretWordField.setTextFormatter(new TextFormatter<>(change -> {
            String text = change.getControlNewText();

            if (text.length() > 12) return null;
            if (!text.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]*")) return null;

            return change;
        }));
    }

    /**
     * Handles the start game button action
     * Validates that the secret word has at least 6 characters
     */
    @FXML
    private void startGame() {
        String palabra = secretWordField.getText();

        if (palabra.length() < 6) {
            secretWordField.setStyle(
                    "-fx-border-color: red; -fx-border-width: 2px;"
            );

            secretWordField.clear();
            secretWordField.setPromptText("Entre 6 y 12 letras");

            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/soleclipsado/soleclipsado/Game.fxml")
            );

            Scene scene = new Scene(loader.load());

            GameController controller = loader.getController();
            controller.setPalabraSecreta(palabra);

            Stage stage = (Stage) secretWordField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}