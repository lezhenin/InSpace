package ru.spbstu.icc.kspt.inspace.model.buildings;


import ru.spbstu.icc.kspt.inspace.model.Resources;
import ru.spbstu.icc.kspt.inspace.model.energy.EnergyConsumer;
import ru.spbstu.icc.kspt.inspace.model.utils.Time;

import java.time.Duration;
import java.time.LocalDateTime;


abstract public class Mine extends Building implements EnergyConsumer {

    private LocalDateTime lastProductionGetting;
    private Resources temporaryStorage;
    double power = 1.0;

    protected Factory factory;

    public Mine(BuildingDepartment department, BuildingType type) {
        super(department, type);

        lastProductionGetting = Time.now();
        temporaryStorage = new Resources(0,0,0);
    }

    @Override
    public void updateDependencies() {
        factory = (Factory)department.getBuilding(BuildingType.FACTORY);
    }

    public Resources getProduction(LocalDateTime now) {
        Resources resources = getProductionPerDuration(Duration.between(lastProductionGetting, now));
        resources.addResources(temporaryStorage);
        temporaryStorage = new Resources(0,0,0);
        lastProductionGetting = Time.now();
        return resources;
    }

    @Override
    protected void upgrade() {
        temporaryStorage.addResources(getProduction(Time.now()));
        super.upgrade();
    }

    @Override
    public void setProductionPower(double power) {
        if (power <= 1 && power >=0 ) {
            this.power = power;
        }
    }

    abstract protected Resources getProductionPerDuration(Duration duration);
}
