package ru.spbstu.icc.kspt.inspace.service.documents.responses;


import ru.spbstu.icc.kspt.inspace.api.AShipModel;
import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;

public class Ship {

    private final ShipType type;
    private final int attack;
    private final int structure;
    private final int shieldStructure;
    private final int capacity;
    private final int speed;
    private final Resources constructionCost;
    private final int constructionDuration;

    public Ship(AShipModel shipModel) {
        this.type = shipModel.getType();
        this.attack = shipModel.getAttack();
        this.structure = shipModel.getStructure();
        this.shieldStructure = shipModel.getShieldStructure();
        this.capacity = shipModel.getResourcesCapacity();
        this.speed = shipModel.getSpeed();
        this.constructionCost = new Resources(shipModel.getConstructCost());
        this.constructionDuration = (int) shipModel.getConstructDuration().getSeconds();

    }

    public ShipType getType() {
        return type;
    }

    public int getAttack() {
        return attack;
    }

    public int getStructure() {
        return structure;
    }

    public int getShieldStructure() {
        return shieldStructure;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSpeed() {
        return speed;
    }

    public Resources getConstructionCost() {
        return constructionCost;
    }

    public int getConstructionDuration() {
        return constructionDuration;
    }
}
