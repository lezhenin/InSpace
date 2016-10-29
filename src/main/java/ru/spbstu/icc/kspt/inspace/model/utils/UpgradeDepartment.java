package ru.spbstu.icc.kspt.inspace.model.utils;


import ru.spbstu.icc.kspt.inspace.model.Planet;


abstract public class UpgradeDepartment {

    protected Planet planet;
    private Upgrade upgrade;

    public UpgradeDepartment(Planet planet) {
        this.planet = planet;
    }

    protected boolean checkUpgradability(Upgradable upgradable) {
        return (planet.getResources().isEnough(upgradable.getUpgradeCost()) && upgrade == null);
    }

    protected void startUpgrade(Upgrade upgrade) {

        Upgradable upgradable = upgrade.getUpgradable();

        if (!checkUpgradability(upgradable)) {
            //TODO exception
            return;
        }

        planet.getResources().takeResources(upgradable.getUpgradeCost());
        this.upgrade = upgrade;
    }

    public Upgrade getCurrentUpgrade() {
        return upgrade;
    }

    protected boolean update() {
        boolean update = upgrade != null && upgrade.getTime().compareTo(Time.now()) <= 0;
        if (update) {
            upgrade.execute(Time.now());
            upgrade = null;
        }
        return update;
    }
}