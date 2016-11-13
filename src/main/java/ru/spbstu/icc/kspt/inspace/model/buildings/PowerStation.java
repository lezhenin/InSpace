package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.Resources;
import ru.spbstu.icc.kspt.inspace.model.energy.EnergyProducer;
import ru.spbstu.icc.kspt.inspace.model.research.EnergyTechnology;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;

import java.time.Duration;


public class PowerStation extends Building implements EnergyProducer {

    private static final int ENERGY_PRODUCTION_VALUE = 100;
    private static final int METAL_COST_VALUE = 85;
    private static final int CRYSTAL_COST_VALUE = 65;
    private static final int UPGRADE_SPEED_VALUE = 2300;

    private Factory factory;
    private EnergyTechnology energyTechnology;

    public PowerStation(BuildingDepartment department) {
        super(department);
    }

    @Override
    public void updateDependencies() {
        factory = (Factory)department.getBuilding(BuildingType.FACTORY);
        energyTechnology = (EnergyTechnology)department.getResearch(ResearchType.ENERGY);
    }

    public int getEnergyProduction() {
        return (int)Math.round(ENERGY_PRODUCTION_VALUE * getLevel() * Math.pow(1.3, getLevel() - 1)
                 * (1 + energyTechnology.getLevel() * 0.05));
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
        int metal = (int)Math.round(METAL_COST_VALUE * Math.pow(1.5, getLevel()));
        int crystal = (int)Math.round(CRYSTAL_COST_VALUE * Math.pow(1.5, getLevel()));
        return new Resources(metal, crystal, 0);
    }
}
