package ru.spbstu.icc.kspt.inspace.model;

import ru.spbstu.icc.kspt.inspace.model.exception.PlanetDoesntExist;

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

    private Map<Position, Planet> galaxy = new HashMap<>();

    private Galaxy() { }


    void addPlanet(Planet planet, Position position) {
        galaxy.put(position, planet);
    }

    public Planet getPlanet(Position position) throws PlanetDoesntExist {
        Planet planet = galaxy.get(position);
        if (planet == null) {
            throw new PlanetDoesntExist(position);
        }
        return planet;
    }

    public Planet getPlanet(int numberOfSystem, int numberOfPlanet) throws PlanetDoesntExist {
        Position position = new Position(numberOfSystem, numberOfPlanet);
        return getPlanet(position);
    }

    public List<Planet> getPlanets(int systemNumber) {
        List<Planet> planets = new ArrayList<>();
        for (Map.Entry<Position, Planet> entry :galaxy.entrySet()) {
            if (entry.getKey().getNumberOfSystem() == systemNumber) {
                planets.add(entry.getValue());
            }
        }
        return planets;
    }

    public List<Planet> getAllPlanets() {
        return new ArrayList<>(galaxy.values());
    }

    public void deletePlanet(Position position) {
        galaxy.remove(position);
    }


}
