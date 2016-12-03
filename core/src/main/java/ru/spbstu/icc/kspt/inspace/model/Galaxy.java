package ru.spbstu.icc.kspt.inspace.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Galaxy {

    private static Galaxy instance;

    public static Galaxy getInstance() {
        if (instance == null) {
            instance = new Galaxy();
        }
        return instance;
    }

    public static final int MAX_SYSTEM_NUMBER = 10;
    public static final int MAX_PLANET_NUMBER = 10;

    private Map<Position, Planet> galaxy = new HashMap<>();

    private Galaxy() { }

    private void checkPosition(Position position) {
        int planetNumber = position.getNumberOfPlanet();
        int systemNumber = position.getNumberOfSystem();
        if (systemNumber < 0 || systemNumber >= MAX_SYSTEM_NUMBER ||
            planetNumber < 0 || planetNumber >= MAX_PLANET_NUMBER) {

            throw new IndexOutOfBoundsException(position
                    + " Maximal number of systems: " + MAX_SYSTEM_NUMBER
                    + ", maximal number of planets: " + MAX_PLANET_NUMBER + ".");
        }
    }

    void addPlanet(Planet planet, Position position) {
        checkPosition(position);
        galaxy.put(position, planet);
    }

    public Planet getPlanet(Position position) {
        checkPosition(position);
        return galaxy.get(position);
    }

    public void deletePlanet(Position position) {
        checkPosition(position);
        galaxy.remove(position);
    }


}
