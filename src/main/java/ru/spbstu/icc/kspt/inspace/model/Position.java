package ru.spbstu.icc.kspt.inspace.model;


public class Position {
    private int systemNumber;
    private int planetNumber;

    public Position(int systemNumber, int planetNumber) {
        this.systemNumber = systemNumber;
        this.planetNumber = planetNumber;
    }

    public int getSystemNumber() {
        return systemNumber;
    }

    public int getPlanetNumber() {
        return planetNumber;
    }
}
