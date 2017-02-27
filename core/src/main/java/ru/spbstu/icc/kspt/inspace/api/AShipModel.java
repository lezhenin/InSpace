package ru.spbstu.icc.kspt.inspace.api;

import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;


public interface AShipModel extends AConstructable {
    ShipType getType();

    int getAttack();

    int getStructure();

    int getShieldStructure();

    int getSpeed();

    int getResourcesCapacity();
}
