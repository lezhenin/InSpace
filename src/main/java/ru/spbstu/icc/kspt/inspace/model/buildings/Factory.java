package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.Resources;

import java.time.Duration;

public class Factory extends Building {

    private final static int BUILDING_SPEED_VALUE = 3000;
    private Planet planet;

    public Factory(Planet planet) {
        this(planet, null);
        factory = this;
    }

    public Factory(Planet planet, Factory factory)
    {
        super(factory);
        this.planet = planet;
    }

    public boolean canBeUpgraded(Building building) {
        if (building.getCost().compareTo(planet.getResources()) == -1) {
            return false;
        }
        else return true;
    }

    public void upgrade(Building building) {

        if (canBeUpgraded(building)){
            planet.getResources().getResources(building.getCost());
            building.level++;
        }
        //TODO implement buildings upgrade mechanism
    }

    public Duration getBuildDuration(Building building) {

        Resources cost = building.getCost();
        double hours = (cost.getMetal() + cost.getCrystals())/(BUILDING_SPEED_VALUE * (1 + level));
        return Duration.ofSeconds(Math.round(hours * 3600));
    }

}
