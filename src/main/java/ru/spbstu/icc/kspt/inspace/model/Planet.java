package ru.spbstu.icc.kspt.inspace.model;

import org.mockito.internal.verification.checkers.MissingInvocationChecker;
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
import ru.spbstu.icc.kspt.inspace.model.utils.Action;
import ru.spbstu.icc.kspt.inspace.model.utils.Time;
import ru.spbstu.icc.kspt.inspace.model.utils.TimeAction;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgrade;

import java.time.LocalDateTime;
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
        this.position = position;
        this.resources = new Resources(0,0,0);

        researchDepartment = new ResearchDepartment(this);
        buildingDepartment = new BuildingDepartment(this);
        buildingDepartment.updateDependencies();
        researchDepartment.updateDependencies();
        fleetDepartment = new FleetDepartment(this);
        energyDepartment = new EnergyDepartment(this);

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
        return resources;
    }

    public void balanceEnergyConsumption() {
        update();
        energyDepartment.balanceEnergyConsumption();
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

    public Set<Map.Entry<BuildingType, Building>> getBuildings() {
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

    public Set<Map.Entry<ResearchType, Research>> getResearches() {
        update();
        return researchDepartment.getResearches();
    }

    public Upgrade getCurrentResearchUpgrade() {
        update();
        return researchDepartment.getCurrentUpgrade();
    }

    public Set<Map.Entry<ShipType, Ship>> getShips() {
        update();
        return fleetDepartment.getShips().entrySet();
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
        for (Mine mine: buildingDepartment.getMines()) {
            resources.addResources(mine.getProduction(now));
        }
    }


    public void update() {
        List<TimeAction> actions = new ArrayList<>();

        try {
            actions.add(buildingDepartment.getCurrentUpgrade());
            actions.add(researchDepartment.getCurrentUpgrade());
            actions.add(fleetDepartment.getCurrentConstruction());
            actions.addAll(fleetDepartment.getMissions());
        }catch (NullPointerException e) {
            return;
        }

        actions.removeIf(Objects::isNull);
        actions.removeIf(a -> a.getTime().compareTo(Time.now()) > 0);
        actions.sort((a1, a2) -> a1.getTime().compareTo(a2.getTime()));
        actions.forEach((a) -> a.addActionBeforeExecution(new Action() {
            private TimeAction action = a;
            @Override
            protected void onExecute() {
                updateResources(action.getTime());
            }
        }));
        actions.forEach(Action::execute);
        updateResources(Time.now());
    }

    public void rename(String newName) {
        name = newName;
    }

}