package ru.spbstu.icc.kspt.inspace.service.documents;


public class Position {
    private int numberOfSystem;
    private int numberOfPlanet;

    public Position(int numberOfSystem, int numberOfPlanet) {
        this.numberOfSystem = numberOfSystem;
        this.numberOfPlanet = numberOfPlanet;
    }

    public Position(ru.spbstu.icc.kspt.inspace.model.Position position) {
        numberOfPlanet = position.getNumberOfPlanet();
        numberOfSystem = position.getNumberOfSystem();
    }

    public Position() {
    }

    public void setNumberOfSystem(int numberOfSystem) {
        this.numberOfSystem = numberOfSystem;
    }

    public void setNumberOfPlanet(int numberOfPlanet) {
        this.numberOfPlanet = numberOfPlanet;
    }

    public int getNumberOfSystem() {
        return numberOfSystem;
    }

    public int getNumberOfPlanet() {
        return numberOfPlanet;
    }
}
