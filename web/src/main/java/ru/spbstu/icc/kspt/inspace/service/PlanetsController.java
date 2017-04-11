package ru.spbstu.icc.kspt.inspace.service;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.icc.kspt.inspace.api.*;
import ru.spbstu.icc.kspt.inspace.model.Galaxy;
import ru.spbstu.icc.kspt.inspace.model.Position;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.exception.ActionIsNotPerforming;
import ru.spbstu.icc.kspt.inspace.model.exception.PlanetDoesntExist;
import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;
import ru.spbstu.icc.kspt.inspace.service.documents.*;

import java.util.*;

@RestController
@RequestMapping(value = "planets")
public class PlanetsController {

    {
        Random random = new Random(214);
        for (int i = 0; i < 50; i++) {
            int numberOfSystem;
            int numberOfPlanet;
            numberOfPlanet = random.nextInt(15);
            numberOfSystem = random.nextInt(15);
            Galaxy.getInstance().
                    addPlanet(new Position(numberOfSystem, numberOfPlanet), "planet" + random.nextInt());
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    List<PlanetDescription> planets() {

        List<PlanetDescription> allDescriptions = new ArrayList<>();
        Galaxy.getInstance().getAllPlanets().
                forEach(planet -> allDescriptions.add(new PlanetDescription(planet)));
        return allDescriptions;
    }

    @RequestMapping(value = {"{numberOfSystem}"},
                    method = RequestMethod.GET)
    List<PlanetDescription> system(@PathVariable("numberOfSystem") int numberOfSystem) {

        List<PlanetDescription> descriptions = new ArrayList<>();
        Galaxy.getInstance().getPlanets(numberOfSystem)
                .forEach(planet -> descriptions.add(new PlanetDescription(planet)));
        return descriptions;
    }

    @RequestMapping(value = "{numberOfSystem}/{numberOfPlanet}",
                    method = RequestMethod.GET)
    Planet planet(@PathVariable("numberOfSystem") int numberOfSystem,
                  @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist {

        APlanet planet = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet);
        return new Planet(planet);
    }

    @RequestMapping(value = "{numberOfSystem}/{numberOfPlanet}/buildings",
                    method = RequestMethod.GET)
    List<Building> buildings(@PathVariable("numberOfSystem") int numberOfSystem,
                             @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist {

        List<Building> buildings = new ArrayList<>();
        APlanet planet = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet);
        planet.getBuildings().values().forEach(building -> buildings.add(new Building(building)));
        return buildings;
    }

    //fixme 10.04.17 path variable to lower case
    @RequestMapping(value = "{numberOfSystem}/{numberOfPlanet}/buildings/{buildingType}",
                    method = RequestMethod.GET)
    Building building(@PathVariable("numberOfSystem") int numberOfSystem,
                      @PathVariable("numberOfPlanet") int numberOfPlanet,
                      @PathVariable("buildingType") BuildingType buildingType)
            throws PlanetDoesntExist {

        return new Building(Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getBuilding(buildingType));
    }

    //todo return 204
    @RequestMapping(value = "{numberOfSystem}/{numberOfPlanet}/buildings/current-upgrade",
                    method = RequestMethod.GET)
    BuildingUpgrade buildingUpgrade(@PathVariable("numberOfSystem") int numberOfSystem,
                                    @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist, ActionIsNotPerforming {

        AUpgrade upgrade = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getCurrentBuildingUpgrade();
        return new BuildingUpgrade(upgrade);
    }

    @RequestMapping(value = "{numberOfSystem}/{numberOfPlanet}/research",
                    method = RequestMethod.GET)
    List<Research> research(@PathVariable("numberOfSystem") int numberOfSystem,
                            @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist {

        List<Research> researchs = new ArrayList<>();
        APlanet planet = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet);
        planet.getResearches().values().forEach(research -> researchs.add(new Research(research)));
        return researchs;
    }

    //fixme 10.04.17 path variable to lower case
    @RequestMapping(value = "{numberOfSystem}/{numberOfPlanet}/research/{researchType}",
                    method = RequestMethod.GET)
    Research research(@PathVariable("numberOfSystem") int numberOfSystem,
                      @PathVariable("numberOfPlanet") int numberOfPlanet,
                      @PathVariable("researchType") ResearchType researchType)
            throws PlanetDoesntExist {

        return new Research(Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getResearch(researchType));
    }

    //todo return 204
    @RequestMapping(value = "{numberOfSystem}/{numberOfPlanet}/research/current-upgrade",
                    method = RequestMethod.GET)
    ResearchUpgrade researchUpgrade(@PathVariable("numberOfSystem") int numberOfSystem,
                                          @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist, ActionIsNotPerforming {

        AUpgrade upgrade = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getCurrentResearchUpgrade();
        return new ResearchUpgrade(upgrade);
    }

    @RequestMapping(value = "{numberOfSystem}/{numberOfPlanet}/ships",
                    method = RequestMethod.GET)
    List<Ship> ships(@PathVariable("numberOfSystem") int numberOfSystem,
                     @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist {

        List<Ship> ships = new ArrayList<>();
        APlanet planet = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet);
        planet.getShips().values().forEach(ship -> ships.add(new Ship((ship))));
        return ships;
    }

    //fixme 10.04.17 path variable to lower case
    @RequestMapping(value = "{numberOfSystem}/{numberOfPlanet}/research/{shipType}",
                    method = RequestMethod.GET)
    Ship ship(@PathVariable("numberOfSystem") int numberOfSystem,
              @PathVariable("numberOfPlanet") int numberOfPlanet,
              @PathVariable("researchType") ShipType shipType)
            throws PlanetDoesntExist {

        return new Ship(Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getShips().get(shipType));
    }

    @RequestMapping(value = "{numberOfSystem}/{numberOfPlanet}/ships/current-construction",
                    method = RequestMethod.GET)
    ShipConstruction shipConstruction(@PathVariable("numberOfSystem") int numberOfSystem,
                                            @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist, ActionIsNotPerforming {

        AConstruct construction = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getCurrentConstruct();
        return new ShipConstruction(construction);
    }

    @RequestMapping(value = "{numberOfSystem}/{numberOfPlanet}/fleet",
                    method = RequestMethod.GET)
    Fleet fleet(@PathVariable("numberOfSystem") int numberOfSystem,
                @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist {

        return new Fleet(Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getFleetOnPlanet());
    }

    @RequestMapping(value = "{numberOfSystem}/{numberOfPlanet}/missions",
                    method = RequestMethod.GET)
    List<Mission> missions(@PathVariable("numberOfSystem") int numberOfSystem,
                           @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist {

        List<Mission> list = new ArrayList<>();
        APlanet planet = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet);
        planet.getMissions().forEach(mission -> list.add(new Mission(mission)));
        return list;
    }

    @RequestMapping(value = "{numberOfSystem}/{numberOfPlanet}/external-missions",
                    method = RequestMethod.GET)
    List<Mission> externalMissions(@PathVariable("numberOfSystem") int numberOfSystem,
                                   @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist {

        List<Mission> list = new ArrayList<>();
        APlanet planet = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet);
        planet.getExternalMissions().forEach(mission -> list.add(new Mission(mission)));
        return list;
    }

    @ResponseStatus(value= HttpStatus.NO_CONTENT, reason="Planet does not exist")
    @ExceptionHandler(PlanetDoesntExist.class)
    public void planetDoesntExistHandler(PlanetDoesntExist e) { }

    @ResponseStatus(value= HttpStatus.NO_CONTENT, reason="Requested action is not performing")
    @ExceptionHandler(ActionIsNotPerforming.class)
    public void actionIsNotPerformingHandler(ActionIsNotPerforming e) { }
}
