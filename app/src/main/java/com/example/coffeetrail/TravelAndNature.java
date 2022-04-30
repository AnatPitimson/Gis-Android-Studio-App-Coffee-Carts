package com.example.coffeetrail;

public class TravelAndNature {

    private double lat;
    private double lon;
    private String name;

    public TravelAndNature() {
    }

    public TravelAndNature(String name, double lat, double lon) {
        this.lat=lat;
        this.lon=lon;
        this.name=name;
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
