package ru.spbstu.icc.kspt.inspace.model.fleet;

import ru.spbstu.icc.kspt.inspace.model.Resources;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.buildings.Shipyard;
import ru.spbstu.icc.kspt.inspace.model.exception.ConstructException;
import ru.spbstu.icc.kspt.inspace.model.research.LaserTechnology;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;
import ru.spbstu.icc.kspt.inspace.model.utils.Construct;
import ru.spbstu.icc.kspt.inspace.model.utils.Constructable;

import java.time.Duration;

public class Ship implements Constructable {


    private ShipType type;

    private FleetDepartment fleetDepartment;
    private LaserTechnology laserTechnology;
    private Shipyard shipyard;

    public Ship(ShipType type, FleetDepartment fleetDepartment) {
        this.type = type;
        this.fleetDepartment = fleetDepartment;
        updateDependencies();
    }

    public ShipType getType() {
        return type;
    }

    public int getAttack() {
        return (int)Math.round(type.attack * (1 + laserTechnology.getLevel() * 0.02));
    }

    public int getStructure() {
        return type.structure;
    }

    public int getShieldStructure() {
        return type.shieldStructure;
    }

    public int getSpeed() {
        return type.speed;
    }

    public int getResourcesCapacity() {
        return type.capacity;
    }

    @Override
    public Resources getConstructCost() {
        return new Resources(type.metalCost, type.crystalCost, type.deuteriumCost);
    }

    @Override
    public void startConstruction(int number) throws ConstructException {
        fleetDepartment.startConstruction(new Construct(this, number) {
            @Override
            public void onExecute() {

            }
        });
    }

    @Override
    public Duration getConstructDuration() {
        double hours = getConstructCost().getAmount() / (type.constructSpeedValue * (1 + shipyard.getLevel()));
        return Duration.ofSeconds(Math.round(hours * 3600));

    }

    public void updateDependencies() {
        laserTechnology = (LaserTechnology) fleetDepartment.getResearch(ResearchType.LASER);
        shipyard = (Shipyard) fleetDepartment.getBuilding(BuildingType.SHIPYARD);
    }
}