package ru.spbstu.icc.kspt.inspace.model.utils;

import ru.spbstu.icc.kspt.inspace.api.AUpgradable;
import ru.spbstu.icc.kspt.inspace.model.resources.Resources;

public interface Upgradable extends AUpgradable {
    @Override
    Resources getUpgradeCost();
}
