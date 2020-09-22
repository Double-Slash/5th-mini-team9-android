package com.coronacircle.model.data;

import java.math.BigDecimal;

public class Location {
    private int id;
    private String name;            //상호명
    private String date_time;       //?
    private String city;            //시(서울시)
    private String district;        //구(노원구)
    private String road;            //로(동일로)
    private String detailed;        //자세한주소
    private BigDecimal latitude;    //위도
    private BigDecimal longitude;   //경도

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String time) { this.date_time = date_time; }

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

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getDetailed() {
        return detailed;
    }

    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }

    public BigDecimal getLatitude() { return latitude; }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}
