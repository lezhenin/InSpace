package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.exception.UpgradeException;
import ru.spbstu.icc.kspt.inspace.model.research.Research;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;
import ru.spbstu.icc.kspt.inspace.model.utils.*;

import java.util.*;

public class BuildingDepartment extends UpgradeDepartment {

    private int occupiedFields = 0;

    private Map<BuildingType, Building> buildings = new EnumMap<>(BuildingType.class);

    public BuildingDepartment(Planet planet) {
        super(planet);
        for (BuildingType type: BuildingType.values()) {
            buildings.put(type, new Building(this, type));
        }

    }

    @Override
    protected boolean additionalCheck() {
        return planet.getNumberOfFields() > occupiedFields;
    }

    protected void startUpgrade(Upgrade upgrade) throws UpgradeException {
        upgrade.addActionAfterExecution(new Action() {
            @Override
            protected void onExecute() {
                occupiedFields ++;
            }
        });
        super.startUpgrade(upgrade);
    }

    public int getFields() {
        return occupiedFields;
    }

    Research getResearch(ResearchType type) {
      return planet.getResearch(type);
    }

    public Building getBuilding(BuildingType type) {
        return buildings.get(type);
    }

    public Map<BuildingType, Building> getBuildings() {
        return buildings;
    }

    protected void updatePlanet() {
        super.updatePlanet();
    }

}
