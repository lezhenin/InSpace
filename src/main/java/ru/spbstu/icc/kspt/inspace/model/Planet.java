package ru.spbstu.icc.kspt.inspace.model;

import ru.spbstu.icc.kspt.inspace.model.buildings.*;

import java.util.*;


public class Planet {

    public enum BuildingType {
        FACTORY,
        CRYSTAL_MINE,
        METAL_MINE,
        DEUTERIUM_MINE,
        POWER_STATION
    }

    private Map<BuildingType, Building> buildings = new EnumMap<>(BuildingType.class);
    private List<Mine> mines = new ArrayList<>();

    private int size = 200;

    private String name;

    private Resources resources;

    public Planet(String name) {
        this.name = name;
        this.resources = new Resources(0,0,0);

        Factory factory = new Factory(this);
        buildings.put(BuildingType.FACTORY, factory);
        buildings.put(BuildingType.CRYSTAL_MINE, new CrystalMine(factory));
        buildings.put(BuildingType.DEUTERIUM_MINE, new DeuteriumMine(factory));
        buildings.put(BuildingType.METAL_MINE, new MetalMine(factory));

        mines.add((Mine)buildings.get(BuildingType.METAL_MINE));
        mines.add((Mine)buildings.get(BuildingType.CRYSTAL_MINE));
        mines.add((Mine)buildings.get(BuildingType.DEUTERIUM_MINE));

        PowerStation station = new PowerStation(factory);
        mines.forEach(station::addConsumer);
        buildings.put(BuildingType.POWER_STATION, station);

    }

    public Planet(String name, int size) {
        this(name);
        this.size = size;
    }

    public Resources getResources() {
        return resources;
    }

    public int getEnergyLevel() {
        return ((PowerStation) (getBuilding(BuildingType.POWER_STATION))).getEnergyLevel();
    }

    public int getSize() {
        return size;
    }

    public int getEmptyFields() {
        return size - ((Factory)getBuilding(BuildingType.FACTORY)).getFields();
    }

    public String getName() {
        return name;
    }

    public Building getBuilding(BuildingType type) {
        updateBuildings();
        return buildings.get(type);
    }

    public Set<Map.Entry<BuildingType, Building>> getBuildings() {
        updateBuildings();
        return buildings.entrySet();
    }

    public BuildingUpgrade getCurrentBuildingUpgrade() {
        updateBuildings();
        Factory factory = (Factory)getBuilding(BuildingType.FACTORY);
        return factory.getCurrentUpgrade();
    }

    public void update() {

        for (Mine mine: mines) {
            resources.addResources(mine.getProduction());
        }
        updateBuildings();
    }

    public void rename(String newName) {
        name = newName;
    }

    private void updateBuildings() {
        ((Factory)buildings.get(BuildingType.FACTORY)).updateBuildings();
    }
}
