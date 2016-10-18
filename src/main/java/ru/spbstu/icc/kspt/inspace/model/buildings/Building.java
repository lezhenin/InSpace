package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.Resources;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgradable;

import java.time.Duration;
import java.time.LocalDateTime;

abstract public class Building implements Upgradable {

    protected Factory factory;

    protected int level;


    protected void upgrade() {
        level++;
    }


    public Building(Factory factory) {
        this.factory = factory;
    }

    public boolean canBeUpgraded() {
        return factory.checkUpgradability(this);
    }

    @Override
    public void startUpgrade() {
        LocalDateTime upgradeTime = LocalDateTime.now().plus(getUpgradeDuration());
        factory.startUpgrade(new BuildingUpgrade(this, upgradeTime) {
            @Override
            public void execute(LocalDateTime now) {
                super.execute(now);
                upgrade();
            }
        });
    }

    abstract public Duration getUpgradeDuration();

    abstract public Resources getUpgradeCost();

    public int getLevel() {
        return level;
    }
}
