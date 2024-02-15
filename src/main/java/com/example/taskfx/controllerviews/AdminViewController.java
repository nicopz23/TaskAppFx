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

import java.util.List;

public class AdminViewController extends ControllerView {

    public Menu mnFile;
    public TableView<Admin> table;
    public TableColumn<Admin, Integer> iduser;
    public TableColumn<Admin, String> username;
    public TableColumn<Admin, String> rol;
    public TextField txtUsername;
    public PasswordField txtPassword;
    public Button bttnNew;
    public Button btnedit;
    public Button btnDelete;
    public ObservableList<Admin> adminlist;
    public PasswordField txtPassword1;
    public Label lblmsg;
    public ComboBox<Rol> cmbRol;

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
        if (txtPassword.getText().equals(txtPassword1)) {
            taskController.newUser(txtUsername.getText(), txtPassword.getText(), cmbRol.getValue().getIdrol());
            txtUsername.clear();
            txtPassword.clear();
        }else {
            lblmsg.setText("Password is not the same");
        }
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
                    btnedit.setVisible(true);
                    bttnNew.setVisible(false);
                    btnDelete.setVisible(true);
                    txtUsername.setDisable(true);
                    cmbRol.setDisable(true);
                }
            }
        });
    }
    public void Cancelar() {
        bttnNew.setVisible(true);
        btnedit.setVisible(false);
        btnDelete.setVisible(false);
        txtUsername.setDisable(false);
        cmbRol.setDisable(false);
        txtUsername.setText("");
        txtPassword.setText("");
        txtPassword1.setText("");
    }

    @Override
    public void cargaInicial() {
        adminlist = FXCollections.observableArrayList(
                taskController.getAllUser());
        table.setItems(adminlist);
        List<Rol> rolList = taskController.getAllRol();
        cmbRol.getItems().addAll(rolList);
    }
}
