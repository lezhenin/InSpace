package ru.spbstu.icc.kspt.inspace.model;

import java.util.ArrayList;
import java.util.List;

public class Galaxy {

    public static final int MAX_SYSTEM_NUMBER = 10;
    public static final int MAX_PLANET_NUMBER = 10;

    private List<List<Planet>> galaxy = new ArrayList<>();

    public Galaxy() {
        for (int i = 0; i < MAX_SYSTEM_NUMBER; i++) {
            galaxy.add(new ArrayList<>());
            for (int j = 0; j < MAX_PLANET_NUMBER; j++) {
                galaxy.get(i).add(null);
            }
        }
    }

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

    public void addPlanet(Planet planet, Position position) {
        checkPosition(position);
        galaxy.get(position.getNumberOfSystem()).add(position.getNumberOfPlanet(), planet);
    }

    public Planet getPlanet(Position position) {
        checkPosition(position);
        return galaxy.get(position.getNumberOfSystem()).get(position.getNumberOfPlanet());
    }

    public void deletePlanet(Position position) {
        checkPosition(position);
        galaxy.get(position.getNumberOfSystem()).set(position.getNumberOfPlanet(), null);
    }


}
