package ru.spbstu.icc.kspt.inspace.model.exception;


import ru.spbstu.icc.kspt.inspace.model.utils.Upgradable;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgrade;

public class UpgradeException extends Exception{
    private Upgradable upgradable;

    public UpgradeException(Upgradable upgradable) {
        this.upgradable = upgradable;
    }

    public UpgradeException(String message, Upgradable upgradable) {
        super(message);
        this.upgradable = upgradable;
    }

    public Upgradable getUpgradable() {
        return upgradable;
    }
}
