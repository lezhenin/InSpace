package ru.spbstu.icc.kspt.inspace.model;

import ru.spbstu.icc.kspt.inspace.model.buildings.*;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingDepartment;
import ru.spbstu.icc.kspt.inspace.model.energy.EnergyDepartment;
import ru.spbstu.icc.kspt.inspace.model.fleet.Fleet;
import ru.spbstu.icc.kspt.inspace.model.fleet.FleetDepartment;
import ru.spbstu.icc.kspt.inspace.model.fleet.Ship;
import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;
import ru.spbstu.icc.kspt.inspace.model.fleet.missions.Mission;
import ru.spbstu.icc.kspt.inspace.model.research.Research;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchDepartment;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;
import ru.spbstu.icc.kspt.inspace.model.resources.ResourceDepartment;
import ru.spbstu.icc.kspt.inspace.model.resources.Resources;
import ru.spbstu.icc.kspt.inspace.model.utils.*;

import java.time.LocalDateTime;
import java.util.*;


public class Planet {

    private Position position;
    private int size = 200;
    private String name;

    private boolean updating = false;

    private BuildingDepartment buildingDepartment;
    private EnergyDepartment energyDepartment;
    private ResearchDepartment researchDepartment;
    private FleetDepartment fleetDepartment;
    private ResourceDepartment resourceDepartment;


    public Planet(String name, Position position) {
        updating = true;
        Galaxy.getInstance().addPlanet(this, position);

        this.name = name;
        this.position = position;

        researchDepartment = new ResearchDepartment(this);
        buildingDepartment = new BuildingDepartment(this);
        fleetDepartment = new FleetDepartment(this);
        energyDepartment = new EnergyDepartment(this);
        resourceDepartment = new ResourceDepartment(this);

        buildingDepartment.updateDependencies();
        researchDepartment.updateDependencies();
        fleetDepartment.updateDependencies();
        energyDepartment.updateDependencies();
        resourceDepartment.updateDependencies();

        updating = false;
    }

    public Planet(String name, Position position, int size) {
        this(name, position);
        this.size = size;
    }

    public Position getPosition() {
        return position;
    }

    public Resources getResources() {
        update();
        return resourceDepartment.getResources();
    }

    public int getEnergyProduction() {
        update();
        return energyDepartment.getTotalEnergyProduction();
    }

    public int getEnergyConsumption() {
        update();
        return energyDepartment.getTotalEnergyConsumption();
    }

    public int getEnergyLevel() {
        update();
        return energyDepartment.getEnergyLevel();
    }

    public double getProductionPower() {
        update();
        return energyDepartment.getProductionPower();
    }

    public int getNumberOfFields() {
        return size;
    }

    public int getNumberOfEmptyFields() {
        return size - getNumberOfFields();
    }

    public String getName() {
        return name;
    }

    public Building getBuilding(BuildingType type) {
        update();
        return buildingDepartment.getBuilding(type);
    }

    public Map<BuildingType, Building> getBuildings() {
        update();
        return buildingDepartment.getBuildings();
    }

    public Upgrade getCurrentBuildingUpgrade() {
        update();
        return buildingDepartment.getCurrentUpgrade();
    }

    public Research getResearch(ResearchType type) {
        update();
        return researchDepartment.getResearch(type);
    }

    public Map<ResearchType, Research> getResearches() {
        update();
        return researchDepartment.getResearches();
    }

    public Upgrade getCurrentResearchUpgrade() {
        update();
        return researchDepartment.getCurrentUpgrade();
    }

    public Construct getCurrentConstruct(){
        update();
        return fleetDepartment.getCurrentConstruct();
    }

    public Map<ShipType, Ship> getShips() {
        update();
        return fleetDepartment.getShips();
    }

    public Fleet getFleetOnPlanet() {
        update();
        return fleetDepartment.getMainFleet();
    }

    public void startMission(Mission mission) {
        update();
        fleetDepartment.startMission(mission);
        if(mission.getDestination() != this) {
            mission.getDestination().addExternalMission(mission);
        }
    }

    private void addExternalMission(Mission mission) {
        update();
        fleetDepartment.addExternalMission(mission);
    }

    public List<Mission> getMissions() {
        update();
        return fleetDepartment.getMissions();
    }

    public List<Mission> getExternalMissions() {
        update();
        return fleetDepartment.getExternalMissions();
    }

    private void updateResources(LocalDateTime now) {
        resourceDepartment.updateResources(now);
    }

    public void update() {
        if(updating) {
            return;
        }
        updating = true;
        List<TimeAction> actions = new ArrayList<>();

        actions.add(buildingDepartment.getCurrentUpgrade());
        actions.add(researchDepartment.getCurrentUpgrade());
        actions.add(fleetDepartment.getCurrentConstruct());
        actions.addAll(fleetDepartment.getMissions());
        actions.addAll(fleetDepartment.getExternalMissions());

        actions.removeIf(Objects::isNull);
        actions.removeIf(a -> a.getTime().compareTo(Time.now()) > 0);
        actions.sort((a1, a2) -> a1.getTime().compareTo(a2.getTime()));

        actions.forEach((a) -> a.addActionBeforeExecution(new Action() {
            @Override
            protected void onExecute() {
                updateResources(a.getTime());
            }
        }));

        actions.forEach((a) -> a.addActionAfterExecution(new Action() {
            @Override
            protected void onExecute() {
                energyDepartment.balanceEnergyConsumption();
            }
        }));

        actions.forEach(Action::execute);
        updateResources(Time.now());

        updating = false;
    }

    public void rename(String newName) {
        name = newName;
    }

}