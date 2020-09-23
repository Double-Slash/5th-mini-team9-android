package com.coronacircle.model.data;

import java.math.BigDecimal;

public class Location {
    private int id;
    private String name;            //상호명
    private String date;            //?
    private String arrivedTime;     //방문시간
    private String exitTime;
    private String city;            //시(서울시)
    private String district;        //구(노원구)
    private String detailed;        //로(동일로)~자세한주소
    private String latitude;    //위도
    private String longitude;   //경도

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

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

    public String getArrivedTime() {
        return arrivedTime;
    }

    public void setArrivedTime(String arrivedTime) {
        this.arrivedTime = arrivedTime;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetailed() {
        return detailed;
    }

    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
