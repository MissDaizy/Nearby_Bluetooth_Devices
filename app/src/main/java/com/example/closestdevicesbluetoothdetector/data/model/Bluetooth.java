package com.example.closestdevicesbluetoothdetector.data.model;

public class Bluetooth {
    private  String name;
    private  String distance;

    public String getName() {
        return name;
    }

    public Bluetooth setName(String name) {
        this.name = name;
        return this;
    }

    public String getDistance() {
        return distance;
    }

    public Bluetooth setDistance(String distance) {
        this.distance = distance;
        return this;
    }
}
