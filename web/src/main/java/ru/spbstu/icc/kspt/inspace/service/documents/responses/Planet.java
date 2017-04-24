package ru.spbstu.icc.kspt.inspace.service.documents.responses;


import ru.spbstu.icc.kspt.inspace.api.*;
import ru.spbstu.icc.kspt.inspace.model.exception.ActionIsNotPerforming;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Planet {

    private final String name;
    private final Position position;
    private final Resources resources;
    private final int energyProduction;
    private final int energyConsumption;
    private final int energyLevel;
    private final int numberOfFields;
    private final int numberOfEmptyFields;
    private final List<Building> buildings;
    private final BuildingUpgrade currentBuildingUpgrade;
    private final List<Research> research;
    private final ResearchUpgrade currentResearchUpgrade;
    private final List<Ship> ships;
    private final ShipConstruction currentShipConstruction;
    private final Fleet fleetOnPlanet;
    private final List<Mission> missions;
    private final List<Mission> externalMissions;

    public Planet(APlanet planet) {

        this.name = planet.getName();
        this.position = new Position(planet.getPosition());
        this.resources = new Resources(planet.getResources());

        this.energyProduction = planet.getEnergyProduction();
        this.energyConsumption = planet.getEnergyConsumption();
        this.energyLevel = planet.getEnergyLevel();

        this.numberOfFields = planet.getNumberOfFields();
        this.numberOfEmptyFields = planet.getNumberOfEmptyFields();

        this.buildings = new ArrayList<>();
        planet.getBuildings().values().
                forEach((Consumer<ABuilding>) building -> buildings.add(new Building(building)));

        BuildingUpgrade buildingUpgrade;
        try {
            buildingUpgrade = new BuildingUpgrade(planet.getCurrentBuildingUpgrade());
        } catch (ActionIsNotPerforming actionIsNotPerforming) {
            buildingUpgrade = null;
        }
        this.currentBuildingUpgrade = buildingUpgrade;

        this.research = new ArrayList<>();
        planet.getResearches().values().
                forEach((Consumer<AResearch>) aResearch -> research.add(new Research(aResearch)));

        ResearchUpgrade researchUpgrade;
        try {
            researchUpgrade = new ResearchUpgrade(planet.getCurrentResearchUpgrade());
        } catch (ActionIsNotPerforming actionIsNotPerforming) {
            researchUpgrade = null;
        }
        this.currentResearchUpgrade = researchUpgrade;

        this.ships = new ArrayList<>();
        planet.getShips().values().
                forEach((Consumer<AShipModel>) ship -> ships.add(new Ship(ship)));

        ShipConstruction shipConstruction;
        try {
            shipConstruction = new ShipConstruction(planet.getCurrentConstruct());
        } catch (ActionIsNotPerforming actionIsNotPerforming) {
            shipConstruction = null;
        }
        this.currentShipConstruction = shipConstruction;

        this.fleetOnPlanet = new Fleet(planet.getFleetOnPlanet());

        this.missions = new ArrayList<>();
        planet.getMissions().
                forEach((Consumer<AMission>) mission -> missions.add(new Mission(mission)));

        this.externalMissions = new ArrayList<>();
        planet.getExternalMissions().
                forEach((Consumer<AMission>) mission -> externalMissions.add(new Mission(mission)));
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public Resources getResources() {
        return resources;
    }

    public int getEnergyProduction() {
        return energyProduction;
    }

    public int getEnergyConsumption() {
        return energyConsumption;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    public int getNumberOfFields() {
        return numberOfFields;
    }

    public int getNumberOfEmptyFields() {
        return numberOfEmptyFields;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public BuildingUpgrade getCurrentBuildingUpgrade() {
        return currentBuildingUpgrade;
    }

    public List<Research> getResearch() {
        return research;
    }

    public ResearchUpgrade getCurrentResearchUpgrade() {
        return currentResearchUpgrade;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public ShipConstruction getCurrentShipConstruction() {
        return currentShipConstruction;
    }

    public Fleet getFleetOnPlanet() {
        return fleetOnPlanet;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public List<Mission> getExternalMissions() {
        return externalMissions;
    }
}
