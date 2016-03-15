package com.forateq.farmapp.objects;

import java.util.List;

/**
 * Created by Vallejos Family on 3/2/2016.
 */
public class AddProjectWrapper {

    private Response response;
    private Data data;
    private List<TasksWrapper> tasks;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<TasksWrapper> getTasks() {
        return tasks;
    }

    public void setTasks(List<TasksWrapper> tasks) {
        this.tasks = tasks;
    }
}
