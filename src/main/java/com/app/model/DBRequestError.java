package com.app.model;

public class DBRequestError {

    String error;

    public DBRequestError() {

    }

    public DBRequestError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
