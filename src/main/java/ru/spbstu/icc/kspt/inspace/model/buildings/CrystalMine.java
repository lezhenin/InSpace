package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.Resources;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

public class CrystalMine extends Mine {

    private static final int PRODUCTION_SPEED_VALUE_1 = 25;
    private static final double PRODUCTION_SPEED_VALUE_2 = 1.1;

    public CrystalMine(Factory factory) {
        super(factory);
    }

    @Override
    protected Resources getProductionPerPeriod(Duration duration) {
        double crystals = PRODUCTION_SPEED_VALUE_1 * (level+1) * Math.pow(PRODUCTION_SPEED_VALUE_2, level);
        crystals *= duration.getSeconds()/3600.0;
        return new Resources(0, (int)Math.round(crystals), 0);
    }
}
