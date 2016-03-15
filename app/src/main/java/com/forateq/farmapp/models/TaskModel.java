package com.forateq.farmapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Vallejos Family on 3/7/2016.
 */
@Table(name = "Tasks")
public class TaskModel extends Model {

    @Column(name = "project_id")
    private int project_id;

    @Column(name = "task_name")
    private String task_name;

    @Column(name = "task_id")
    private int task_id;

    /**
     * use to get the task name
     * @return task_name
     */
    public String getTask_name() {
        return task_name;
    }

    /**
     * use to set the task name
     * @param task_name
     */
    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    /**
     * use to get the task_id
     * @return task_id
     */
    public int getTask_id() {
        return task_id;
    }

    /**
     * use to set the task id
     * @param task_id
     */
    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    /**
     * use to get the project_id
     * @return project_id
     */
    public int getProject_id() {
        return project_id;
    }

    /**
     * use to set the project_id
     * @param project_id
     */
    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    /**
     * use to get the list of task from sqlite
     * @return list of tasks
     */
    public static List<TaskModel> getTasks(){
        return new Select().from(TaskModel.class).execute();
    }
}
