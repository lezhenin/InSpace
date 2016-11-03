package ru.spbstu.icc.kspt.inspace.model.fleet;

import ru.spbstu.icc.kspt.inspace.model.utils.Constructable;

abstract public class Ship implements Constructable {

    protected FleetDepartment fleetDepartment;

    public Ship(FleetDepartment fleetDepartment) {
        this.fleetDepartment = fleetDepartment;
    }

    public abstract int getAttack();

    public abstract int getStructure();

    public abstract int getShieldStructure();

    public abstract int getSpeed();

    abstract public void updateDependencies();
}