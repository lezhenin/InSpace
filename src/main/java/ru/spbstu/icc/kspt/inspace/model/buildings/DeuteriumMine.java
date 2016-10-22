package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.departments.BuildingDepartment;
import ru.spbstu.icc.kspt.inspace.model.Resources;

import java.time.Duration;

public class DeuteriumMine extends Mine {

    private static final int PRODUCTION_SPEED_VALUE = 20;

    private static final int METAL_COST_VALUE = 62;
    private static final int CRYSTAL_COST_VALUE = 58;
    private static final int UPGRADE_SPEED_VALUE = 2300;

    public DeuteriumMine(BuildingDepartment department, Factory factory) {
        super(department, factory);
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
        int metal = (int)Math.round(METAL_COST_VALUE * Math.pow(1.5, level));
        int crystal = (int)Math.round(CRYSTAL_COST_VALUE * Math.pow(1.5, level));
        return new Resources(metal, crystal, 0);
    }

    @Override
    protected Resources getProductionPerPeriod(Duration duration) {
        double deuterium = PRODUCTION_SPEED_VALUE * (level+1) * Math.pow(1.1, level);
        deuterium *= duration.getSeconds()/3600.0;
        return new Resources(0, 0, (int)Math.round(deuterium));
    }

    @Override
    public int getEnergyConsumption() {
        return 0;
    }
}
