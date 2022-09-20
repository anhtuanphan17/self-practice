package com.traningsprint1.models;

import java.util.List;
import java.util.Map;

public class ResponseObject <T>{
    private boolean status;
    private String message;
    private Map<String, String> errorMap;
    private List<T> data;


    public ResponseObject() {
    }

    public ResponseObject(boolean status, String message, Map<String, String> errorMap, List<T> data) {
        this.status = status;
        this.message = message;
        this.errorMap = errorMap;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }

    public void setErrorMap(Map<String, String> errorMap) {
        this.errorMap = errorMap;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
