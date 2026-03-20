module com.soleclipsado.soleclipsado {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.soleclipsado.soleclipsado to javafx.fxml;
    exports com.soleclipsado.soleclipsado;
    exports com.soleclipsado.soleclipsado.controller;
    opens com.soleclipsado.soleclipsado.controller to javafx.fxml;
}