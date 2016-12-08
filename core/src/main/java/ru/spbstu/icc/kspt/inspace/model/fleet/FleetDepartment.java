package ru.spbstu.icc.kspt.inspace.model.fleet;

import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.resources.Resources;
import ru.spbstu.icc.kspt.inspace.model.buildings.Building;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.exception.ConstructException;
import ru.spbstu.icc.kspt.inspace.model.fleet.missions.Mission;
import ru.spbstu.icc.kspt.inspace.model.research.Research;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;
import ru.spbstu.icc.kspt.inspace.model.utils.Action;
import ru.spbstu.icc.kspt.inspace.model.utils.Construct;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class FleetDepartment {

    private Planet planet;
    private Map<ShipType, Ship> ships = new EnumMap<>(ShipType.class);
    private Fleet mainFleet = new Fleet(this);
    private List<Mission> missions = new ArrayList<>();
    private List<Mission> externalMissions = new ArrayList<>();

    private Construct currentConstruct;


    public FleetDepartment(Planet planet) {
        this.planet = planet;
        ships.put(ShipType.FIGHTER, new Ship(ShipType.FIGHTER, this));
        ships.put(ShipType.SMALL_CARGO, new Ship(ShipType.SMALL_CARGO, this));
    }

    void startConstruction(Construct construct) throws ConstructException {
        Resources cost = construct.getConstructable().getConstructCost();
        if (planet.getResources().compareTo(cost) < 0 || currentConstruct != null) {
            throw new ConstructException(construct.getConstructable());
        }
        currentConstruct = construct;
        planet.getResources().takeResources(cost);
        ShipType type = ((Ship)construct.getConstructable()).getType();
        construct.addActionAfterExecution(new Action() {
            @Override
            protected void onExecute() {
                mainFleet.addShips(type, construct.getNumberOfUnits());
                FleetDepartment.this.currentConstruct = null;
            }
        });
    }

    public void startMission(Mission mission) {
        missions.add(mission);
        mission.addActionBeforeExecution(new Action() {
            @Override
            protected void onExecute() {
                missions.remove(mission);
            }
        });
    }

    public void addExternalMission(Mission mission) {
        externalMissions.add(mission);
        mission.addActionAfterExecution(new Action() {
            @Override
            protected void onExecute() {
                externalMissions.remove(mission);
            }
        });
    }

    public List<Mission> getExternalMissions() {
        return externalMissions;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public Construct getCurrentConstruct() {
        return currentConstruct;
    }

    public Map<ShipType, Ship> getShips() {
        return ships;
    }

    public Fleet getMainFleet() {
        return mainFleet;
    }

    Research getResearch(ResearchType type) {
        return planet.getResearch(type);
    }

    Building getBuilding(BuildingType type) {
        return planet.getBuilding(type);
    }

    public void updateDependencies() {
        ships.values().forEach(Ship::updateDependencies);
    }

    public Planet getPlanet() {
        return planet;
    }

}
