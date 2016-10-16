package ru.spbstu.icc.kspt.inspace.model.buildings;


import ru.spbstu.icc.kspt.inspace.model.Resources;

import java.time.Duration;
import java.time.LocalDateTime;

abstract public class Mine extends Building {;

    protected LocalDateTime lastProductionGetting;

    public Mine(Factory factory) {
        super(factory);
        lastProductionGetting = LocalDateTime.now();
    }

    public Resources getProduction() {
        Resources resources = getProductionPerPeriod(Duration.between(lastProductionGetting, LocalDateTime.now()));
        lastProductionGetting = LocalDateTime.now();
        return resources;
    }

    abstract protected Resources getProductionPerPeriod(Duration duration);

}
