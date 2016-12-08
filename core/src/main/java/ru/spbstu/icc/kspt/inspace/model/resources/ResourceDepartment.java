package ru.spbstu.icc.kspt.inspace.model.resources;


import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.utils.Department;
import ru.spbstu.icc.kspt.inspace.model.utils.Time;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgradable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ResourceDepartment extends Department{

    private final static int METAL_PRODUCTION_VALUE = 30;
    private final static int CRYSTAL_PRODUCTION_VALUE = 25;
    private final static int DEUTERIUM_PRODUCTION_VALUE = 20;

    private final static double PRODUCTION_GROW_VALUE = 1.1;

    private Resources resources = new Resources(3000, 2000, 1000);
    private LocalDateTime lastUpdating;
    private List<ResourceProducer> producers = new ArrayList<>();

    public ResourceDepartment(Planet planet) {
        super(planet);
        lastUpdating = Time.now();
        producers.add(
                createResourceProducer(planet.getBuilding(BuildingType.METAL_MINE), METAL_PRODUCTION_VALUE, 0, 0));
        producers.add(
                createResourceProducer(planet.getBuilding(BuildingType.CRYSTAL_MINE), 0, CRYSTAL_PRODUCTION_VALUE, 0));
        producers.add(
                createResourceProducer(planet.getBuilding(BuildingType.DEUTERIUM_MINE), 0, 0, DEUTERIUM_PRODUCTION_VALUE));
    }

    @Override
    public void updateDependencies() {

    }


    private ResourceProducer createResourceProducer(Upgradable upgradable, int metalProductionValue,
                                                    int crystalsProductionValue, int deuteriumProductionValue) {
        return new ResourceProducer() {

            private double getProduction(Duration duration, int productionValue, int level) {
                double production = productionValue * (level + 1) * Math.pow(PRODUCTION_GROW_VALUE, level);
                production *= duration.getSeconds() / 3600.0;
                return production;
            }

            @Override
            public Resources getProduction(Duration duration) {
                double metal = getProduction(
                        duration,  metalProductionValue,  upgradable.getLevel()) * planet.getProductionPower();
                double crystals = getProduction(
                        duration, crystalsProductionValue, upgradable.getLevel()) * planet.getProductionPower();
                double deuterium = getProduction(
                        duration, deuteriumProductionValue, upgradable.getLevel()) * planet.getProductionPower();
                return new Resources((int)Math.round(metal), (int)Math.round(crystals), (int)Math.round(deuterium));
            }};
    }


    public void updateResources(LocalDateTime now) {
        Duration duration = Duration.between(lastUpdating, now);
        Resources resources = new Resources(0, 0, 0);
        for (ResourceProducer producer: producers) {
            resources.putResources(producer.getProduction(duration));
        }
        lastUpdating = Time.now();
        this.resources.putResources(resources);
    }

    public Resources getResources() {
        return resources;
    }
}
