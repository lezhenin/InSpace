package ru.spbstu.icc.kspt.inspace.api;


import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;

public class Ship extends Constructable{
    final private ru.spbstu.icc.kspt.inspace.model.fleet.Ship ship;

    Ship(ru.spbstu.icc.kspt.inspace.model.fleet.Ship ship) {
        super(ship);
        this.ship = ship;
    }

    public ShipType getType() {
        return ship.getType();
    }

    public int getAttack() {
        return ship.getAttack();
    }

    public int getStructure() {
        return ship.getStructure();
    }

    public int getShieldStructure() {
        return ship.getShieldStructure();
    }

    public int getSpeed() {
        return ship.getSpeed();
    }

    public int getResourcesCapacity() {
        return ship.getResourcesCapacity();
    }
}
