package com.coronacircle.model.data;

import java.math.BigDecimal;

public class UserLocation {

    private int id;
//    private String date_time;       //YYYY-MM-DD HH:MM:SS
    private String date;    //날짜 YYYY-MM-DD
    private String time;    //시간 HH:MM:SS
    private BigDecimal latitude;    //위도
    private BigDecimal longitude;   //경도

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public String getDate_time() { return date_time; }

//    public void setDate_time(String date_time) { this.date_time = date_time; }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
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
}
