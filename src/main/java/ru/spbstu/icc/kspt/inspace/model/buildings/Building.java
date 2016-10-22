package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.departments.BuildingDepartment;
import ru.spbstu.icc.kspt.inspace.model.Resources;
import ru.spbstu.icc.kspt.inspace.model.utils.Time;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgradable;

import java.time.Duration;
import java.time.LocalDateTime;

abstract public class Building implements Upgradable {

    protected BuildingDepartment department;

    protected int level;

    protected void upgrade() {
        level++;
    }

    public Building(BuildingDepartment department) {
        this.department = department;
    }

    public boolean canBeUpgraded() {
        return department.checkUpgradability(this);
    }

    @Override
    public void startUpgrade() {
        LocalDateTime upgradeTime = Time.now().plus(getUpgradeDuration());
        department.startUpgrade(new BuildingUpgrade(this, upgradeTime) {
            @Override
            public void execute(LocalDateTime now) {
                super.execute(now);
                upgrade();
            }
        });
    }

    abstract public Duration getUpgradeDuration();

    abstract public Resources getUpgradeCost();

    abstract public int getEnergyConsumption();

    public int getLevel() {
        return level;
    }
}
