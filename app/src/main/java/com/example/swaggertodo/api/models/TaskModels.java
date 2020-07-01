package com.example.swaggertodo.api.models;

import com.google.gson.annotations.SerializedName;

public class TaskModels {
  @SerializedName("todo")
private String task;

    public TaskModels(String task) {
        this.task = task;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
