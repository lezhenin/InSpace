package ru.spbstu.icc.kspt.inspace.model.resources;


import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.buildings.Building;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.utils.Department;
import ru.spbstu.icc.kspt.inspace.model.utils.Time;

import java.time.Duration;
import java.time.LocalDateTime;


public class ResourceDepartment extends Department{

    private final static int METAL_PRODUCTION_VALUE = 30;
    private final static int CRYSTAL_PRODUCTION_VALUE = 25;
    private final static int DEUTERIUM_PRODUCTION_VALUE = 20;

    private Resources resources = new Resources(0, 0, 0);
    private LocalDateTime lastUpdating;
    private Building metalMine;
    private Building crystalMine;
    private Building deuteriumMine;

    public ResourceDepartment(Planet planet) {
        super(planet);
        lastUpdating = Time.now();
        metalMine = planet.getBuilding(BuildingType.METAL_MINE);
        crystalMine = planet.getBuilding(BuildingType.CRYSTAL_MINE);
        deuteriumMine = planet.getBuilding(BuildingType.DEUTERIUM_MINE);
    }

    private double getProduction(Duration duration, int productionValue, int level) {
        double production = productionValue * (level+1) * Math.pow(1.1, level);
        production *= duration.getSeconds() / 3600.0;
        return production;
    }

    public void updateResources(LocalDateTime now) {
        Duration duration = Duration.between(lastUpdating, now);
        double power = planet.getProductionPower();
        assert (power >= 0.0 && power <= 1.0);
        int metal = (int)Math.round(getProduction(duration, METAL_PRODUCTION_VALUE, metalMine.getLevel()) * power);
        int crystals = (int)Math.round(getProduction(duration, CRYSTAL_PRODUCTION_VALUE, crystalMine.getLevel()) * power);
        int deuterium = (int)Math.round(getProduction(duration, DEUTERIUM_PRODUCTION_VALUE, deuteriumMine.getLevel()) * power);
        Resources resources = new Resources(metal, crystals, deuterium);
        lastUpdating = Time.now();
        this.resources.addResources(resources);
    }

    public Resources getResources() {
        return resources;
    }
}
