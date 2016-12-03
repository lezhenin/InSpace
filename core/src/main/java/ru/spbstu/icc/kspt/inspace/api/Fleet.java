package ru.spbstu.icc.kspt.inspace.api;



import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;

import java.util.Collections;
import java.util.Map;

public class Fleet {

    final private ru.spbstu.icc.kspt.inspace.model.fleet.Fleet fleet;

    Fleet(ru.spbstu.icc.kspt.inspace.model.fleet.Fleet fleet) {
        this.fleet = fleet;
    }

    public Map<ShipType, Integer> getNumbersOfShips() {
        return Collections.unmodifiableMap(fleet.getNumbersOfShips());
    }

    public int getCapacity() {
        return fleet.getCapacity();
    }

    public Planet getOwner() {
        return new Planet(fleet.getOwner());
    }

    public Resources getResources() {
        return new Resources(fleet.getResources());
    }
}
