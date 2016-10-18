package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.Resources;

import java.time.Duration;

public class MetalMine extends Mine {

    private static final int PRODUCTION_SPEED_VALUE = 30;

    private static final int METAL_COST_VALUE = 40;
    private static final int CRYSTAL_COST_VALUE = 35;

    private static final int UPGRADE_SPEED_VALUE = 2700;

    public MetalMine(Factory factory) {
        super(factory);
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
        double metal = PRODUCTION_SPEED_VALUE * (level+1) * Math.pow(1.1, level);
        metal *= duration.getSeconds()/3600.0;
        return new Resources((int)Math.round(metal), 0, 0);
    }
}
