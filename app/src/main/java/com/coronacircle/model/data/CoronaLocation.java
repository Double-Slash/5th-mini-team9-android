package com.coronacircle.model.data;

public class CoronaLocation {
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

    private String allDateTime="null";

    public CoronaLocation() { }

    //복사생성자(무시)
    public CoronaLocation(String name, String date, String arrivedTime, String exitTime, String city, String district, String detailed, String latitude, String longitude) {
        this.name = name;
        this.date = date;
        this.arrivedTime = arrivedTime;
        this.exitTime = exitTime;
        this.city = city;
        this.district = district;
        this.detailed = detailed;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public CoronaLocation(CoronaLocation coronaLocation) {
        this.name = coronaLocation.name;
        this.date = coronaLocation.date;
        this.arrivedTime = coronaLocation.arrivedTime;
        this.exitTime = coronaLocation.exitTime;
        this.city = coronaLocation.city;
        this.district = coronaLocation.district;
        this.detailed = coronaLocation.detailed;
        this.latitude = coronaLocation.latitude;
        this.longitude = coronaLocation.longitude;
    }

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
        if (exitTime.equals("null")){
            //방문시간이 밤23시 55분 이후일 경우, 퇴장시간이 다음날로 넘어가기 때문에 임의로 당일 23:59:00로 표시
            if(arrivedTime.substring(0,2).equals("23") && Integer.parseInt(arrivedTime.substring(3,5))>54){
                return "23:59:00";
            }
            int exitTimePlus = Integer.parseInt(arrivedTime.substring(3,5))+5;
            //60분이 넘어가면 시+1 되기때문에 시도 변경되어야함. ex)도착시간 : 14:57:00 -> 퇴장시간 : 15:03:00
            if(exitTimePlus > 60) {
                exitTimePlus = exitTimePlus-60;
                return (Integer.parseInt(arrivedTime.substring(0,2))+1)+":"+ (exitTimePlus < 10? "0"+exitTimePlus : exitTimePlus) + ":00";
            }
            exitTime = arrivedTime.substring(0,3) + (exitTimePlus < 10? "0"+exitTimePlus : exitTimePlus) + ":00";
            return exitTime;
        }
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

    public String getAllDateTime() {
        if(allDateTime.equals("null")) setAllDateTime(getDate() + " " + getArrivedTime() + " - " + this.getExitTime());
        return allDateTime;
    }

    public void setAllDateTime(String allDateTime) {
        this.allDateTime = allDateTime;
    }

    public void setAllDateTimeAdd(String sameDateTime) {
        if(allDateTime.equals("null")) setAllDateTime(getDate() + " " + getArrivedTime() + " - " + this.getExitTime());
        this.allDateTime = allDateTime + "\n"+ sameDateTime;
    }
}
