package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.Resources;

import java.time.Duration;

public class DeuteriumMine extends Mine {

    private static final int PRODUCTION_SPEED_VALUE_1 = 20;
    private static final double PRODUCTION_SPEED_VALUE_2 = 1.1;

    public DeuteriumMine(Factory factory) {
        super(factory);
    }

    @Override
    protected Resources getProductionPerPeriod(Duration duration) {
        int deuterium = (int)Math.round(PRODUCTION_SPEED_VALUE_1 * level * Math.pow(PRODUCTION_SPEED_VALUE_2, level));
        return new Resources(0, 0, deuterium);
    }
}
