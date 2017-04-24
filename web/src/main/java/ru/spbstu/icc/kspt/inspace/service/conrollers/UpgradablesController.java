package ru.spbstu.icc.kspt.inspace.service.conrollers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.icc.kspt.inspace.api.APlanet;
import ru.spbstu.icc.kspt.inspace.api.AUpgrade;
import ru.spbstu.icc.kspt.inspace.model.Galaxy;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.exception.ActionIsNotPerforming;
import ru.spbstu.icc.kspt.inspace.model.exception.PlanetDoesntExist;
import ru.spbstu.icc.kspt.inspace.model.exception.UpgradeException;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;
import ru.spbstu.icc.kspt.inspace.service.documents.responses.Building;
import ru.spbstu.icc.kspt.inspace.service.documents.responses.BuildingUpgrade;
import ru.spbstu.icc.kspt.inspace.service.documents.responses.Research;
import ru.spbstu.icc.kspt.inspace.service.documents.responses.ResearchUpgrade;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "planets/{numberOfSystem}/{numberOfPlanet}/")
public class UpgradablesController {

    @RequestMapping(value = "buildings",
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
    @RequestMapping(value = "buildings/{buildingType}",
            method = RequestMethod.GET)
    Building building(@PathVariable("numberOfSystem") int numberOfSystem,
                      @PathVariable("numberOfPlanet") int numberOfPlanet,
                      @PathVariable("buildingType") BuildingType buildingType)
            throws PlanetDoesntExist {

        return new Building(Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getBuilding(buildingType));
    }

    @RequestMapping(value = "buildings/upgrade",
            method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    BuildingUpgrade upgradeBuilding(@PathVariable("numberOfSystem") int numberOfSystem,
                                    @PathVariable("numberOfPlanet") int numberOfPlanet,
                                    @RequestParam("buildingType") BuildingType type)
            throws PlanetDoesntExist, UpgradeException, AssertionError {

        APlanet planet = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet);
        planet.getBuilding(type).startUpgrade();

        try {
            return new BuildingUpgrade(planet.getCurrentBuildingUpgrade());
        } catch (ActionIsNotPerforming actionIsNotPerforming) {
            throw new AssertionError();
        }
    }

    @RequestMapping(value = "buildings/current-upgrade",
            method = RequestMethod.GET)
    BuildingUpgrade buildingUpgrade(@PathVariable("numberOfSystem") int numberOfSystem,
                                    @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist, ActionIsNotPerforming {

        AUpgrade upgrade = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getCurrentBuildingUpgrade();
        return new BuildingUpgrade(upgrade);
    }

    @RequestMapping(value = "research",
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
    @RequestMapping(value = "research/{researchType}",
            method = RequestMethod.GET)
    Research research(@PathVariable("numberOfSystem") int numberOfSystem,
                      @PathVariable("numberOfPlanet") int numberOfPlanet,
                      @PathVariable("researchType") ResearchType researchType)
            throws PlanetDoesntExist {

        return new Research(Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getResearch(researchType));
    }

    @RequestMapping(value = "research/upgrade",
            method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResearchUpgrade upgradeResearch(@PathVariable("numberOfSystem") int numberOfSystem,
                                    @PathVariable("numberOfPlanet") int numberOfPlanet,
                                    @RequestParam("researchType") ResearchType type)
            throws PlanetDoesntExist, UpgradeException, AssertionError {

        APlanet planet = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet);
        planet.getResearch(type).startUpgrade();

        try {
            return new ResearchUpgrade(planet.getCurrentBuildingUpgrade());
        } catch (ActionIsNotPerforming actionIsNotPerforming) {
            throw new AssertionError();
        }
    }

    @RequestMapping(value = "research/current-upgrade",
            method = RequestMethod.GET)
    ResearchUpgrade researchUpgrade(@PathVariable("numberOfSystem") int numberOfSystem,
                                    @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist, ActionIsNotPerforming {

        AUpgrade upgrade = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getCurrentResearchUpgrade();
        return new ResearchUpgrade(upgrade);
    }
}
