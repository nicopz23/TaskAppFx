module com.example.taskfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.taskfx to javafx.fxml;
    exports com.example.taskfx;
    exports com.example.taskfx.controllerviews;
    opens com.example.taskfx.controllerviews to javafx.fxml;
}