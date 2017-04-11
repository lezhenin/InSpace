package ru.spbstu.icc.kspt.inspace.service.documents;

import ru.spbstu.icc.kspt.inspace.api.AFleet;
import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;

import java.util.Map;

public class Fleet {
    private final int capacity;
    private final int speed;
    private final int attack;
    private final int structure;
    private final int numberOfShips;
    private final Map<ShipType, Integer> numbersOfShips;
    private final PlanetDescription owner;

    public Fleet(AFleet fleet) {
        this.capacity = fleet.getCapacity();
        this.speed = fleet.getSpeed();
        this.attack = fleet.getSummaryAttack();
        this.structure = fleet.getSummaryStructure();
        this.numberOfShips = fleet.getNumberOfShips();
        this.numbersOfShips = fleet.getNumbersOfShips();
        this.owner = new PlanetDescription(fleet.getOwner());
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSpeed() {
        return speed;
    }

    public int getAttack() {
        return attack;
    }

    public int getStructure() {
        return structure;
    }

    public int getNumberOfShips() {
        return numberOfShips;
    }

    public Map<ShipType, Integer> getNumbersOfShips() {
        return numbersOfShips;
    }

    public PlanetDescription getOwner() {
        return owner;
    }
}
