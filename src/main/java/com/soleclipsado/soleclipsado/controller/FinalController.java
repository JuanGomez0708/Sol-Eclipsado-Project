package com.soleclipsado.soleclipsado.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Final screen controller
 * Displays the game result (win or loss) and provides options to restart or exit
 * @author Juan Gomez & Camilo Pineda
 * @version 1.0
 */
public class FinalController {

    /** ImageView displaying the win or game over image. */
    @FXML
    private ImageView imagenResultado;

    /**
     * Sets the game result image based on win/loss status
     * @param gano true if player won the game, false if player lost
     */
    public void setResultado(boolean gano) {
        String ruta;

        if (gano) {
            ruta = "/Images/Win.png";
        } else {
            ruta = "/Images/GameOver.png";
        }

        imagenResultado.setImage(new Image(getClass().getResourceAsStream(ruta)));
    }

    /**
     * Handles the restart button action
     * Transitions back to the start screen to begin a new game
     */
    @FXML
    private void reiniciar() {
        cambiarEscena("/com/soleclipsado/soleclipsado/Start.fxml");
    }

    /**
     Handles the exit button action.
     */
    @FXML
    private void salir() {
        System.exit(0);
    }

    /**
     * Utility method to change the current scene to a different FXML view
     * Loads the specified FXML file and sets it as the new scene on the current stage
     * @param rutaFXML The resource path to the FXML file to load
     */
    private void cambiarEscena(String rutaFXML) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource(rutaFXML));
            javafx.scene.Scene scene = new javafx.scene.Scene(loader.load());

            javafx.stage.Stage stage = (javafx.stage.Stage) imagenResultado.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}