package ru.spbstu.icc.kspt.inspace.model.utils;


import java.time.LocalDateTime;
import java.util.*;

public abstract class Upgrade extends Action {

    private Upgradable upgradable;
    private LocalDateTime time;

    public abstract void onExecute();

    public Upgrade(Upgradable upgradable) {
        this.upgradable = upgradable;
        this.time = Time.now().plus(upgradable.getUpgradeDuration());
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Upgradable getUpgradable() {
        return upgradable;
    }
}
