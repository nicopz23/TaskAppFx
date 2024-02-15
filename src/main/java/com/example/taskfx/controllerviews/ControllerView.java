package com.example.taskfx.controllerviews;

import com.example.taskfx.controller.TaskController;

public abstract class ControllerView {
    TaskController taskController;

    public void setTaskController(TaskController taskController) {
        this.taskController=taskController;
        cargaInicial();
    }
    public abstract void cargaInicial();

}
