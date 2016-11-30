package ru.spbstu.icc.kspt.inspace.api;


import ru.spbstu.icc.kspt.inspace.model.Resources;
import ru.spbstu.icc.kspt.inspace.model.fleet.Fleet;
import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;

import java.util.Collections;
import java.util.Map;

public class FleetInfo {

    final private Map<ShipType, Integer> numbersOfShips;
    final private int capacity;
    final private Planet owner;
    final private ResourcesInfo resources;

    public FleetInfo(Fleet fleet) {
        numbersOfShips = Collections.unmodifiableMap(fleet.getNumbersOfShips());
        capacity = fleet.getCapacity();
        owner = new Planet(fleet.getOwner());
        resources = new ResourcesInfo(fleet.getResources());
    }

    public Map<ShipType, Integer> getNumbersOfShips() {
        return numbersOfShips;
    }

    public int getCapacity() {
        return capacity;
    }

    public Planet getOwner() {
        return owner;
    }

    public ResourcesInfo getResources() {
        return resources;
    }
}
