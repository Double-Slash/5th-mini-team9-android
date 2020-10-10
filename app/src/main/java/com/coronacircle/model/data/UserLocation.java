package com.coronacircle.model.data;

import java.math.BigDecimal;

public class UserLocation {

    private int id;
//    private String date_time;       //YYYY-MM-DD HH:MM:SS
    private String date;    //날짜 YYYY-MM-DD
    private String time;    //시간 HH:MM:SS
    private Double latitude;    //위도
    private Double longitude;   //경도

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public String getDate_time() { return date_time; }

//    public void setDate_time(String date_time) { this.date_time = date_time; }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public void setDateByDateTime(String datetime) {
        this.date = datetime.substring(0, 10);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }
    public void setTimeByDateTime(String datetime) {
        this.time = datetime.substring(11,17)+"00";
    }

    public void setTime(String time) {
        this.time = time;
    }

}
