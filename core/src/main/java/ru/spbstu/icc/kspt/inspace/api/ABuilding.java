package ru.spbstu.icc.kspt.inspace.api;

import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgradable;


public interface ABuilding extends AUpgradable {

    BuildingType getType();
}
