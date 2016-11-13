package ru.spbstu.icc.kspt.inspace.model.fleet;

import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.buildings.Building;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.research.Research;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;

import java.util.EnumMap;
import java.util.Map;

public class FleetDepartment {

    private Planet planet;
    private Map<ShipType, Ship> ships = new EnumMap<>(ShipType.class);

    public FleetDepartment(Planet planet) {
        ships.put(ShipType.FIGHTER, new Ship(ShipType.FIGHTER, this));
        ships.put(ShipType.SMALL_CARGO, new Ship(ShipType.SMALL_CARGO, this));
        this.planet = planet;
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
}
