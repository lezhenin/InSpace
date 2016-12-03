package ru.spbstu.icc.kspt.inspace.api;


import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.utils.*;

public class Building extends Upgradable{
    private final ru.spbstu.icc.kspt.inspace.model.buildings.Building building;

    Building(ru.spbstu.icc.kspt.inspace.model.buildings.Building building) {
        super(building);
        this.building = building;
    }

    public BuildingType getType(){
        return building.getType();
    }

}
