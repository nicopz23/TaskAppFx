package com.example.taskfx.controllerviews;

import com.example.taskfx.controller.TaskController;
import com.example.taskfx.models.Admin;
import com.example.taskfx.models.Rol;
import com.example.taskfx.models.Task;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;

public class AdminViewController extends ControllerView {

    public Menu mnFile;
    public TableView<Admin> table;
    public TableColumn<Admin, Integer> iduser;
    public TableColumn<Admin, String> username;
    public TableColumn<Admin, String> rol;
    public TextField txtUsername;
    public TextField txtPassword;
    public Button bttnNew;
    public Button btnedit;
    public Button btnDelete;
    public TextField txtRolInt;
    public ObservableList<Admin> adminlist;

    @FXML
    public void traerdatos() {
        Admin admin = new Admin();
        adminlist = FXCollections.observableArrayList(
                taskController.getAllUser());
        table.setItems(adminlist);
    }

    @FXML
    public void showNew() {
        Cancelar();
    }

    @FXML
    public void agregarAtabla() {
        taskController.newUser(txtUsername.getText(),txtPassword.getText(),Integer.parseInt(txtRolInt.getText()));
        txtUsername.clear();
        txtPassword.clear();
        txtRolInt.clear();
    }

    @FXML
    public void btneditTask() {
        Admin admin = table.getSelectionModel().getSelectedItem();
        admin.setIduser(admin.getIduser());
        admin.setUsername(admin.getUsername());
        admin.setPassword(admin.getPassword());
        admin.setIdrol(admin.getIdrol());
        taskController.newPassword(admin.getUsername(), txtPassword.getText());
        table.refresh();
        Cancelar();
    }

    @FXML
    public void btnBorrar() {
        Admin admin = table.getSelectionModel().getSelectedItem();
        adminlist.remove(admin);
        taskController.deleteUser(txtUsername.getText(),txtPassword.getText());
        Cancelar();
        table.refresh();
        btnDelete.setVisible(false);
    }

    @FXML
    public void initialize() {
        iduser.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIduser()));
        username.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUsername()));
        rol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdrol().getName()));
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 1) {
                    Admin admin = table.getSelectionModel().getSelectedItem();
                    txtUsername.setText(admin.getUsername());
                    txtPassword.setText(admin.getPassword());
                    txtRolInt.setText(admin.getIdrol().getName());
                    btnedit.setVisible(true);
                    bttnNew.setVisible(false);
                    btnDelete.setVisible(true);
                    txtUsername.setDisable(true);
                    txtRolInt.setDisable(true);
                }
            }
        });
    }
    public void Cancelar() {
        bttnNew.setVisible(true);
        btnedit.setVisible(false);
        btnDelete.setVisible(false);
        txtUsername.setDisable(false);
        txtRolInt.setDisable(false);
        txtUsername.setText("");
        txtPassword.setText("");
        txtRolInt.setText("");
    }

    @Override
    public void cargaInicial() {
        adminlist = FXCollections.observableArrayList(
                taskController.getAllUser());
        table.setItems(adminlist);
    }
}
