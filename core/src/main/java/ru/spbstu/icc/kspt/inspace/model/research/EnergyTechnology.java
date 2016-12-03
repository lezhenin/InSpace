package ru.spbstu.icc.kspt.inspace.model.research;

import ru.spbstu.icc.kspt.inspace.model.Resources;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.buildings.Laboratory;

import java.time.Duration;


public class EnergyTechnology extends Research {

    //TODO подумать об вынесении заинлайненых констант в переменные (1.6 например)
    private static final int METAL_COST_VALUE = 40;
    private static final int CRYSTAL_COST_VALUE = 65;
    private static final int DEUTERIUM_COST_VALUE = 65;
    private static final int UPGRADE_SPEED_VALUE = 1000;

    private Laboratory laboratory;

    public EnergyTechnology(ResearchDepartment department) {
        super(department, ResearchType.ENERGY);
    }

    @Override
    void updateDependencies() {
        laboratory = (Laboratory)department.getBuilding(BuildingType.LABORATORY);
    }

    @Override
    public Duration getUpgradeDuration() {
        Resources cost = getUpgradeCost();
        double summaryCost = cost.getMetal() + cost.getCrystals() + cost.getDeuterium();
        double hours = summaryCost / (UPGRADE_SPEED_VALUE * (1 + laboratory.getLevel()));
        return Duration.ofSeconds(Math.round(hours * 3600));
    }

    @Override
    public Resources getUpgradeCost() {
        int metal = (int)Math.round(METAL_COST_VALUE * Math.pow(1.6, level));
        int crystal = (int)Math.round(CRYSTAL_COST_VALUE * Math.pow(1.6, level));
        int deuterium = (int)Math.round(DEUTERIUM_COST_VALUE * Math.pow(1.6, level));
        return new Resources(metal, crystal, deuterium);
    }
}
