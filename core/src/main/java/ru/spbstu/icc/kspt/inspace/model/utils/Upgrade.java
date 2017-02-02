package ru.spbstu.icc.kspt.inspace.model.utils;


import ru.spbstu.icc.kspt.inspace.api.AUpgrade;

import java.time.LocalDateTime;

public abstract class Upgrade extends TimeAction implements AUpgrade {

    private Upgradable upgradable;
    private LocalDateTime time;

    public abstract void onExecute();

    public Upgrade(Upgradable upgradable) {
        this.upgradable = upgradable;
        this.time = Time.now().plus(upgradable.getUpgradeDuration());
    }

    @Override
    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public Upgradable getUpgradable() {
        return upgradable;
    }
}
