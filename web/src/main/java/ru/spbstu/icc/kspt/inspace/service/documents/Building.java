package ru.spbstu.icc.kspt.inspace.service.documents;

import ru.spbstu.icc.kspt.inspace.api.ABuilding;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;

public class Building {
    private final BuildingType type;
    private final  int level;

    public Building(ABuilding building) {
        type = building.getType();
        level = building.getLevel();
    }

    public BuildingType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }
}
