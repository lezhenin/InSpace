package ru.spbstu.icc.kspt.inspace.model.fleet;

import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.research.Research;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;

import java.util.EnumMap;
import java.util.Map;

public class FleetDepartment {

    private Planet planet;
    private Map<ShipType, Ship> ships = new EnumMap<>(ShipType.class);

    public FleetDepartment(Planet planet) {
        ships.put(ShipType.FIGHTER, new Fighter(this));
        this.planet = planet;
    }

    Research getResearch(ResearchType type) {
        return planet.getResearch(type);
    }

    public void updateDependencies() {
        ships.values().forEach(Ship::updateDependencies);
    }
}
