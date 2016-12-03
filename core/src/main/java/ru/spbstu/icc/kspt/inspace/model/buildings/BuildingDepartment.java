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
    private List<Mine> mines = new ArrayList<>();

    public BuildingDepartment(Planet planet) {
        super(planet);

        buildings.put(BuildingType.FACTORY, new Factory(this));
        buildings.put(BuildingType.LABORATORY, new Laboratory(this));
        buildings.put(BuildingType.SHIPYARD, new Shipyard(this));
        buildings.put(BuildingType.CRYSTAL_MINE, new CrystalMine(this));
        buildings.put(BuildingType.DEUTERIUM_MINE, new DeuteriumMine(this));
        buildings.put(BuildingType.METAL_MINE, new MetalMine(this));
        buildings.put(BuildingType.POWER_STATION, new PowerStation(this));

        mines.add((Mine)buildings.get(BuildingType.METAL_MINE));
        mines.add((Mine)buildings.get(BuildingType.CRYSTAL_MINE));
        mines.add((Mine)buildings.get(BuildingType.DEUTERIUM_MINE));
    }

    public void updateDependencies() {
        buildings.values().forEach(Building::updateDependencies);
    }

    @Override
    protected boolean canBeUpgraded(Upgradable building) {
        return (super.canBeUpgraded(building) && planet.getNumberOfFields() > occupiedFields);
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

    public List<Mine> getMines() {
        return mines;
    }

    protected void updatePlanet() {
        super.updatePlanet();
    }

}
