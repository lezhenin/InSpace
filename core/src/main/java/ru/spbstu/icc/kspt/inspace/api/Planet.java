package ru.spbstu.icc.kspt.inspace.api;

import ru.spbstu.icc.kspt.inspace.model.Position;
import ru.spbstu.icc.kspt.inspace.model.buildings.Building;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;

import java.util.*;

public class Planet {
    private ru.spbstu.icc.kspt.inspace.model.Planet planet;

    public Planet(ru.spbstu.icc.kspt.inspace.model.Planet planet) {
        this.planet = planet;
    }

    public Planet(String name, Position position) {
        planet = new ru.spbstu.icc.kspt.inspace.model.Planet(name, position);
    }

    public Planet(String name, Position position, int size) {
        planet = new ru.spbstu.icc.kspt.inspace.model.Planet(name, position, size);


    }
    public Position getPosition() {
        return planet.getPosition();
    }

    public ResourcesInfo getResources() {
        return new ResourcesInfo(planet.getResources());
    }

    public void balanceEnergyConsumption() {
        planet.balanceEnergyConsumption();
    }

    public int getEnergyProduction() {
        return planet.getEnergyProduction();
    }

    public int getEnergyConsumption() {
        return planet.getEnergyConsumption();
    }

    public int getEnergyLevel() {
        return planet.getEnergyLevel();
    }

    public int getNumberOfFields() {
        return planet.getNumberOfFields();
    }

    public int getNumberOfEmptyFields() {
        return planet.getNumberOfEmptyFields();
    }

    public String getName() {
        return planet.getName();
    }

    public Building getBuilding(BuildingType type) {
        return planet.getBuilding(type);

        //TODO
    }

//    public Set<Map.Entry<BuildingType, Building>> getBuildings() {
//        update();
//        return buildingDepartment.getBuildings();
//    }
//
//    public Upgrade getCurrentBuildingUpgrade() {
//        update();
//        return buildingDepartment.getCurrentUpgrade();
//    }
//
//    public Research getResearch(ResearchType type) {
//        update();
//        return researchDepartment.getResearch(type);
//    }
//
//    public Set<Map.Entry<ResearchType, Research>> getResearches() {
//        update();
//        return researchDepartment.getResearches();
//    }
//
//    public Upgrade getCurrentResearchUpgrade() {
//        update();
//        return researchDepartment.getCurrentUpgrade();
//    }
//
//    public Set<Map.Entry<ShipType, Ship>> getShips() {
//        update();
//        return fleetDepartment.getShips().entrySet();
//    }
//
    public FleetInfo getFleetOnPlanet() {
        return new FleetInfo(planet.getFleetOnPlanet());
    }

    public List<MissionInfo> getMissions() {
        List<MissionInfo> list = new ArrayList<>();
        planet.getMissions().forEach(m -> list.add(new MissionInfo(m)));
        return list;
    }

    public List<MissionInfo> getExternalMissions() {
        List<MissionInfo> list = new ArrayList<>();
        planet.getExternalMissions().forEach(m -> list.add(new MissionInfo(m)));
        return list;
    }
}
