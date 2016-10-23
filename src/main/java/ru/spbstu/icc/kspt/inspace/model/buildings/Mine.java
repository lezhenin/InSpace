package ru.spbstu.icc.kspt.inspace.model.buildings;


import ru.spbstu.icc.kspt.inspace.model.departments.BuildingDepartment;
import ru.spbstu.icc.kspt.inspace.model.Resources;
import ru.spbstu.icc.kspt.inspace.model.energy.EnergyConsumer;
import ru.spbstu.icc.kspt.inspace.model.utils.Time;

import java.time.Duration;
import java.time.LocalDateTime;

abstract public class Mine extends Building implements EnergyConsumer {;

    protected LocalDateTime lastProductionGetting;
    protected Resources temporaryStorage;
    protected Factory factory;
    protected double power = 1.0;

    public Mine(BuildingDepartment department, Factory factory) {
        super(department);
        this.factory = factory;

        lastProductionGetting = Time.now();
        temporaryStorage = new Resources(0,0,0);
    }

    public Resources getProduction() {
        Resources resources = getProductionPerPeriod(Duration.between(lastProductionGetting, Time.now()));
        resources.addResources(temporaryStorage);
        temporaryStorage = new Resources(0,0,0);
        lastProductionGetting = Time.now();
        return resources;
    }

    @Override
    protected void upgrade() {
        temporaryStorage.addResources(getProduction());
        super.upgrade();
    }

    @Override
    public void setProductionPower(double power) {
        if (power <= 1 && power >=0 ) {
            this.power = power;
        }
    }

    abstract protected Resources getProductionPerPeriod(Duration duration);
}
