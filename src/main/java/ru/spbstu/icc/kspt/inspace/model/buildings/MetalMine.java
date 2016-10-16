package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.Resources;

import java.time.Duration;

public class MetalMine extends Mine {

    private static final int PRODUCTION_SPEED_VALUE_1 = 30;
    private static final double PRODUCTION_SPEED_VALUE_2 = 1.1;

    public MetalMine(Factory factory) {
        super(factory);
    }

    @Override
    protected Resources getProductionPerPeriod(Duration duration) {
        double metal = PRODUCTION_SPEED_VALUE_1 * (level+1) * Math.pow(PRODUCTION_SPEED_VALUE_2, level);
        metal *= duration.getSeconds()/3600.0;
        return new Resources((int)Math.round(metal), 0, 0);
    }
}
