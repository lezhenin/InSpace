package ru.spbstu.icc.kspt.inspace.model.energy;

import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.buildings.Building;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.research.Research;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;
import ru.spbstu.icc.kspt.inspace.model.utils.Department;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgradable;

import java.util.ArrayList;
import java.util.List;

public class EnergyDepartment extends Department{

    private static final int POWER_STATION_PRODUCING = 100;
    private static final int METAL_MINE_CONSUMPTION = 30;
    private static final int CRYSTAL_MINE_CONSUMPTION = 40;
    private static final int DEUTERIUM_MINE_CONSUMPTION = 45;

    private static final double CONSUMPTION_GROW_VALUE = 1.4;
    private static final double PRODUCTION_GROW_VALUE = 1.35;

    private List<EnergyConsumer> consumers = new ArrayList<>();
    private List<EnergyProducer> producers = new ArrayList<>();
    private Research energyTechnology;
    private double power = 1;

    public EnergyDepartment(Planet planet) {
        super(planet);
        producers.add(createEnergyProducer(planet.getBuilding(BuildingType.POWER_STATION), POWER_STATION_PRODUCING));
        consumers.add(createEnergyConsumer(planet.getBuilding(BuildingType.METAL_MINE), METAL_MINE_CONSUMPTION));
        consumers.add(createEnergyConsumer(planet.getBuilding(BuildingType.CRYSTAL_MINE), CRYSTAL_MINE_CONSUMPTION));
        consumers.add(createEnergyConsumer(planet.getBuilding(BuildingType.DEUTERIUM_MINE), DEUTERIUM_MINE_CONSUMPTION));
    }

    public void updateDependencies() {
        energyTechnology = planet.getResearch(ResearchType.ENERGY);
    }

    private EnergyProducer createEnergyProducer(Upgradable upgradable, int productionValue) {
        return () -> (int)Math.round(productionValue * upgradable.getLevel() *
                Math.pow(PRODUCTION_GROW_VALUE, upgradable.getLevel() - 1));
    }

    private EnergyConsumer createEnergyConsumer(Upgradable upgradable, int consumptionValue) {
        return () -> (int)Math.round(consumptionValue * upgradable.getLevel() *
                Math.pow(CONSUMPTION_GROW_VALUE, upgradable.getLevel()) * power);
    }

    public void balanceEnergyConsumption() {
        power = ((double)getTotalEnergyProduction()  / getTotalEnergyConsumption()) * power;
        if (power > 1 || Double.isNaN(power)) {
            power = 1;
        }
    }

    public int getTotalEnergyProduction() {
        int production = 0;
        for(EnergyProducer producer: producers) {
            production += producer.getEnergyProduction();
        }
        return (int)Math.round(production * (1 + 0.1 * energyTechnology.getLevel()));
    }

    public int getTotalEnergyConsumption() {
        int consumption = 0;
        for(EnergyConsumer consumer: consumers) {
            consumption += consumer.getEnergyConsumption();
        }
        return consumption;
    }

    public int getEnergyLevel() {
        return getTotalEnergyProduction() - getTotalEnergyConsumption();
    }

    public double getProductionPower() {
        return power;
    }
}
