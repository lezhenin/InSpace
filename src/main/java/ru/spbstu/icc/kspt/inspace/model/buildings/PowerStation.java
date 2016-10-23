package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.Resources;
import ru.spbstu.icc.kspt.inspace.model.energy.EnergyProducer;

import java.time.Duration;

public class PowerStation extends Building implements EnergyProducer {

    public static final int ENERGY_PRODUCTION_VALUE = 100;

    private static final int METAL_COST_VALUE = 85;
    private static final int CRYSTAL_COST_VALUE = 65;

    private static final int UPGRADE_SPEED_VALUE = 2300;

    private Factory factory;

    public PowerStation(BuildingDepartment department, Factory factory) {
        super(department);
        this.factory = factory;
    }

    public int getEnergyProduction() {
        return ENERGY_PRODUCTION_VALUE * level * (int)Math.pow(1.3, level);
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
}
