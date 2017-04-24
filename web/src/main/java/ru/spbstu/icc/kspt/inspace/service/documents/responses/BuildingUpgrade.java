package ru.spbstu.icc.kspt.inspace.service.documents.responses;


import ru.spbstu.icc.kspt.inspace.api.ABuilding;
import ru.spbstu.icc.kspt.inspace.api.AUpgrade;
import ru.spbstu.icc.kspt.inspace.service.documents.responses.Building;

public class BuildingUpgrade {
    private final Building building;
    private final int levelAfterUpgrade;
    private final String startDate;
    private final String endDate;

    public BuildingUpgrade(AUpgrade upgrade) {
        this.building = new Building((ABuilding)upgrade.getUpgradable());
        levelAfterUpgrade = building.getLevel() + 1;
        endDate = upgrade.getTime().toString();
        startDate = upgrade.getTime().minusSeconds((long)building.getUpgradeDuration()).toString();
    }

    public Building getBuilding() {
        return building;
    }

    public int getLevelAfterUpgrade() {
        return levelAfterUpgrade;
    }


    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
