package ru.spbstu.icc.kspt.inspace.model.buildings;


public enum BuildingType {
    FACTORY(60, 50, 0, 3000),
    CRYSTAL_MINE(40, 35, 0, 2700),
    METAL_MINE(55, 50, 0, 2500),
    DEUTERIUM_MINE(62, 58, 0, 2300),
    POWER_STATION (85, 65, 0, 2300),
    LABORATORY(50, 60, 0, 2800),
    SHIPYARD(55, 55, 0, 2900);

    private final int METAL_COST_VALUE;
    private final int CRYSTAL_COST_VALUE;
    private final int DEUTERIUM_COST_VALUE;
    private final int UPGRADE_SPEED_VALUE;


    BuildingType(int metalCostValue, int crystalCostValue, int deuteriumCostValue, int upgradeSpeedValue) {
        this.METAL_COST_VALUE = metalCostValue;
        this.CRYSTAL_COST_VALUE = crystalCostValue;
        this.DEUTERIUM_COST_VALUE = deuteriumCostValue;
        this.UPGRADE_SPEED_VALUE = upgradeSpeedValue;
    }

    public int getMetalCostValue() {
        return METAL_COST_VALUE;
    }

    public int getCrystalCostValue() {
        return CRYSTAL_COST_VALUE;
    }

    public int getDeuteriumCostValue() {
        return DEUTERIUM_COST_VALUE;
    }

    public int getUpgradeSpeedValue() {
        return UPGRADE_SPEED_VALUE;
    }
}

