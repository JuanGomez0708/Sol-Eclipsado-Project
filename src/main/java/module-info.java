module com.soleclipsado.soleclipsado {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.soleclipsado.soleclipsado to javafx.fxml;
    exports com.soleclipsado.soleclipsado;
}