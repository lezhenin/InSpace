package ru.spbstu.icc.kspt.inspace.model;

import ru.spbstu.icc.kspt.inspace.api.APlanet;
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

    private Map<Position, APlanet> galaxy = new HashMap<>();

    private Galaxy() { }


    public void addPlanet(Position position, String name) {
        APlanet planet = new Planet(name, position);
        galaxy.put(position, planet);
    }

    public APlanet getPlanet(Position position) throws PlanetDoesntExist {
        APlanet planet = galaxy.get(position);
        if (planet == null) {
            throw new PlanetDoesntExist(position);
        }
        return planet;
    }

    public APlanet getPlanet(int numberOfSystem, int numberOfPlanet) throws PlanetDoesntExist {
        Position position = new Position(numberOfSystem, numberOfPlanet);
        return getPlanet(position);
    }

    public List<APlanet> getPlanets(int systemNumber) {
        List<APlanet> planets = new ArrayList<>();
        for (Map.Entry<Position, APlanet> entry :galaxy.entrySet()) {
            if (entry.getKey().getNumberOfSystem() == systemNumber) {
                planets.add(entry.getValue());
            }
        }
        return planets;
    }

    public List<APlanet> getAllPlanets() {
        return new ArrayList<>(galaxy.values());
    }

    public void deletePlanet(Position position) {
        galaxy.remove(position);
    }


}
