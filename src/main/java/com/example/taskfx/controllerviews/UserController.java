package com.example.taskfx.controllerviews;

import com.example.taskfx.controller.TaskController;
import com.example.taskfx.models.Task;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.LocalTime;

public class UserController implements IControllerView{
    public Menu mnFile;
    public MenuItem mnClose;
    public TableView<Task> table;
    public TableColumn<Task, LocalDate> Date;
    public TableColumn<Task,String> title;
    public TableColumn<Task,LocalDate> deadline;
    public TableColumn<Task, Boolean> status;
    public TextField txttitle;
    public TextField txtDescription;
    public Button bttnNew;
    public DatePicker dpDeadline;
    public CheckBox ckstatus;
    public Button btnedit;
    public Button btnDelete;
    private ObservableList<Task> task;

    TaskController taskController;

    @Override
    public void setTaskController(TaskController taskController) {
        this.taskController=taskController;
    }
    public UserController() {
        task = FXCollections.observableArrayList();
    }

    public void btnBorrar(ActionEvent actionEvent) {
        taskController.deleteTask(txttitle.getText());
        table.refresh();
    }

    public void btneditTask(ActionEvent actionEvent) {
    }

    public void agregarAtabla(ActionEvent actionEvent) {
    }

    @FXML
    public void traerdatos(ActionEvent actionEvent) {
        task = FXCollections.observableArrayList(
                new Task (LocalDate.now(), "Sistema solar", "Dibuja el sistema solar", LocalDate.of(2024, 02, 16), false),
                new Task(LocalDate.now(), "Programacion", "Crea un programa de sumas", LocalDate.of(2024, 02, 29), false),
                new Task(LocalDate.now(), "Crea una tabla", "Crea unas tablas para guardar datos", LocalDate.of(2024, 03, 15), false)
        );
        table.setItems(task);
    }

    public void showNew(MouseEvent mouseEvent) {
    }

    public void close(ActionEvent actionEvent) {
    }
    @FXML
    public void initialize() {
        Date.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().datetime));
        title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().title));
        deadline.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().deadline));
        status.setCellFactory(cell -> new CheckBoxTableCell<>());
        status.setCellValueFactory(e ->{
            Task task1 = e.getValue();
            SimpleBooleanProperty simpleBooleanProperty=new SimpleBooleanProperty(task1.getStatus());
            simpleBooleanProperty.addListener((event,old,newvalue)->{
            });
            return simpleBooleanProperty;
        });
        status.setEditable(true);
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 1) {
                    Task task1 = table.getSelectionModel().getSelectedItem();
                    txttitle.setText(task1.getTitle());
                    txtDescription.setText(task1.getDescription());
                    dpDeadline.setValue(task1.getDeadline());
                    ckstatus.setSelected(task1.getStatus());
                    btnedit.setVisible(true);
                    bttnNew.setVisible(false);
                    btnDelete.setVisible(true);
                }
            }
        });
    }
}
