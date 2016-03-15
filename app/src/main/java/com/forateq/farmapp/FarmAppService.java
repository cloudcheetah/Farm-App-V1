package com.forateq.farmapp;

import com.forateq.farmapp.objects.AddProjectWrapper;
import com.forateq.farmapp.objects.EmployeesWrapper;
import com.forateq.farmapp.objects.LoginWrapper;
import com.forateq.farmapp.objects.ProjectWrapper;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;


/**
 * Created by Vallejos Family on 2/23/2016.
 * This interface is use to get access to different url of the api
 */
public interface FarmAppService {

    String URL = "http://128.199.140.96";
    String SERVER_TOKEN = "!JJJJcheetah8888";

    /**
     * use to login
     * @param user
     * @param password
     * @param deviceId
     * @param token
     * @param notification_id
     * @return active session
     */
    @GET("/api/apilogin/")
    LoginWrapper login(@Query("user") String user, @Query("password") String password, @Query("deviceid") String deviceId, @Query("token") String token, @Query("notif") String notification_id);

    /**
     * use to create a project
     * @param projectName
     * @param projectStart
     * @param projectEnd
     * @param budget
     * @param projectDescription
     * @param latitude
     * @param longitude
     * @param user
     * @param deviceid
     * @param key
     * @return response if successful or not
     */
    @FormUrlEncoded
    @POST("/api_projects/create")
    AddProjectWrapper createProject(@Field("project[name]") String projectName, @Field("project[start_date]") String projectStart, @Field("project[end_date]") String projectEnd, @Field("project[budget]") String budget, @Field("project[description]") String projectDescription, @Field("project[latitude]") String latitude, @Field("project[longitude]") String longitude, @Field("user") String user, @Field("deviceid") String deviceid, @Field("key") String key);

    /**
     * use to get all the projects
     * @param user
     * @param deviceid
     * @param key
     * @return list of projects
     */
    @GET("/api_projects")
    ProjectWrapper getAllProjects(@Query("user") String user, @Query("deviceid") String deviceid, @Query("key") String key);

    /**
     * use to get the details of a specific project
     * @param projectId
     * @param user
     * @param deviceid
     * @param key
     * @return project details
     */
    @GET("/api_projects/{id}")
    AddProjectWrapper getProjectById(@Path("id") int projectId, @Query("user") String user, @Query("deviceid") String deviceid, @Query("key") String key);

    /**
     * use to update a project
     * @param projectId
     * @param projectName
     * @param projectStart
     * @param projectEnd
     * @param budget
     * @param projectDescription
     * @param latitude
     * @param longitude
     * @param user
     * @param deviceid
     * @param key
     * @param method
     * @return response of the update is successful or not
     */
    @FormUrlEncoded
    @POST("/api_projects/{id}")
    AddProjectWrapper updateProject(@Path("id") int projectId, @Field("project[name]") String projectName, @Field("project[start_date]") String projectStart, @Field("project[end_date]") String projectEnd, @Field("project[budget]") double budget, @Field("project[description]") String projectDescription, @Field("project[latitude]") String latitude, @Field("project[longitude]") String longitude, @Field("user") String user, @Field("deviceid") String deviceid, @Field("key") String key, @Field("_method") String method);

    /**
     * use to delete a project
     * @param projectId
     * @param user
     * @param deviceid
     * @param key
     * @param method
     * @return response if delete is successful or not
     */
    @FormUrlEncoded
    @POST("/api_projects/{id}")
    AddProjectWrapper deleteProject(@Path("id") int projectId, @Field("user") String user, @Field("deviceid") String deviceid, @Field("key") String key, @Field("_method") String method);

    /**
     * use to create task
     * @param taskName
     * @param startDate
     * @param endDate
     * @param budget
     * @param description
     * @param latitude
     * @param longitude
     * @param duration
     * @param projectId
     * @param personResponsibleId
     * @param user
     * @param deviceId
     * @param key
     * @return response of creation of task is successful or not
     */
    @FormUrlEncoded
    @POST("/api_tasks/create")
    AddProjectWrapper createTask(@Field("task[name]") String taskName, @Field("task[start_date]") String startDate, @Field("task[end_date]") String endDate, @Field("task[budget]") double budget, @Field("task[description]") String description, @Field("task[latitude]") String latitude, @Field("task[longitude]") String longitude, @Field("task[duration]") int duration, @Field("task[project_id]") int projectId, @Field("task[peson_responsible_id]") int personResponsibleId, @Field("user") String user, @Field("deviceid") String deviceId, @Field("key") String key);

    /**
     * use to get all employees for person responsible
     * @param user
     * @param deviceid
     * @param key
     * @return returns all list of employees
     */
    @GET("/api_employees")
    EmployeesWrapper getAllEmployees(@Query("user") String user, @Query("deviceid") String deviceid, @Query("key") String key);

}
