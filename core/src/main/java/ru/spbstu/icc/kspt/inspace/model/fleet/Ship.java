package ru.spbstu.icc.kspt.inspace.model.fleet;

import ru.spbstu.icc.kspt.inspace.model.buildings.Building;
import ru.spbstu.icc.kspt.inspace.model.resources.Resources;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.exception.ConstructException;
import ru.spbstu.icc.kspt.inspace.model.research.Research;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;
import ru.spbstu.icc.kspt.inspace.model.utils.Construct;
import ru.spbstu.icc.kspt.inspace.model.utils.Constructable;

import java.time.Duration;

public class Ship implements Constructable {


    private ShipType type;

    private FleetDepartment fleetDepartment;
    private Research laserTechnology;
    private Building shipyard;

    public Ship(ShipType type, FleetDepartment fleetDepartment) {
        this.type = type;
        this.fleetDepartment = fleetDepartment;
        updateDependencies();
    }

    public ShipType getType() {
        return type;
    }

    public int getAttack() {
        return (int)Math.round(type.ATTACK * (1 + laserTechnology.getLevel() * 0.02));
    }

    public int getStructure() {
        return type.STRUCTURE;
    }

    public int getShieldStructure() {
        return type.SHIELD_STRUCTURE;
    }

    public int getSpeed() {
        return type.SPEED;
    }

    public int getResourcesCapacity() {
        return type.CAPACITY;
    }

    @Override
    public Resources getConstructCost() {
        return new Resources(type.METAL_COST, type.CRYSTALS_COST, type.DEUTERIUM_COST);
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
        double hours = getConstructCost().getAmount() / (type.CONSTRUCT_SPEED_VALUE * (1 + shipyard.getLevel()));
        return Duration.ofSeconds(Math.round(hours * 3600));

    }

    public void updateDependencies() {
        laserTechnology = fleetDepartment.getResearch(ResearchType.LASER);
        shipyard = fleetDepartment.getBuilding(BuildingType.SHIPYARD);
    }
}