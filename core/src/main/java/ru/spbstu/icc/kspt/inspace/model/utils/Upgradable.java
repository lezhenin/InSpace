package ru.spbstu.icc.kspt.inspace.model.utils;

import ru.spbstu.icc.kspt.inspace.model.Resources;
import ru.spbstu.icc.kspt.inspace.model.exception.UpgradeException;

import java.time.Duration;

public interface Upgradable {

    boolean canBeUpgraded();

    void startUpgrade() throws UpgradeException;

    Duration getUpgradeDuration();

    Resources getUpgradeCost();

    int getLevel();
}
