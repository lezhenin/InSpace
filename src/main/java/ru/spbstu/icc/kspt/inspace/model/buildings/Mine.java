package ru.spbstu.icc.kspt.inspace.model.buildings;


import ru.spbstu.icc.kspt.inspace.model.Resources;

import java.time.Duration;
import java.time.LocalDateTime;

abstract public class Mine extends Building {;

    protected LocalDateTime lastProductionGetting;
    protected Resources temporaryStorage;

    public Mine(Factory factory) {
        super(factory);
        lastProductionGetting = LocalDateTime.now();
        temporaryStorage = new Resources(0,0,0);
    }

    public Resources getProduction() {
        Resources resources = getProductionPerPeriod(Duration.between(lastProductionGetting, LocalDateTime.now()));
        resources.addResources(temporaryStorage);
        temporaryStorage = new Resources(0,0,0);
        lastProductionGetting = LocalDateTime.now();
        return resources;
    }

    @Override
    protected void upgrade() {
        temporaryStorage.addResources(getProduction());
        super.upgrade();
    }

    abstract protected Resources getProductionPerPeriod(Duration duration);

}
