package ru.spbstu.icc.kspt.inspace.api;

import ru.spbstu.icc.kspt.inspace.model.exception.UpgradeException;

import java.time.Duration;


abstract public class Upgradable {
    public final ru.spbstu.icc.kspt.inspace.model.utils.Upgradable upgradable;

    Upgradable(ru.spbstu.icc.kspt.inspace.model.utils.Upgradable upgradable) {
        this.upgradable = upgradable;
    }

    public boolean canBeUpgraded(){
        return upgradable.canBeUpgraded();
    }

    public void upgrade() throws UpgradeException {
        upgradable.startUpgrade();
    }

    public int getLevel() {
        return upgradable.getLevel();
    }

    public Duration getUpgradeDuration(){
        return Duration.ofSeconds(upgradable.getUpgradeDuration().getSeconds());
    }

    public Resources getUpgradeCost() {
        return new Resources(upgradable.getUpgradeCost());
    }
}
