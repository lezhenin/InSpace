package ru.spbstu.icc.kspt.inspace.service.documents;


import ru.spbstu.icc.kspt.inspace.api.APlanet;

public class PlanetInfo {
    private final String name;

    public PlanetInfo(String name) {
        this.name = name;
    }

    public PlanetInfo(APlanet planet) {
        this.name = planet.getName();
    }

    public String getName() {
        return name;
    }
}
