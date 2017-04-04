package ru.spbstu.icc.kspt.inspace.service.documents;

import java.util.List;

public class PlanetSystem {

    private final String name;
    private final int number;
    private final List<PlanetInfo> planetInfos;

    public PlanetSystem(String name, int number, List<PlanetInfo> planetInfos) {
        this.name = name;
        this.number = number;
        this.planetInfos = planetInfos;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public List<PlanetInfo> getPlanetInfos() {
        return planetInfos;
    }
}
