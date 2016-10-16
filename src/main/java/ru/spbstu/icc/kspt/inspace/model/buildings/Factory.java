package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.Resources;

import java.time.Duration;

public class Factory extends Building {

    private final static int BUILDING_SPEED_VALUE = 3000;

    public Factory() {
        this(null);
        factory = this;
    }

    public Factory(Factory factory) {
        super(factory);
    }

    protected void upgrade(Building building) {
        building.level++;
        //TODO implement buildings upgrade mechanism
    }

    protected Duration getDuration(Building building) {

        Resources cost = building.getCost();
        double hours = (cost.getMetal() + cost.getCrystals())/(BUILDING_SPEED_VALUE * (1 + level));
        return Duration.ofSeconds(Math.round(hours * 3600));
    }

}
