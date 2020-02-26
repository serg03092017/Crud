package com.app.model;

public class Mistakes {

    private String mistake;
    private int number;

    public Mistakes() {

    }

    public Mistakes(String mistake, int number) {
        this.mistake = mistake;
        this.number = number;
    }

    public String getMistake() {
        return mistake;
    }

    public void setMistake(String mistake) {
        this.mistake = mistake;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
