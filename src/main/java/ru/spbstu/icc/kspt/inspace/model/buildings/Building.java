package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.Resources;

import java.time.Duration;

public class Building {

    protected Factory factory;

    protected Resources cost;

    protected int level;

    public Building(Factory factory) {
        this.factory = factory;
    }

    public boolean canBeUpgraded() {
        return factory.canBeUpgraded(this);
    }

    public void upgrade() {
        factory.upgrade(this);
    }

    public Duration getBuildDuration() {
        return factory.getBuildDuration(this);
    }

    public Resources getCost() {
        return cost;
    }

    public int getLevel() {
        return level;
    }
}
