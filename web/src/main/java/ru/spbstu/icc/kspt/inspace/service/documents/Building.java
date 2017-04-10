package ru.spbstu.icc.kspt.inspace.service.documents;

import ru.spbstu.icc.kspt.inspace.api.ABuilding;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;

public class Building {
    private final BuildingType type;
    private final int level;
    private final Resources upgradeCost;
    private final int upgradeDuration;

    public Building(ABuilding building) {
        type = building.getType();
        level = building.getLevel();
        upgradeCost = new Resources(building.getUpgradeCost());
        upgradeDuration = (int) building.getUpgradeDuration().getSeconds();

    }

    public BuildingType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public Resources getUpgradeCost() {
        return upgradeCost;
    }

    public int getUpgradeDuration() {
        return upgradeDuration;
    }
}
