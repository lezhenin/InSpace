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
import ru.spbstu.icc.kspt.inspace.service.exceptions.UnitTypeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "planets/{numberOfSystem}/{numberOfPlanet}/")
public class UpgradablesController {

    private final Map<String, BuildingType> buildingTypeTable = new HashMap<>();
    private final Map<String, ResearchType> researchTypeTable = new HashMap<>();

    {
        for (BuildingType value: BuildingType.values()) {
            buildingTypeTable.put(value.toString().toLowerCase().replace('_','-'), value);
        }

        for (ResearchType value: ResearchType.values()) {
            researchTypeTable.put(value.toString().toLowerCase().replace('_','-'), value);
        }
    }

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

    @RequestMapping(value = "buildings/{buildingType}",
            method = RequestMethod.GET)
    Building building(@PathVariable("numberOfSystem") int numberOfSystem,
                      @PathVariable("numberOfPlanet") int numberOfPlanet,
                      @PathVariable("buildingType") String typeString)
            throws PlanetDoesntExist, UnitTypeException {

        BuildingType type;
        if (buildingTypeTable.containsKey(typeString)){
            type = buildingTypeTable.get(typeString);
        } else {
            throw new UnitTypeException(typeString);
        }

        return new Building(Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getBuilding(type));
    }

    @RequestMapping(value = "buildings/upgrade",
            method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    BuildingUpgrade upgradeBuilding(@PathVariable("numberOfSystem") int numberOfSystem,
                                    @PathVariable("numberOfPlanet") int numberOfPlanet,
                                    @RequestParam("buildingType") String typeString)
            throws PlanetDoesntExist, UpgradeException, AssertionError, UnitTypeException {

        BuildingType type;
        if (buildingTypeTable.containsKey(typeString)){
            type = buildingTypeTable.get(typeString);
        } else {
            throw new UnitTypeException(typeString);
        }

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

    @RequestMapping(value = "research/{researchType}",
            method = RequestMethod.GET)
    Research research(@PathVariable("numberOfSystem") int numberOfSystem,
                      @PathVariable("numberOfPlanet") int numberOfPlanet,
                      @PathVariable("researchType") String typeString)
            throws PlanetDoesntExist, UnitTypeException {

        ResearchType type;
        if (researchTypeTable.containsKey(typeString)){
            type = researchTypeTable.get(typeString);
        } else {
            throw new UnitTypeException(typeString);
        }

        return new Research(Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getResearch(type));
    }

    @RequestMapping(value = "research/upgrade",
            method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResearchUpgrade upgradeResearch(@PathVariable("numberOfSystem") int numberOfSystem,
                                    @PathVariable("numberOfPlanet") int numberOfPlanet,
                                    @RequestParam("researchType") String typeString)
            throws PlanetDoesntExist, UpgradeException, AssertionError, UnitTypeException {

        ResearchType type;
        if (researchTypeTable.containsKey(typeString)){
            type = researchTypeTable.get(typeString);
        } else {
            throw new UnitTypeException(typeString);
        }

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
