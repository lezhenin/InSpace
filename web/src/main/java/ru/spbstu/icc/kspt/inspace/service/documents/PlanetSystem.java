package ru.spbstu.icc.kspt.inspace.service.documents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanetSystem {

    private final String name;
    private final int number;
    private final List<PlanetInfo> planetInfos;
    private final Map<String, Link> links;

    public PlanetSystem(String name, int number, List<PlanetInfo> planetInfos, String baseURL) {
        this.name = name;
        this.number = number;
        this.planetInfos = planetInfos;

        links = new HashMap<>();
        links.put("self", new Link(baseURL + number));

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

    public Map<String, Link> getLinks() {
        return links;
    }
}
