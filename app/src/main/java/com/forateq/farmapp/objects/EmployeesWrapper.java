package com.forateq.farmapp.objects;

import java.util.List;

/**
 * Created by Vallejos Family on 3/7/2016.
 */
public class EmployeesWrapper {

    private Response response;
    private List<Employee> data;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public List<Employee> getData() {
        return data;
    }

    public void setData(List<Employee> data) {
        this.data = data;
    }
}
