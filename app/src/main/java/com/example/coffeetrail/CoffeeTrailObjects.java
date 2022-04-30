package com.example.coffeetrail;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

public class
CoffeeTrailObjects {

        String name;
        String city;
        String street;
        String phone;
        String idNumber;
        String streetNumber;
        String prices;
        String openingHours;
        String location;
        String routes;
        String naturereserve;
        int index;

        public String getnaturereserve() {
            return naturereserve;
        }
        public String getName() {
        return name;
    }
        public String getCity() {
            return city;
        }
        public String getStreet() {
            return street;
        }
        public String getPhone() {
            return phone;
        }
        public String getIdNumber() {
            return idNumber;
        }
        public String getStreetNumber() {
            return streetNumber;
        }
        public String getPrices() {
            return prices;
        }
        public String getOpeningHours() {
            return openingHours;
        }
        public String getlocation() {
            return location;
        }
        public String getroutes() {
            return routes;
        }

        public int getIndex() {
        return index;
    }

        public void setnaturereserve(String naturereserve) {
            this.naturereserve = naturereserve;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setCity(String city) {
            this.city = city;
        }
        public void setStreet(String street) {
            this.street = street;
        }
        public void setPhone(String phone) {
            this.phone = phone;
        }
        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }
        public void setStreetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
        }
        public void setPrices(String prices) {
            this.prices = prices;
        }
        public void setOpeningHours(String openingHours) {
            this.openingHours = openingHours;
        }
        public void setlocation(String location) {
            this.location = location;
        }
        public void setroutes(String routes) {
            this.routes = routes;
        }

    }

