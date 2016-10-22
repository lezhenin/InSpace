package ru.spbstu.icc.kspt.inspace.model.departments;

import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.buildings.*;
import ru.spbstu.icc.kspt.inspace.model.utils.Time;

import java.util.*;

public class BuildingDepartment {

    private Planet planet;
    private BuildingUpgrade upgrading;
    private int occupiedFields = 0;

    private Map<Planet.BuildingType, Building> buildings = new EnumMap<>(Planet.BuildingType.class);
    private List<Mine> mines = new ArrayList<>();

    public BuildingDepartment(Planet planet) {
        this.planet = planet;

        Factory factory = new Factory(this);

        buildings.put(Planet.BuildingType.FACTORY, factory);
        buildings.put(Planet.BuildingType.CRYSTAL_MINE, new CrystalMine(this, factory));
        buildings.put(Planet.BuildingType.DEUTERIUM_MINE, new DeuteriumMine(this, factory));
        buildings.put(Planet.BuildingType.METAL_MINE, new MetalMine(this, factory));

        mines.add((Mine)buildings.get(Planet.BuildingType.METAL_MINE));
        mines.add((Mine)buildings.get(Planet.BuildingType.CRYSTAL_MINE));
        mines.add((Mine)buildings.get(Planet.BuildingType.DEUTERIUM_MINE));

        PowerStation station = new PowerStation(this, factory);
        mines.forEach(station::addConsumer);
        buildings.put(Planet.BuildingType.POWER_STATION, station);
    }

    public boolean checkUpgradability(Building building) {
        return (building.getUpgradeCost().compareTo(planet.getResources()) == -1 &&
                !isBusy() && planet.getSize() - occupiedFields > 0);
    }

    public boolean isBusy() {
        updateBuildings();
        return upgrading != null;
    }

    public void startUpgrade(BuildingUpgrade upgrading) {

        Building building = upgrading.getUpgradable();

        if (!checkUpgradability(building)){
            //TODO exception
            return;
        }

        planet.getResources().getResources(building.getUpgradeCost());
        this.upgrading = upgrading;
    }

    public BuildingUpgrade getCurrentUpgrade() {
        updateBuildings();
        return upgrading;
    }

    public int getFields() {
        updateBuildings();
        return occupiedFields;
    }

    public Building getBuilding(Planet.BuildingType type) {
        updateBuildings();
        return buildings.get(type);
    }

    public Set<Map.Entry<Planet.BuildingType, Building>> getBuildings() {
        updateBuildings();
        return buildings.entrySet();
    }

    public List<Mine> getMines() {
        updateBuildings();
        return mines;
    }

   private void updateBuildings() {
        if (upgrading != null && upgrading.getTime().compareTo(Time.now()) <= 0 ){
            upgrading.execute(Time.now());
            upgrading = null;
            occupiedFields++;
        }
    }
}
