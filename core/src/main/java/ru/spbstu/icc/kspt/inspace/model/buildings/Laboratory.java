package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.Resources;

import java.time.Duration;

public class Laboratory extends Building {

    private static final int METAL_COST_VALUE = 50;
    private static final int CRYSTAL_COST_VALUE = 60;
    private final static int UPGRADE_SPEED_VALUE = 2900;

    private Factory factory;

    public Laboratory(BuildingDepartment department) {
        super(department, BuildingType.LABORATORY);
    }

    @Override
    public void updateDependencies() {
        factory = (Factory)department.getBuilding(BuildingType.FACTORY);
    }

    @Override
    public Duration getUpgradeDuration() {
        Resources cost = getUpgradeCost();
        double summaryCost = cost.getMetal() + cost.getCrystals() + cost.getMetal();
        double hours = summaryCost / (UPGRADE_SPEED_VALUE * (1 + factory.getLevel()));
        return Duration.ofSeconds(Math.round(hours * 3600));
    }

    @Override
    public Resources getUpgradeCost() {
        //TODO возможно вынести 1.6 в переменную.
        int metal = (int)Math.round(METAL_COST_VALUE * Math.pow(1.6, getLevel()));
        int crystal = (int)Math.round(CRYSTAL_COST_VALUE * Math.pow(1.6, getLevel()));
        return new Resources(metal, crystal, 0);
    }
}
