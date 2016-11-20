package ru.spbstu.icc.kspt.inspace.model;


public class Position {
    private int numberOfSystem;
    private int numberOfPlanet;

    public Position(int numberOfSystem, int numberOfPlanet) {
        this.numberOfSystem = numberOfSystem;
        this.numberOfPlanet = numberOfPlanet;
    }


    public int getNumberOfSystem() {
        return numberOfSystem;
    }

    public int getNumberOfPlanet() {
        return numberOfPlanet;
    }

    public int getDistanceTo(Position p) {
        int distance = 0;
        distance += Math.abs(numberOfPlanet - p.numberOfPlanet) * 100;
        distance += Math.abs(numberOfSystem - p.numberOfSystem) * 1000;
        return distance;
    }

    @Override
    public String toString() {
        return "Position(" +
                 +numberOfSystem +
                ", " + numberOfPlanet +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        return numberOfSystem == position.numberOfSystem && numberOfPlanet == position.numberOfPlanet;

    }

    @Override
    public int hashCode() {
        int result = numberOfSystem;
        result = 31 * result + numberOfPlanet;
        return result;
    }
}
