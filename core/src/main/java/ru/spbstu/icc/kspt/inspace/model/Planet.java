package ru.spbstu.icc.kspt.inspace.model;

import ru.spbstu.icc.kspt.inspace.api.APlanet;
import ru.spbstu.icc.kspt.inspace.model.buildings.*;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingDepartment;
import ru.spbstu.icc.kspt.inspace.model.energy.EnergyDepartment;
import ru.spbstu.icc.kspt.inspace.model.exception.CapacityExcessException;
import ru.spbstu.icc.kspt.inspace.model.exception.FleetDetachException;
import ru.spbstu.icc.kspt.inspace.model.exception.PlanetDoesntExist;
import ru.spbstu.icc.kspt.inspace.model.fleet.Fleet;
import ru.spbstu.icc.kspt.inspace.model.fleet.FleetDepartment;
import ru.spbstu.icc.kspt.inspace.model.fleet.ShipModel;
import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;
import ru.spbstu.icc.kspt.inspace.model.fleet.missions.Attack;
import ru.spbstu.icc.kspt.inspace.model.fleet.missions.Mission;
import ru.spbstu.icc.kspt.inspace.model.fleet.missions.Transportation;
import ru.spbstu.icc.kspt.inspace.model.research.Research;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchDepartment;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;
import ru.spbstu.icc.kspt.inspace.model.resources.ResourceDepartment;
import ru.spbstu.icc.kspt.inspace.model.resources.Resources;
import ru.spbstu.icc.kspt.inspace.model.utils.*;

import java.time.LocalDateTime;
import java.util.*;


public class Planet implements APlanet {

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

        buildingDepartment = new BuildingDepartment(this);
        researchDepartment = new ResearchDepartment(this);
        fleetDepartment = new FleetDepartment(this);
        energyDepartment = new EnergyDepartment(this);
        resourceDepartment = new ResourceDepartment(this);

        updating = false;
    }

    public Planet(String name, Position position, int size) {
        this(name, position);
        this.size = size;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Resources getResources() {
        update();
        return resourceDepartment.getResources();
    }

    @Override
    public int getEnergyProduction() {
        update();
        return energyDepartment.getTotalEnergyProduction();
    }

    @Override
    public int getEnergyConsumption() {
        update();
        return energyDepartment.getTotalEnergyConsumption();
    }

    @Override
    public int getEnergyLevel() {
        update();
        return energyDepartment.getEnergyLevel();
    }

    @Override
    public double getProductionPower() {
        update();
        return energyDepartment.getProductionPower();
    }

    @Override
    public int getNumberOfFields() {
        return size;
    }

    @Override
    public int getNumberOfEmptyFields() {
        return size - buildingDepartment.getFields();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Building getBuilding(BuildingType type) {
        update();
        return buildingDepartment.getBuilding(type);
    }

    @Override
    public Map<BuildingType, Building> getBuildings() {
        update();
        return buildingDepartment.getBuildings();
    }

    @Override
    public Upgrade getCurrentBuildingUpgrade() {
        update();
        return buildingDepartment.getCurrentUpgrade();
    }

    @Override
    public Research getResearch(ResearchType type) {
        update();
        return researchDepartment.getResearch(type);
    }

    @Override
    public Map<ResearchType, Research> getResearches() {
        update();
        return researchDepartment.getResearches();
    }

    @Override
    public Upgrade getCurrentResearchUpgrade() {
        update();
        return researchDepartment.getCurrentUpgrade();
    }

    @Override
    public Construct getCurrentConstruct(){
        update();
        return fleetDepartment.getCurrentConstruct();
    }

    @Override
    public Map<ShipType, ShipModel> getShips() {
        update();
        return fleetDepartment.getShips();
    }

    @Override
    public Fleet getFleetOnPlanet() {
        update();
        return fleetDepartment.getMainFleet();
    }

    @Override
    public void startAttack(Position destination, Map<ShipType, Integer> numbersOfShips)
            throws FleetDetachException, PlanetDoesntExist {
        Fleet fleet = getFleetOnPlanet().detachFleet(numbersOfShips);
        startMission(new Attack(this, Galaxy.getInstance().getPlanet(destination), fleet));
    }

    @Override
    public void startTransportation(Position destination, Map<ShipType, Integer> numbersOfShips, int metal, int crystal, int deuterium)
            throws FleetDetachException, CapacityExcessException, PlanetDoesntExist {
        Fleet fleet = getFleetOnPlanet().detachFleet(numbersOfShips);
        Resources resources = new Resources(metal, crystal, deuterium);
        fleet.putResources(resources);
        startMission(new Transportation(this, Galaxy.getInstance().getPlanet(destination), fleet));
    }

    public void startMission(Mission mission) {
        update();
        fleetDepartment.addMission(mission);
        if(mission.getDestination() != this) {
            mission.getDestination().addExternalMission(mission);
        }
    }

    private void addExternalMission(Mission mission) {
        update();
        fleetDepartment.addExternalMission(mission);
    }

    @Override
    public List<Mission> getMissions() {
        update();
        return fleetDepartment.getMissions();
    }

    @Override
    public List<Mission> getExternalMissions() {
        update();
        return fleetDepartment.getExternalMissions();
    }

    private void updateResources(LocalDateTime now) {
        resourceDepartment.updateResources(now);
    }

    @Override
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

    @Override
    public void rename(String newName) {
        name = newName;
    }

}