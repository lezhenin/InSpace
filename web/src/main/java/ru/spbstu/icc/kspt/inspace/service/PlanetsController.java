package ru.spbstu.icc.kspt.inspace.service;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.icc.kspt.inspace.api.ABuilding;
import ru.spbstu.icc.kspt.inspace.api.APlanet;
import ru.spbstu.icc.kspt.inspace.model.Galaxy;
import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.Position;
import ru.spbstu.icc.kspt.inspace.service.documents.Building;
import ru.spbstu.icc.kspt.inspace.service.documents.PlanetDescription;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class PlanetsController {

    {
        Random random = new Random(214);
        for (int i = 0; i < 30; i++) {
            int numberOfSystem;
            int numberOfPlanet;
            numberOfPlanet = random.nextInt(10);
            numberOfSystem = random.nextInt(10);
            new Planet("planet" + random.nextInt(), new Position(numberOfSystem, numberOfPlanet));
        }
    }

    @RequestMapping(value = {"/planets"}, method = RequestMethod.GET)
    List<PlanetDescription> planets(HttpServletRequest request) {

        List<PlanetDescription> allDescriptions = new ArrayList<>();

        for (int i = 0; i < Galaxy.MAX_SYSTEM_NUMBER; i++) {
            List<PlanetDescription> systemDescriptions = new ArrayList<>();
            for (APlanet planet: Galaxy.getInstance().getPlanets(i)) {
                systemDescriptions.add(new PlanetDescription(planet, getBaseURL(request) + "planets/"));
            }
            allDescriptions.addAll(systemDescriptions);
        }

        return allDescriptions;
    }

    @RequestMapping(value = {"/planets/{numberOfSystem}"}, method = RequestMethod.GET)
    List<PlanetDescription> system(@PathVariable("numberOfSystem") int systemNumber, HttpServletRequest request) {

        List<PlanetDescription> descriptions = new ArrayList<>();
        for (APlanet planet : Galaxy.getInstance().getPlanets(systemNumber)) {
            descriptions.add(new PlanetDescription(planet, getBaseURL(request) + "planets/"));
        }
        return descriptions;
    }

    private String getBaseURL(HttpServletRequest request) {
        return String.format("%s://%s:%d/", request.getScheme(), request.getServerName(), request.getServerPort());
    }

    @RequestMapping("planets/{numberOfSystem}/{numberOfPlanet}/buildings")
    List<Building> buildings(@PathVariable("numberOfSystem") int numberOfSystem,
                             @PathVariable("numberOfPlanet") int numberOfPlanet) {

        List<Building> buildings = new ArrayList<>();

        for (ABuilding building: Galaxy.getInstance().
                getPlanet(numberOfSystem, numberOfPlanet).
                getBuildings().values()) {
            buildings.add(new Building(building));
        }

        return buildings;
    }

}
