package ru.spbstu.icc.kspt.inspace.model.buildings;


import ru.spbstu.icc.kspt.inspace.model.Resources;
import ru.spbstu.icc.kspt.inspace.model.utils.Time;

import java.time.Duration;
import java.time.LocalDateTime;

abstract public class Mine extends Building {;

    protected LocalDateTime lastProductionGetting;
    protected Resources temporaryStorage;

    public Mine(Factory factory) {
        super(factory);
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

    abstract protected Resources getProductionPerPeriod(Duration duration);

}
