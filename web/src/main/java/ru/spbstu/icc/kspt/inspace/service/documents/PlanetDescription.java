package ru.spbstu.icc.kspt.inspace.service.documents;


import ru.spbstu.icc.kspt.inspace.api.APlanet;

import java.util.HashMap;
import java.util.Map;

public class PlanetDescription {

    private final String name;
    private final Position position;
    private final String url;

    public PlanetDescription(APlanet planet, String baseURL) {
        this.name = planet.getName();
        position = new Position(planet.getPosition());
        url = baseURL + planet.getPosition().getNumberOfSystem()
                + "/" + planet.getPosition().getNumberOfPlanet();
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public String getUrl() {
        return url;
    }
}
