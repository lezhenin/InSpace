package ru.spbstu.icc.kspt.inspace.service;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.icc.kspt.inspace.api.ABuilding;
import ru.spbstu.icc.kspt.inspace.api.APlanet;
import ru.spbstu.icc.kspt.inspace.api.AResearch;
import ru.spbstu.icc.kspt.inspace.api.AUpgrade;
import ru.spbstu.icc.kspt.inspace.model.Galaxy;
import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.Position;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgrade;
import ru.spbstu.icc.kspt.inspace.service.documents.*;

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

    //fixme 10.04.17 path variable to lower case
    @RequestMapping("planets/{numberOfSystem}/{numberOfPlanet}/buildings/{buildingType}")
    Building building(@PathVariable("numberOfSystem") int numberOfSystem,
                      @PathVariable("numberOfPlanet") int numberOfPlanet,
                      @PathVariable("buildingType")   BuildingType buildingType) {
        return new Building(Galaxy.getInstance().
                getPlanet(numberOfSystem, numberOfPlanet).getBuilding(buildingType));
    }

    @RequestMapping("planets/{numberOfSystem}/{numberOfPlanet}/buildings/current-update")
    List<BuildingUpgrade> buildingUpgrade(@PathVariable("numberOfSystem") int numberOfSystem,
                                              @PathVariable("numberOfPlanet") int numberOfPlanet) {
        List<BuildingUpgrade> list = new ArrayList<>();
        AUpgrade upgrade = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getCurrentBuildingUpgrade();
        if (upgrade != null) {
            list.add(new BuildingUpgrade(upgrade));
        }
        return list;
    }

    @RequestMapping("planets/{numberOfSystem}/{numberOfPlanet}/research")
    List<Research> research(@PathVariable("numberOfSystem") int numberOfSystem,
                            @PathVariable("numberOfPlanet") int numberOfPlanet) {

        List<Research> researchs = new ArrayList<>();

        for (AResearch research: Galaxy.getInstance().
                getPlanet(numberOfSystem, numberOfPlanet).
                getResearches().values()) {
            researchs.add(new Research(research));
        }

        return researchs;
    }

    //fixme 10.04.17 path variable to lower case
    @RequestMapping("planets/{numberOfSystem}/{numberOfPlanet}/research/{researchType}")
    Research research(@PathVariable("numberOfSystem") int numberOfSystem,
                      @PathVariable("numberOfPlanet") int numberOfPlanet,
                      @PathVariable("researchType")   ResearchType researchType) {
        return new Research(Galaxy.getInstance().
                getPlanet(numberOfSystem, numberOfPlanet).getResearch(researchType));
    }

    @RequestMapping("planets/{numberOfSystem}/{numberOfPlanet}/research/current-update")
    List<ResearchUpgrade> researchUpgrade(@PathVariable("numberOfSystem") int numberOfSystem,
                                          @PathVariable("numberOfPlanet") int numberOfPlanet) {
        List<ResearchUpgrade> list = new ArrayList<>();
        AUpgrade upgrade = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getCurrentResearchUpgrade();
        if (upgrade != null) {
            list.add(new ResearchUpgrade(upgrade));
        }
        return list;
    }

}
