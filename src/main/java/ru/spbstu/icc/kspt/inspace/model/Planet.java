package ru.spbstu.icc.kspt.inspace.model;

import ru.spbstu.icc.kspt.inspace.model.buildings.*;
import ru.spbstu.icc.kspt.inspace.model.departments.BuildingDepartment;

import java.util.*;


public class Planet {

    public enum BuildingType {
        FACTORY,
        CRYSTAL_MINE,
        METAL_MINE,
        DEUTERIUM_MINE,
        POWER_STATION
    }

    private int size = 200;
    private String name;
    private Resources resources;
    private BuildingDepartment buildingDepartment;


    public Planet(String name) {
        this.name = name;
        this.resources = new Resources(0,0,0);

        buildingDepartment = new BuildingDepartment(this);
    }

    public Planet(String name, int size) {
        this(name);
        this.size = size;
    }

    public Resources getResources() {
        update();
        return resources;
    }

    public int getEnergyLevel() {
        return ((PowerStation)(getBuilding(BuildingType.POWER_STATION))).getEnergyLevel();
    }

    public int getSize() {
        return size;
    }

    public int getEmptyFields() {
        return size - buildingDepartment.getFields();
    }

    public String getName() {
        return name;
    }

    public Building getBuilding(BuildingType type) {
        return buildingDepartment.getBuilding(type);
    }

    public Set<Map.Entry<BuildingType, Building>> getBuildings() {
        return buildingDepartment.getBuildings();
    }

    public BuildingUpgrade getCurrentBuildingUpgrade() {
        return buildingDepartment.getCurrentUpgrade();
    }

    public void update() {

        for (Mine mine: buildingDepartment.getMines()) {
            resources.addResources(mine.getProduction());
        }
    }

    public void rename(String newName) {
        name = newName;
    }

}
