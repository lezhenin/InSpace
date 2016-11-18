package ru.spbstu.icc.kspt.inspace.model;

import ru.spbstu.icc.kspt.inspace.model.buildings.*;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingDepartment;
import ru.spbstu.icc.kspt.inspace.model.energy.EnergyDepartment;
import ru.spbstu.icc.kspt.inspace.model.fleet.Fleet;
import ru.spbstu.icc.kspt.inspace.model.fleet.FleetDepartment;
import ru.spbstu.icc.kspt.inspace.model.fleet.Ship;
import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;
import ru.spbstu.icc.kspt.inspace.model.research.Research;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchDepartment;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgrade;

import java.util.*;


public class Planet {

    private Position position;
    private int size = 200;
    private String name;
    private Resources resources;

    private BuildingDepartment buildingDepartment;
    private EnergyDepartment energyDepartment;
    private ResearchDepartment researchDepartment;
    private FleetDepartment fleetDepartment;

    public Planet(String name, Position position) {

        Galaxy.getInstance().addPlanet(this, position);

        this.name = name;
        this.resources = new Resources(0,0,0);

        researchDepartment = new ResearchDepartment(this);
        buildingDepartment = new BuildingDepartment(this);
        energyDepartment = new EnergyDepartment(this);
        fleetDepartment = new FleetDepartment(this);

        buildingDepartment.updateDependencies();
        researchDepartment.updateDependencies();
    }

    public Planet(String name, Position position, int size) {
        this(name, position);
        this.size = size;
    }

    public Position getPosition() {
        return position;
    }

    public Resources getResources() {
        updateResources();
        return resources;
    }

    public void balanceEnergyConsumption() {
        updateBuildings();
        energyDepartment.balanceEnergyConsumption();
    }

    public int getEnergyProduction() {
        updateBuildings();
        return energyDepartment.getTotalEnergyProduction();
    }

    public int getEnergyConsumption() {
        updateBuildings();
        return energyDepartment.getTotalEnergyConsumption();
    }

    public int getEnergyLevel() {
        updateBuildings();
        return energyDepartment.getEnergyLevel();
    }

    public int getSize() {
        return size;
    }

    public int getEmptyFields() {
        return size - buildingDepartment.getFields();
    }

    public String getName() {
        return name;
    }

    public Building getBuilding(BuildingType type) {
        return buildingDepartment.getBuilding(type);
    }

    public Set<Map.Entry<BuildingType, Building>> getBuildings() {
        return buildingDepartment.getBuildings();
    }

    public Upgrade getCurrentBuildingUpgrade() {
        return buildingDepartment.getCurrentUpgrade();
    }

    public Research getResearch(ResearchType type) {
        return researchDepartment.getResearch(type);
    }

    public Set<Map.Entry<ResearchType, Research>> getResearches() {
        return researchDepartment.getResearches();
    }

    public Upgrade getCurrentResearchUpgrade() {
        return researchDepartment.getCurrentUpgrade();
    }

    public Set<Map.Entry<ShipType, Ship>> getShips() {
        return fleetDepartment.getShips().entrySet();
    }

    public Fleet getFleet() {
        return fleetDepartment.getFleet();
    }

    private void updateResources() {
        updateBuildings();
        for (Mine mine: buildingDepartment.getMines()) {
            resources.addResources(mine.getProduction());
        }
    }

    private void updateBuildings(){
        updateResearches();
        buildingDepartment.update();
    }

    private void updateResearches() {
        researchDepartment.update();
    }

    private void updateFleet() {
        fleetDepartment.update();
    }

    public void update() {
        updateResources();
        updateFleet();
    }

    public void rename(String newName) {
        name = newName;
    }

}