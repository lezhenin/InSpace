package ru.spbstu.icc.kspt.inspace.api;

import ru.spbstu.icc.kspt.inspace.model.exception.UpgradeException;
import ru.spbstu.icc.kspt.inspace.model.resources.Resources;

import java.time.Duration;


public interface AUpgradable {

    boolean canBeUpgraded();

    void startUpgrade() throws UpgradeException;

    Duration getUpgradeDuration();

    AResources getUpgradeCost();

    int getLevel();
}
