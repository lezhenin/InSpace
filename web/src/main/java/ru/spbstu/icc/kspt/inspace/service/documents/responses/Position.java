package ru.spbstu.icc.kspt.inspace.service.documents.responses;


public class Position {
    private final int numberOfSystem;
    private final int numberOfPlanet;

    public Position(int numberOfSystem, int numberOfPlanet) {
        this.numberOfSystem = numberOfSystem;
        this.numberOfPlanet = numberOfPlanet;
    }

    public Position(ru.spbstu.icc.kspt.inspace.model.Position position) {
        numberOfPlanet = position.getNumberOfPlanet();
        numberOfSystem = position.getNumberOfSystem();
    }

    public int getNumberOfSystem() {
        return numberOfSystem;
    }

    public int getNumberOfPlanet() {
        return numberOfPlanet;
    }
}
