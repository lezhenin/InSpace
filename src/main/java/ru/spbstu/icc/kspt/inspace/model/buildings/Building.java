package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.Resources;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgradable;

import java.time.Duration;
import java.time.LocalDateTime;

abstract public class Building implements Upgradable {

    protected Factory factory;

    protected Resources upgradeCost = new Resources(0,0,0);

    protected int level;

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

    protected void upgrade() {
        level++;
    }

    public Duration getUpgradeDuration() {
        return factory.calculateUpgradeDuration(this);
    }

    public Resources getUpgradeCost() {
        return upgradeCost;
    }

    public int getLevel() {
        return level;
    }
}
