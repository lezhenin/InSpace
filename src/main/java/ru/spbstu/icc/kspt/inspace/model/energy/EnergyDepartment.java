package ru.spbstu.icc.kspt.inspace.model.energy;

import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.buildings.Building;
import ru.spbstu.icc.kspt.inspace.model.buildings.PowerStation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnergyDepartment {

    private Planet planet;
    private List<EnergyConsumer> consumers = new ArrayList<>();
    private List<EnergyProducer> producers = new ArrayList<>();

    public EnergyDepartment(Planet planet) {
        this.planet = planet;

        //TODO find alternative way to get consumers and producers
        for(Map.Entry<Planet.BuildingType, Building> building: planet.getBuildings()){
            if (building instanceof EnergyConsumer) {
                addConsumer((EnergyConsumer)building);
            }
            if (building instanceof EnergyProducer) {
                addProducer((EnergyProducer)building);
            }
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


}
