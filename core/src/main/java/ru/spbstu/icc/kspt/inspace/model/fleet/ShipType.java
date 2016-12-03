package ru.spbstu.icc.kspt.inspace.model.fleet;


public enum ShipType {
    FIGHTER(100, 100, 25, 200, 1000, 750, 0, 5000, 3000),
    SMALL_CARGO(10, 300, 100, 175, 1000, 1000, 100, 4500, 5000);

    ShipType(int attack, int structure, int shieldStructure, int speed, int metalCost,
             int crystalCost, int deuteriumCost, double constructSpeedValue, int capacity) {
        this.ATTACK = attack;
        this.STRUCTURE = structure;
        this.SHIELD_STRUCTURE = shieldStructure;
        this.SPEED = speed;
        this.METAL_COST = metalCost;
        this.CRYSTALS_COST = crystalCost;
        this.DEUTERIUM_COST = deuteriumCost;
        this.CONSTRUCT_SPEED_VALUE = constructSpeedValue;
        this.CAPACITY = capacity;
    }

    final protected int ATTACK;
    final protected int STRUCTURE;
    final protected int SHIELD_STRUCTURE;
    final protected int SPEED;
    final protected int METAL_COST;
    final protected int CRYSTALS_COST;
    final protected int DEUTERIUM_COST;
    final protected double CONSTRUCT_SPEED_VALUE;
    final protected int CAPACITY;

}
