package ru.spbstu.icc.kspt.inspace.model.research;

import ru.spbstu.icc.kspt.inspace.model.resources.Resources;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.buildings.Laboratory;
import ru.spbstu.icc.kspt.inspace.model.exception.UpgradeException;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgradable;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgrade;

import java.time.Duration;

public class Research implements Upgradable{

    private static final double COST_GROWING_SPEED = 1.6;

    private int level;
    protected ResearchDepartment department;
    private ResearchType type;

    private Laboratory laboratory;

    public Research(ResearchDepartment department, ResearchType type) {
        this.department = department;
        this.type = type;
    }

    void updateDependencies() {
        laboratory = (Laboratory)department.getBuilding(BuildingType.LABORATORY);
    }

    public boolean canBeUpgraded() {
        return department.canBeUpgraded(this);
    }

    @Override
    public void startUpgrade() throws UpgradeException {
        department.startUpgrade(new Upgrade(this) {
            @Override
            public void onExecute() {
                level++;
            }
        });
    }

    public ResearchType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public Duration getUpgradeDuration() {
        Resources cost = getUpgradeCost();
        double summaryCost = cost.getMetal() + cost.getCrystals() + cost.getDeuterium();
        double hours = summaryCost / (type.UPGRADE_SPEED_VALUE * (1 + laboratory.getLevel()));
        return Duration.ofSeconds(Math.round(hours * 3600));
    }

    @Override
    public Resources getUpgradeCost() {
        int metal = (int)Math.round(type.METAL_COST_VALUE * Math.pow(COST_GROWING_SPEED, level));
        int crystal = (int)Math.round(type.CRYSTAL_COST_VALUE * Math.pow(COST_GROWING_SPEED, level));
        int deuterium = (int)Math.round(type.DEUTERIUM_COST_VALUE * Math.pow(COST_GROWING_SPEED, level));
        return new Resources(metal, crystal, deuterium);
    }

}
