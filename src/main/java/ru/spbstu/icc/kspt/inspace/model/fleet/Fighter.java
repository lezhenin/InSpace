package ru.spbstu.icc.kspt.inspace.model.fleet;

import ru.spbstu.icc.kspt.inspace.model.Resources;
import ru.spbstu.icc.kspt.inspace.model.research.LaserTechnology;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;

import java.time.Duration;

public class Fighter extends Ship {

    private static int BASE_ATTACK = 100;
    private static int BASE_STRUCTURE = 100;
    private static int BASE_SHIELD_STRUCTURE = 20;
    private static int BASE_SPEED = 200;
    private static int METAL_COST = 1500;
    private static int CRYSTAL_COST = 500;

    private LaserTechnology laserTechnology;

    public Fighter(FleetDepartment fleetDepartment) {
        super(fleetDepartment);
    }

    @Override
    public int getAttack() {
        return (int)Math.round(BASE_ATTACK * (1 + laserTechnology.getLevel() * 0.02));
    }

    @Override
    public int getStructure() {
        return BASE_STRUCTURE;
    }

    @Override
    public int getShieldStructure() {
        return BASE_SHIELD_STRUCTURE;
    }

    @Override
    public int getSpeed() {
        return BASE_SPEED;
    }

    @Override
    public void updateDependencies() {
        laserTechnology = (LaserTechnology)fleetDepartment.getResearch(ResearchType.LASER);
    }

    @Override
    public Resources getConstructCost() {
        return new Resources(METAL_COST, CRYSTAL_COST, 0);
    }

    @Override
    public Duration getConstructDuration() {
        return null;
    }
}
