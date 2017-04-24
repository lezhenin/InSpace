package ru.spbstu.icc.kspt.inspace.service.documents.requests;


public class PlanetCreateInfo {

    private String name;
    private int numberOfPlanet;
    private int numberOfSystem;

    public PlanetCreateInfo() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberOfPlanet(int numberOfPlanet) {
        this.numberOfPlanet = numberOfPlanet;
    }

    public void setNumberOfSystem(int numberOfSystem) {
        this.numberOfSystem = numberOfSystem;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfPlanet() {
        return numberOfPlanet;
    }

    public int getNumberOfSystem() {
        return numberOfSystem;
    }
}
