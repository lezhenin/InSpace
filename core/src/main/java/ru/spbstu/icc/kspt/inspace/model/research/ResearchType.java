package ru.spbstu.icc.kspt.inspace.model.research;

public enum ResearchType {
    ENERGY(45, 65, 50, 1000),
    LASER(40, 65, 65, 1100);


    protected final int METAL_COST_VALUE;
    protected final int CRYSTAL_COST_VALUE;
    protected final int DEUTERIUM_COST_VALUE;
    protected final int UPGRADE_SPEED_VALUE;

    ResearchType(int metalCostValue, int crystalCostValue, int deuteriumCostValue, int upgradeSpeedValue) {
        this.METAL_COST_VALUE = metalCostValue;
        this.CRYSTAL_COST_VALUE = crystalCostValue;
        this.DEUTERIUM_COST_VALUE = deuteriumCostValue;
        this.UPGRADE_SPEED_VALUE = upgradeSpeedValue;
    }
}