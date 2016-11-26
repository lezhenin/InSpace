package ru.spbstu.icc.kspt.inspace.model.utils;

import ru.spbstu.icc.kspt.inspace.model.Resources;

import java.time.Duration;

public interface Upgradable {

    boolean canBeUpgraded();

    void startUpgrade();

    Duration getUpgradeDuration();

    Resources getUpgradeCost();

    int getLevel();
}
