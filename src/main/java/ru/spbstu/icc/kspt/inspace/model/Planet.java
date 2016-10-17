package ru.spbstu.icc.kspt.inspace.model;

import ru.spbstu.icc.kspt.inspace.model.buildings.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Planet {

    public enum BuildingType {
        FACTORY,
        CRYSTAL_MINE,
        METAL_MINE,
        DEUTERIUM_MINE,
    }

    private Map<BuildingType, Building> buildings = new HashMap<>();
    private List<Mine> mines = new ArrayList<>();

    private int size;

    private Resources resources;

    public Planet() {
        this.resources = new Resources(0,0,0);

        Factory factory = new Factory(this);
        buildings.put(BuildingType.FACTORY, factory);
        buildings.put(BuildingType.CRYSTAL_MINE, new CrystalMine(factory));
        buildings.put(BuildingType.DEUTERIUM_MINE, new DeuteriumMine(factory));
        buildings.put(BuildingType.METAL_MINE, new MetalMine(factory));

        mines.add((Mine)buildings.get(BuildingType.METAL_MINE));
        mines.add((Mine)buildings.get(BuildingType.CRYSTAL_MINE));
        mines.add((Mine)buildings.get(BuildingType.DEUTERIUM_MINE));

    }

    public Planet(int size) {
        this();
        this.size = size;
    }

    public Resources getResources() {
        return resources;
    }

    public int getSize() {
        return size;
    }

    public Building getBuilding(BuildingType type) {
        return buildings.get(type);
    }

    public void update() {
        for (Mine mine: mines) {
            resources.addResources(mine.getProduction());
        }
    }
}
