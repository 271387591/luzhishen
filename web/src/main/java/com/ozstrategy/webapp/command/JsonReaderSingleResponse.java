package com.ozstrategy.webapp.command;

public class JsonReaderSingleResponse<T> {
    public T data;

    public String message="";

    public Boolean success=true;

    public JsonReaderSingleResponse() {
        this.data = null;
        success = false;
        message="";
    }


    public JsonReaderSingleResponse(T data) {
        this.data = data;
        success = true;
    }

    public JsonReaderSingleResponse(T data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public T getData() {
        return data;
    }


    public String getMessage() {
        return message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonReaderSingleResponse(T data, boolean success) {
        this.data = data;
        this.success = success;
    }
} 
