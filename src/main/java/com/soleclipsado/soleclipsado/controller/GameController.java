package com.soleclipsado.soleclipsado.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

/**
 * Game Controller
 * Handles the visual logic of Eclipse
 * @author Juan Gomez & Camilo Pineda
 * @version 1.0
 */
public class GameController {
    private String palabraSecreta;

    @FXML private HBox contenedorLetras;

    @FXML private Label labelErrores;

    @FXML private Label labelAyudas;

    @FXML private ImageView imageSol;

    private int errores = 0;

    private int ayudas = 0;
    /**
     * Initializes the game controller after FXML loading
     * Sets initial error count to 0/5, hints to 3, and loads the initial sun image
     */
    @FXML
    private void initialize() {
        labelErrores.setText("Errores: 0/5");
        labelAyudas.setText("3");
        actualizarImagen();
    }

    /**
     * Sets the secret word for the current game session
     * @param palabra The secret word to be guessed
     */
    public void setPalabraSecreta(String palabra) {
        palabraSecreta = normalizar(palabra);
        generarCasillas();
    }

    /**
     * Generates text input fields for each letter of the secret word
     * Each field accepts only one letter
     * Fields are configured with validation and ENTER key handling
     */
    private void generarCasillas() {
        contenedorLetras.getChildren().clear();

        for (int i = 0; i < palabraSecreta.length(); i++) {
            TextField c = new TextField();
            c.setPrefSize(40, 40);

            c.setTextFormatter(new TextFormatter<>(ch -> {
                String t = ch.getControlNewText();
                return (t.length() <= 1 && t.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]*")) ? ch : null;
            }));

            int pos = i;

            c.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) verificarLetra(pos);
            });

            contenedorLetras.getChildren().add(c);
        }
    }

    /**
     * Verifies the letter entered at the specified position
     * Compares user input against the secret word, updates field styling
     * increments error count if incorrect, and checks for game end conditions
     * @param i The index position of the letter to verify
     */
    private void verificarLetra(int i) {
        if (errores >= 5) return;

        TextField c = (TextField) contenedorLetras.getChildren().get(i);
        if (c.getText().isEmpty()) return;

        char user = quitarTilde(Character.toUpperCase(c.getText().charAt(0)));
        char real = palabraSecreta.charAt(i);

        if (user == real) {
            c.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            c.setEditable(false);
        } else {
            c.clear();
            errores++;
            labelErrores.setText("Errores: " + errores + "/5");
            actualizarImagen();
        }

        verificarFin();
    }

    /**
     * Updates the sun image based on current error count.
     * Images are loaded from /Images/Sun{errorCount}.png (Sun0.png to Sun5.png).
     * Prints error message to console if image resource is not found.
     */
    private void actualizarImagen() {
        String ruta = "/Images/Sun" + errores + ".png";
        var r = getClass().getResourceAsStream(ruta);

        if (r != null) imageSol.setImage(new Image(r));
        else System.out.println("No se encontró: " + ruta);
    }

    /**
     * Limited to 3 hints per game. Automatically fills the first unrevealed letter
     * marks it as correct (green), and decrements available hints
     * Does nothing if max hints used or game already over
     */
    @FXML
    private void mostrarAyuda() {
        if (ayudas >= 3 || errores >= 5) return;

        for (int i = 0; i < palabraSecreta.length(); i++) {
            TextField c = (TextField) contenedorLetras.getChildren().get(i);

            if (!c.getStyle().contains("green")) {
                c.setText(String.valueOf(Character.toLowerCase(palabraSecreta.charAt(i))));
                c.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                c.setEditable(false);

                ayudas++;
                labelAyudas.setText(String.valueOf(3 - ayudas));

                verificarFin();
                break;
            }
        }
    }

    /**
     * Checks if the game has ended
     * Game is lost when errors reach 5. Game is won when all letters are revealed
     * Triggers end screen transition when game ends
     */
    private void verificarFin() {
        if (errores >= 5) {
            bloquear();
            cambiarPantallaFinal(false);
            return;
        }

        for (int i = 0; i < palabraSecreta.length(); i++) {
            TextField c = (TextField) contenedorLetras.getChildren().get(i);
            if (!c.getStyle().contains("green")) return;
        }

        bloquear();
        cambiarPantallaFinal(true);
    }

    /**
     * Disables all letter input fields to prevent further input.
     * Called when the game ends (win or loss).
     */
    private void bloquear() {
        contenedorLetras.getChildren().forEach(n -> ((TextField) n).setEditable(false));
    }

    /**
     * Normalizes a string by converting to uppercase and removing accents.
     *
     * @param p The string to normalize
     * @return The normalized string (uppercase, no accents)
     */
    private String normalizar(String p) {
        String r = "";
        for (char c : p.toUpperCase().toCharArray()) r += quitarTilde(c);
        return r;
    }

    /**
     * Removes accent
     * Converts accented characters to their unaccented equivalents.
     * @param c The character to process
     * @return The unaccented character, or the original if not an accented vowel
     */
    private char quitarTilde(char c) {
        return switch (c) {
            case 'Á' -> 'A';
            case 'É' -> 'E';
            case 'Í' -> 'I';
            case 'Ó' -> 'O';
            case 'Ú' -> 'U';
            default -> c;
        };
    }

    /**
     * Transitions to the final screen showing game result.
     * Loads FinalScreen.fxml and passes win/loss status to FinalController.
     *
     * @param gano true if player won, false if player lost
     */
    private void cambiarPantallaFinal(boolean gano) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/com/soleclipsado/soleclipsado/FinalScreen.fxml")
            );

            javafx.scene.Scene scene = new javafx.scene.Scene(loader.load());

            FinalController controller = loader.getController();
            controller.setResultado(gano);

            javafx.stage.Stage stage = (javafx.stage.Stage) contenedorLetras.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}