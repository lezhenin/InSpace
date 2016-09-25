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

    @Override
    public String toString() {
        return "Position(" +
                 + systemNumber +
                ", " + planetNumber +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (systemNumber != position.systemNumber) return false;
        return planetNumber == position.planetNumber;

    }

    @Override
    public int hashCode() {
        int result = systemNumber;
        result = 31 * result + planetNumber;
        return result;
    }
}
