package com.example.taskfx.controllerviews;

import com.example.taskfx.controller.TaskController;

public class AdminViewController implements IControllerView{
    private TaskController taskController;


    @Override
    public void setTaskController(TaskController taskController) {
        this.taskController=taskController;
    }
}
