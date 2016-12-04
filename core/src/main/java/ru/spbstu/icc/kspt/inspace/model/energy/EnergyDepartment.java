package ru.spbstu.icc.kspt.inspace.model.energy;

import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.buildings.Building;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.utils.Department;

import java.util.ArrayList;
import java.util.List;

public class EnergyDepartment extends Department{

    private static final double CONSUMPTION_GROW_VALUE = 1.4;
    private static final double PRODUCTION_GROW_VALUE = 1.35;

    private List<EnergyConsumer> consumers = new ArrayList<>();
    private List<EnergyProducer> producers = new ArrayList<>();
    private double power = 1;

    public EnergyDepartment(Planet planet) {
        super(planet);
        producers.add(createEnergyProducer(planet.getBuilding(BuildingType.POWER_STATION), 100));
        consumers.add(createEnergyConsumer(planet.getBuilding(BuildingType.METAL_MINE), 30));
        consumers.add(createEnergyConsumer(planet.getBuilding(BuildingType.CRYSTAL_MINE), 40));
        consumers.add(createEnergyConsumer(planet.getBuilding(BuildingType.DEUTERIUM_MINE), 45));

    }

    private EnergyProducer createEnergyProducer(Building building, int productionValue) {
        return new EnergyProducer() {

            private final Building consumer = building;
            private final int production = productionValue;

            @Override
            public int getEnergyProduction() {
                return (int)Math.round(production * consumer.getLevel() *
                        Math.pow(PRODUCTION_GROW_VALUE, consumer.getLevel() - 1));
            }
        };
    }

    private EnergyConsumer createEnergyConsumer(Building building, int consumptionValue) {
        return new EnergyConsumer() {

            private final Building consumer = building;
            private final int consumption = consumptionValue;

            @Override
            public int getEnergyConsumption() {
                return (int)Math.round(consumption * consumer.getLevel() *
                        Math.pow(CONSUMPTION_GROW_VALUE, consumer.getLevel()) * power);
            }
        };
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
        return production;
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

    public void addConsumer(EnergyConsumer consumer) {
        consumers.add(consumer);
    }

    public void addProducer(EnergyProducer producer) {
        producers.add(producer);
    }

    public List<EnergyProducer> getProducers() {
        return producers;
    }

    public List<EnergyConsumer> getConsumers() {
        return consumers;
    }

    public double getProductionPower() {
        return power;
    }
}
