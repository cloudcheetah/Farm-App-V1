package com.forateq.farmapp.objects;

/**
 * Created by Vallejos Family on 3/1/2016.
 */
public class Response {

    private String id;
    private String status_code;
    private String response_text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getResponse_text() {
        return response_text;
    }

    public void setResponse_text(String response_text) {
        this.response_text = response_text;
    }
}
