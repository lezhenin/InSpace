package ru.spbstu.icc.kspt.inspace.service.documents;


import ru.spbstu.icc.kspt.inspace.api.*;

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
    private final List<BuildingUpgrade> currentBuildingUpgrade;
    private final List<Research> research;
    private final List<ResearchUpgrade> currentResearchUpgrade;
    private final List<Ship> ships;
    private final List<ShipConstruction> currentShipConstruction;
    private final Fleet fleetOnPlanet;
    private final List<Mission> missions;
    private final List<Mission> externalMissions;

    public Planet(APlanet planet, String baseURL) {

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

        this.currentBuildingUpgrade = new ArrayList<>(1);
        if (planet.getCurrentBuildingUpgrade() != null) {
            currentBuildingUpgrade.add(new BuildingUpgrade(planet.getCurrentBuildingUpgrade()));
        }

        this.research = new ArrayList<>();
        planet.getResearches().values().
                forEach((Consumer<AResearch>) aResearch -> research.add(new Research(aResearch)));

        this.currentResearchUpgrade = new ArrayList<>(1);
        if (planet.getCurrentResearchUpgrade() != null) {
            currentResearchUpgrade.add(new ResearchUpgrade(planet.getCurrentResearchUpgrade()));
        }

        this.ships = new ArrayList<>();
        planet.getShips().values().
                forEach((Consumer<AShipModel>) ship -> ships.add(new Ship(ship)));

        this.currentShipConstruction = new ArrayList<>(1);
        if (planet.getCurrentConstruct() != null) {
            currentShipConstruction.add(new ShipConstruction(planet.getCurrentConstruct()));
        }

        this.fleetOnPlanet = new Fleet(planet.getFleetOnPlanet(), baseURL);

        this.missions = new ArrayList<>();
        planet.getMissions().
                forEach((Consumer<AMission>) mission -> missions.add(new Mission(mission, baseURL)));

        this.externalMissions = new ArrayList<>();
        planet.getExternalMissions().
                forEach((Consumer<AMission>) mission -> externalMissions.add(new Mission(mission, baseURL)));

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

    public List<BuildingUpgrade> getCurrentBuildingUpgrade() {
        return currentBuildingUpgrade;
    }

    public List<Research> getResearch() {
        return research;
    }

    public List<ResearchUpgrade> getCurrentResearchUpgrade() {
        return currentResearchUpgrade;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public List<ShipConstruction> getCurrentShipConstruction() {
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
