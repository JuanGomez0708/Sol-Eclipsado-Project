package com.soleclipsado.soleclipsado;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
* Main class that launches the Sol Eclipsado application
* Responsible for loading the game's initial interface
* @author Juan Gomez & Camilo Pineda
* @version 1.0
 */
public class MainApp extends Application {
    /**
     * Method that launch the graphic interface
     * @param stage principal window of the app
     * @throws Exception if an error occurs while loading the FXML file
     */

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                MainApp.class.getResource("Start.fxml")
        );
        Scene scene = new Scene(loader.load(),600, 400);

        stage.setTitle("Sol Eclipsado");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Principal method that launch the app
     * @param args execution arguments
     */
    public static void main(String[] args){
        launch();
    }
}