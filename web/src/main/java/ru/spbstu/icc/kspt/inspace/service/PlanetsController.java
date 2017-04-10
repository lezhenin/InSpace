package ru.spbstu.icc.kspt.inspace.service;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.icc.kspt.inspace.api.*;
import ru.spbstu.icc.kspt.inspace.model.Galaxy;
import ru.spbstu.icc.kspt.inspace.model.Position;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;
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
            new ru.spbstu.icc.kspt.inspace.model.Planet(
                    "planet" + random.nextInt(), new Position(numberOfSystem, numberOfPlanet));
        }
    }

    private String getBaseURL(HttpServletRequest request) {
        return String.format("%s://%s:%d/", request.getScheme(), request.getServerName(), request.getServerPort());
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

    @RequestMapping(value = "planets/{numberOfSystem}/{numberOfPlanet}", method = RequestMethod.GET)
    Planet planet(@PathVariable("numberOfSystem") int numberOfSystem,
                  @PathVariable("numberOfPlanet") int numberOfPlanet,
                  HttpServletRequest request) {
        return new Planet(Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet), getBaseURL(request));
    }

    @RequestMapping(value = "planets/{numberOfSystem}/{numberOfPlanet}/buildings", method = RequestMethod.GET)
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
    @RequestMapping(value = "planets/{numberOfSystem}/{numberOfPlanet}/buildings/{buildingType}", method = RequestMethod.GET)
    Building building(@PathVariable("numberOfSystem") int numberOfSystem,
                      @PathVariable("numberOfPlanet") int numberOfPlanet,
                      @PathVariable("buildingType")   BuildingType buildingType) {
        return new Building(Galaxy.getInstance().
                getPlanet(numberOfSystem, numberOfPlanet).getBuilding(buildingType));
    }

    @RequestMapping(value = "planets/{numberOfSystem}/{numberOfPlanet}/buildings/current-upgrade", method = RequestMethod.GET)
    List<BuildingUpgrade> buildingUpgrade(@PathVariable("numberOfSystem") int numberOfSystem,
                                              @PathVariable("numberOfPlanet") int numberOfPlanet) {
        List<BuildingUpgrade> list = new ArrayList<>();
        AUpgrade upgrade = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getCurrentBuildingUpgrade();
        if (upgrade != null) {
            list.add(new BuildingUpgrade(upgrade));
        }
        return list;
    }

    @RequestMapping(value = "planets/{numberOfSystem}/{numberOfPlanet}/research", method = RequestMethod.GET)
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
    @RequestMapping(value = "planets/{numberOfSystem}/{numberOfPlanet}/research/{researchType}", method = RequestMethod.GET)
    Research research(@PathVariable("numberOfSystem") int numberOfSystem,
                      @PathVariable("numberOfPlanet") int numberOfPlanet,
                      @PathVariable("researchType")   ResearchType researchType) {
        return new Research(Galaxy.getInstance().
                getPlanet(numberOfSystem, numberOfPlanet).getResearch(researchType));
    }

    @RequestMapping(value = "planets/{numberOfSystem}/{numberOfPlanet}/research/current-upgrade", method = RequestMethod.GET)
    List<ResearchUpgrade> researchUpgrade(@PathVariable("numberOfSystem") int numberOfSystem,
                                          @PathVariable("numberOfPlanet") int numberOfPlanet) {
        List<ResearchUpgrade> list = new ArrayList<>();
        AUpgrade upgrade = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getCurrentResearchUpgrade();
        if (upgrade != null) {
            list.add(new ResearchUpgrade(upgrade));
        }
        return list;
    }

    @RequestMapping(value = "planets/{numberOfSystem}/{numberOfPlanet}/ships", method = RequestMethod.GET)
    List<Ship> ships(@PathVariable("numberOfSystem") int numberOfSystem,
                     @PathVariable("numberOfPlanet") int numberOfPlanet) {
        List<Ship> list = new ArrayList<>();
        for (AShipModel shipModel: Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getShips().values()){
            list.add(new Ship(shipModel));
        }
        return list;
    }

    //fixme 10.04.17 path variable to lower case
    @RequestMapping(value = "planets/{numberOfSystem}/{numberOfPlanet}/research/{shipType}", method = RequestMethod.GET)
    Ship ship(@PathVariable("numberOfSystem") int numberOfSystem,
                  @PathVariable("numberOfPlanet") int numberOfPlanet,
                  @PathVariable("researchType")   ShipType shipType) {
        return new Ship(Galaxy.getInstance().
                getPlanet(numberOfSystem, numberOfPlanet).getShips().get(shipType));
    }

    @RequestMapping(value = "planets/{numberOfSystem}/{numberOfPlanet}/ships/current-construction", method = RequestMethod.GET)
    List<ShipConstruction> shipConstruction(@PathVariable("numberOfSystem") int numberOfSystem,
                                          @PathVariable("numberOfPlanet") int numberOfPlanet) {
        List<ShipConstruction> list = new ArrayList<>();
        AConstruct construction = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getCurrentConstruct();
        if (construction != null) {
            list.add(new ShipConstruction(construction));
        }
        return list;
    }

    @RequestMapping(value = "planets/{numberOfSystem}/{numberOfPlanet}/fleet", method = RequestMethod.GET)
    Fleet fleet(@PathVariable("numberOfSystem") int numberOfSystem,
                @PathVariable("numberOfPlanet") int numberOfPlanet,
                HttpServletRequest request) {
        return new Fleet(Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getFleetOnPlanet(),
                getBaseURL(request));
    }

    @RequestMapping(value = "planets/{numberOfSystem}/{numberOfPlanet}/missions", method = RequestMethod.GET)
    List<Mission> missions(@PathVariable("numberOfSystem") int numberOfSystem,
                           @PathVariable("numberOfPlanet") int numberOfPlanet,
                           HttpServletRequest request) {
        List<Mission> list = new ArrayList<>();
        for(AMission mission: Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getMissions()) {
            list.add(new Mission(mission, getBaseURL(request)));
        }
        return list;
    }

    @RequestMapping(value = "planets/{numberOfSystem}/{numberOfPlanet}/external-missions", method = RequestMethod.GET)
    List<Mission> externalMissions(@PathVariable("numberOfSystem") int numberOfSystem,
                                   @PathVariable("numberOfPlanet") int numberOfPlanet,
                                   HttpServletRequest request) {
        List<Mission> list = new ArrayList<>();
        for(AMission mission: Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getExternalMissions()) {
            list.add(new Mission(mission, getBaseURL(request)));
        }
        return list;
    }
}
