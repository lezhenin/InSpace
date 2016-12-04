package ru.spbstu.icc.kspt.inspace.model.resources;


import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.buildings.Building;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.utils.Department;
import ru.spbstu.icc.kspt.inspace.model.utils.Time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ResourceDepartment extends Department{

    private final static int METAL_PRODUCTION_VALUE = 30;
    private final static int CRYSTAL_PRODUCTION_VALUE = 25;
    private final static int DEUTERIUM_PRODUCTION_VALUE = 20;

    private final static double PRODUCTION_GROW_VALUE = 1.1;

    private Resources resources = new Resources(0, 0, 0);
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


    //TODO возможно стоит сделать отдельный класс, а не анонимный, надо подумать
    private ResourceProducer createResourceProducer(Building building, int metalProductionValue,
                                                    int crystalsProductionValue, int deuteriumProductionValue) {
        return new ResourceProducer() {

            private Building producer = building;
            private int metalProduction = metalProductionValue;
            private int crystalsProduction = crystalsProductionValue;
            private int deuteriumProduction = deuteriumProductionValue;

            private double getProduction(Duration duration, int productionValue, int level) {
                double production = productionValue * (level + 1) * Math.pow(PRODUCTION_GROW_VALUE, level);
                production *= duration.getSeconds() / 3600.0;
                return production;
            }

            @Override
            public Resources getProduction(Duration duration) {
                double metal = getProduction(duration, metalProduction, producer.getLevel()) * planet.getProductionPower();
                double crystals = getProduction(duration, crystalsProduction, producer.getLevel()) * planet.getProductionPower();
                double deuterium = getProduction(duration, deuteriumProduction, producer.getLevel()) * planet.getProductionPower();
                return new Resources((int)Math.round(metal), (int)Math.round(crystals), (int)Math.round(deuterium));
            }
        };
    }

    public void updateResources(LocalDateTime now) {
        Duration duration = Duration.between(lastUpdating, now);
        Resources resources = new Resources(0, 0, 0);
        for (ResourceProducer producer: producers) {
            resources.addResources(producer.getProduction(duration));
        }
        lastUpdating = Time.now();
        this.resources.addResources(resources);
    }

    public Resources getResources() {
        return resources;
    }
}
