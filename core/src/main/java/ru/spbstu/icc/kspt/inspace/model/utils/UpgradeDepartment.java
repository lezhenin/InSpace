package ru.spbstu.icc.kspt.inspace.model.utils;


import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.exception.UpgradeException;


abstract public class UpgradeDepartment {

    protected Planet planet;
    private Upgrade upgrade;

    public UpgradeDepartment(Planet planet) {
        this.planet = planet;
    }

    protected boolean canBeUpgraded(Upgradable upgradable) {
        return (planet.getResources().compareTo(upgradable.getUpgradeCost()) != -1 && upgrade == null);
    }

    protected void startUpgrade(Upgrade upgrade) throws UpgradeException {
        Upgradable upgradable = upgrade.getUpgradable();
        if (!canBeUpgraded(upgradable)) {
            throw new UpgradeException(upgradable);
        }
        upgrade.addActionAfterExecution(new Action() {
            @Override
            protected void onExecute() {
                UpgradeDepartment.this.upgrade = null;
            }
        });
        this.upgrade = upgrade;
        planet.getResources().takeResources(upgradable.getUpgradeCost());
    }

    public Upgrade getCurrentUpgrade() {
        return upgrade;
    }

//    public void update() {
//        if (upgrade != null && upgrade.getTime().compareTo(Time.now()) <= 0) {
//            upgrade.execute();
//            upgrade = null;
//        }
//    }
}