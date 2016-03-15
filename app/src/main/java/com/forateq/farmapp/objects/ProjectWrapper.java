package com.forateq.farmapp.objects;

import java.util.List;

/**
 * Created by Vallejos Family on 3/1/2016.
 */
public class ProjectWrapper {
    private Response response;
    private List<Data> data;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
