package ru.spbstu.icc.kspt.inspace.model;

public class Planet {

    private int size;

    private Resources resources;

    public Planet() {
        this.resources = new Resources(0,0,0);
    }

    public Planet(int size) {
        this.size = size;
        this.resources = new Resources(0,0,0);
    }

    public void update() {

    }
}
