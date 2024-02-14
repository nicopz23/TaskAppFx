package com.example.taskfx.controllerviews;

import com.example.taskfx.controller.TaskController;
import com.example.taskfx.models.ModeloBase;
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
    private ObservableList<Task> tasklist;

    TaskController taskController;
    TaskController funciones = new TaskController();
    @Override
    public void setTaskController(TaskController taskController) {
        this.taskController=taskController;
    }
    public UserController() {
        tasklist = FXCollections.observableArrayList();
    }

    public void btnBorrar() {
        Task task = table.getSelectionModel().getSelectedItem();
        task.setTitle(task.getTitle());
        funciones.deleteTask(txttitle.getText());
        Cancelar();
        table.refresh();
    }

    public void btneditTask() {
        Task task = table.getSelectionModel().getSelectedItem();
        task.setIdtask(task.getIdtask());
        task.setTitle(task.getTitle());
        task.setDescription(task.getDescription());
        task.setStatus(ckstatus.isSelected());
        task.setDeadline(dpDeadline.getValue());
        System.out.println(task.getIdtask());
        taskController.completeTask(task.getIdtask());
        taskController.newDescription(task.getIdtask(), txtDescription.getText());
        taskController.newTitle(task.getIdtask(),txttitle.getText());
        taskController.newDeadline(task.getIdtask(),dpDeadline.getValue());
        table.refresh();
        Cancelar();
    }

    public void agregarAtabla() {
        taskController.newTask(txttitle.getText(),txtDescription.getText(),dpDeadline.getValue());
        txttitle.clear();
        txtDescription.clear();
        dpDeadline.setValue(null);
    }

    @FXML
    public void traerdatos() {
        Task task = new Task();
        tasklist = FXCollections.observableArrayList(
           task.getallTasks());
        table.setItems(tasklist);
    }

    public void showNew() {
        Cancelar();
    }

    public void close() {
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

    public void Cancelar() {
        bttnNew.setVisible(true);
        btnedit.setVisible(false);
        btnDelete.setVisible(false);
        txttitle.setDisable(false);
        txttitle.setText("");
        txtDescription.setText("");
        dpDeadline.setValue(null);
    }
}
