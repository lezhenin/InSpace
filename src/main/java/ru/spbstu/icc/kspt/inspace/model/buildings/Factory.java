package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.Resources;

import java.time.Duration;

public class Factory extends Building {

    private static final int METAL_COST_VALUE = 60;
    private static final int CRYSTAL_COST_VALUE = 50;

    private final static int UPGRADE_SPEED_VALUE = 3000;


    public Factory(BuildingDepartment department)
    {
        super(department);
    }

    @Override
    public Duration getUpgradeDuration() {
        Resources cost = getUpgradeCost();
        double summaryCost = cost.getMetal() + cost.getCrystals() + cost.getMetal();
        double hours = summaryCost / (UPGRADE_SPEED_VALUE * (1 + getLevel()));
        return Duration.ofSeconds(Math.round(hours * 3600));
    }

    @Override
    public Resources getUpgradeCost() {
        int metal = (int)Math.round(METAL_COST_VALUE * Math.pow(1.6, getLevel()));
        int crystal = (int)Math.round(CRYSTAL_COST_VALUE * Math.pow(1.6, getLevel()));
        return new Resources(metal, crystal, 0);
    }
}
