package ru.spbstu.icc.kspt.inspace.model.utils;


import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.Resources;


abstract public class UpgradeDepartment {

    protected Planet planet;
    private Upgrade upgrade;

    public UpgradeDepartment(Planet planet) {
        this.planet = planet;
    }

    protected boolean canBeUpgraded(Upgradable upgradable) {
        update();
        return (planet.getResources().compareTo(upgradable.getUpgradeCost()) != -1 && upgrade == null);
    }

    protected void startUpgrade(Upgrade upgrade) {
        update();
        Upgradable upgradable = upgrade.getUpgradable();
        if (!canBeUpgraded(upgradable)) {
            //TODO exception
            return;
        }

        planet.getResources().takeResources(upgradable.getUpgradeCost());
        this.upgrade = upgrade;
    }

    public Upgrade getCurrentUpgrade() {
        update();
        return upgrade;
    }

    public void update() {
        if (upgrade != null && upgrade.getTime().compareTo(Time.now()) <= 0) {
            upgrade.execute();
            upgrade = null;
        }
    }
}