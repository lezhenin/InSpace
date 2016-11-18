package ru.spbstu.icc.kspt.inspace.model.fleet;

import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.buildings.Building;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.research.Research;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;
import ru.spbstu.icc.kspt.inspace.model.utils.Action;
import ru.spbstu.icc.kspt.inspace.model.utils.Construct;
import ru.spbstu.icc.kspt.inspace.model.utils.Constructable;
import ru.spbstu.icc.kspt.inspace.model.utils.Time;

import java.awt.datatransfer.FlavorListener;
import java.util.EnumMap;
import java.util.Map;

public class FleetDepartment {

    private Planet planet;
    private Map<ShipType, Ship> ships = new EnumMap<>(ShipType.class);
    private Fleet mainFleet = new Fleet(this);

    private Construct currentConstruction;


    public FleetDepartment(Planet planet) {
        this.planet = planet;
        ships.put(ShipType.FIGHTER, new Ship(ShipType.FIGHTER, this));
        ships.put(ShipType.SMALL_CARGO, new Ship(ShipType.SMALL_CARGO, this));
    }

    void startConstruction(Construct construct) {
        currentConstruction = construct;
        ShipType type = ((Ship)construct.getConstructable()).getType();
        construct.addActionAfterExecution(new Action() {
            ShipType innerType = type;
            Construct innerConstruct = construct;
            @Override
            protected void onExecute() {
                mainFleet.addShips(innerType, innerConstruct.getNumberOfUnits());
            }
        });
    }

    public Construct getCurrentConstruction() {
        return currentConstruction;
    }

    public Map<ShipType, Ship> getShips() {
        return ships;
    }

    public Fleet getFleet() {
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

    public void update() {
        if (currentConstruction != null && currentConstruction.getTime().compareTo(Time.now()) <= 0) {
            currentConstruction.execute();
            currentConstruction = null;
        }
    }
}
