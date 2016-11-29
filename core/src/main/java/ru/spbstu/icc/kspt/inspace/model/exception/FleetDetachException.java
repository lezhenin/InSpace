package ru.spbstu.icc.kspt.inspace.model.exception;


import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;

import java.util.Map;

public class FleetDetachException extends Exception {

    Map<ShipType, Integer> existingFleet;
    Map<ShipType, Integer> detachingFleet;

    public FleetDetachException(Map<ShipType, Integer> existingFleet, Map<ShipType, Integer> detachingFleet) {
        this.existingFleet = existingFleet;
        this.detachingFleet = detachingFleet;
    }

    public FleetDetachException(String message, Map<ShipType, Integer> existingFleet, Map<ShipType, Integer> detachingFleet) {
        super(message);
        this.existingFleet = existingFleet;
        this.detachingFleet = detachingFleet;
    }

    public Map<ShipType, Integer> getExistingFleet() {
        return existingFleet;
    }

    public Map<ShipType, Integer> getDetachingFleet() {
        return detachingFleet;
    }
}
