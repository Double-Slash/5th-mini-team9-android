package com.coronacircle.model.data;

public class Notification {


    private int id;
    private String whereName;
    private String date;    //날짜 YYYY-MM-DD
    private String time;    //시간 HH:MM:SS
    private String message;

    public Notification(){}

    public Notification(String date, String time, String message) {
        this.date = date;
        this.time = time;
        this.message = message;
    }

    public Notification(String whereName, String date, String time, String message) {
        this.whereName = whereName;
        this.date = date;
        this.time = time;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWhereName() {
        return whereName;
    }

    public void setWhereName(String whereName) {
        this.whereName = whereName;
    }
}
