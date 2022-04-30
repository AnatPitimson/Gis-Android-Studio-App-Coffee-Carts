package com.example.coffeetrail;

public class Carts {


    private String name;
    private String place;
    private String area;
    private String kosher_;
    private String accessible_;
    private String vegan_;
    private String activitytime;
    private String gluten_free_;
    private String phone;
    private String surl;
    private double lat;
    private double lon;
    private String id;

    public Carts(String name, String place, String area, String kosher_, String accessible_,
                 String vegan_, String gluten_free_, String activitytime,String surl, double lat, double lon, String phone,String id) {
        this.name=name;
        this.place =place;
        this.area=area;
        this.kosher_=kosher_;
        this.accessible_=accessible_;
        this.vegan_=vegan_;
        this.gluten_free_=gluten_free_;
        this.lat=lat;
        this.lon=lon;
        this.activitytime=activitytime;
        this.phone=phone;
        this.surl=surl;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getActivitytime() {
        return activitytime;
    }

    public void setActivitytime(String activitytime) {
        this.activitytime = activitytime;
    }

    public String getKosher_() {
        return kosher_;
    }

    public void setKosher_(String kosher_) {
        this.kosher_ = kosher_;
    }

    public String getAccessible_() {
        return accessible_;
    }

    public void setAccessible_(String accessible_) {
        this.accessible_ = accessible_;
    }

    public String getVegan_() {
        return vegan_;
    }

    public void setVegan_(String vegan_) {
        this.vegan_ = vegan_;
    }

    public String getGluten_free_() {
        return gluten_free_;
    }

    public void setGluten_free_(String gluten_free_) {
        this.gluten_free_ = gluten_free_;
    }


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Carts() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
