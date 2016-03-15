package com.forateq.farmapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.util.SQLiteUtils;

import java.util.List;

/**
 * Created by Vallejos Family on 3/2/2016.
 */
@Table(name = "Projects")
public class ProjectsModel extends Model {

    @Column(name = "project_id")
    private int projectId;
    @Column(name = "project_name")
    private String projectName;

    /**
     * use to get the project_id
     * @return project_id
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * sets the project_id
     * @param projectId
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    /**
     * use to get the project name
     * @return project_name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * sets the project_name
     * @param projectName
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * use to get all the list of Projects from sqlite
     * @return list of projects
     */
    public static List<ProjectsModel> getProjectsModel(){
        return new Select().from(ProjectsModel.class).execute();
    }

    /**
     * use to search projects in sqlite
     * @param name
     * @return search list of projects
     */
    public static List<ProjectsModel> getSearchProject(String name){
        String [] selectionArgs = new String[] {"%" + name + "%"};
        List<ProjectsModel> searchItems =
                SQLiteUtils.rawQuery(ProjectsModel.class,
                        "SELECT * FROM Projects WHERE project_name  LIKE ?",
                        selectionArgs);
        return searchItems;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProjectsModel)) {
            return false;
        }
        ProjectsModel other = (ProjectsModel) o;
        return projectName.equals(other.projectName) && projectId == other.projectId;
    }

    @Override
    public int hashCode() {
        return projectId / 11;
    }
}
