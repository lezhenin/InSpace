package ru.spbstu.icc.kspt.inspace.model.buildings;


import ru.spbstu.icc.kspt.inspace.model.exception.UpgradeException;
import ru.spbstu.icc.kspt.inspace.model.resources.Resources;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgradable;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgrade;

import java.time.Duration;

public class Building implements Upgradable {

    private static final double UPGRADE_COST_GROW_VALUE = 1.6;
    private BuildingDepartment department;
    private int level;
    private BuildingType type;
    private Building factory;

    public Building(BuildingDepartment department, BuildingType type) {
        this.department = department;
        this.type = type;
        if (type == BuildingType.FACTORY) {
            factory = this;
        } else {
            factory = department.getBuilding(BuildingType.FACTORY);
        }
    }

    public boolean canBeUpgraded() {
        department.updatePlanet();
        return department.canBeUpgraded(this);
    }

    @Override
    public void startUpgrade() throws UpgradeException {
        if(!canBeUpgraded()) {
            throw new UpgradeException(this);
        }
        department.startUpgrade(new Upgrade(this) {
            @Override
            public void onExecute(){
                level++;
            }
        });
    }

    @Override
    public Duration getUpgradeDuration() {
        Resources cost = getUpgradeCost();
        double summaryCost = cost.getMetal() + cost.getCrystals();
        double hours = summaryCost / (type.UPGRADE_SPEED_VALUE * (1 + factory.getLevel()));
        return Duration.ofSeconds(Math.round(hours * 3600));
    }

    @Override
    public Resources getUpgradeCost() {
        int metal = (int)Math.round(type.METAL_COST_VALUE * Math.pow(UPGRADE_COST_GROW_VALUE, getLevel()));
        int crystal = (int)Math.round(type.CRYSTAL_COST_VALUE * Math.pow(UPGRADE_COST_GROW_VALUE, getLevel()));
        return new Resources(metal, crystal, 0);
    }

    public BuildingType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }
}
