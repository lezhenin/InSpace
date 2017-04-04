package ru.spbstu.icc.kspt.inspace.service.documents;


import ru.spbstu.icc.kspt.inspace.api.APlanet;

import java.util.HashMap;
import java.util.Map;

public class PlanetInfo {

    private final String name;
    private final Position position;
    private final Map<String, Link> links;

    public PlanetInfo(APlanet planet, String baseURL) {
        this.name = planet.getName();
        position = new Position(planet.getPosition());
        links = new HashMap<>();
        links.put("planet", new Link(baseURL + planet.getPosition().getNumberOfSystem()
                + "/" + planet.getPosition().getNumberOfPlanet()));
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public Map<String, Link> getLinks() {
        return links;
    }
}
