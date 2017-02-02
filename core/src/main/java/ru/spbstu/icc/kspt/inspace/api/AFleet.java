package ru.spbstu.icc.kspt.inspace.api;

import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;
import ru.spbstu.icc.kspt.inspace.model.resources.*;

import java.util.Map;


public interface AFleet {

    AResources getResources();

    Map<ShipType, Integer> getNumbersOfShips();

    APlanet getOwner();

    int getCapacity();

    int getSpeed();

    int getNumberOfShips();

    int getSummaryStructure();

    int getSummaryAttack();
}
