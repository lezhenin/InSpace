package ru.spbstu.icc.kspt.inspace.model.fleet;


public enum ShipType {
    FIGHTER(100, 100, 25, 200, 1000, 750, 0, 500, 3000),
    SMALL_CARGO(10, 300, 100, 175, 1000, 1000, 100, 450, 5000);

    ShipType(int attack, int structure, int shieldStructure, int speed, int metalCost,
             int crystalCost, int deuteriumCost, double constructSpeedValue, int capacity) {
        this.attack = attack;
        this.structure = structure;
        this.shieldStructure = shieldStructure;
        this.speed = speed;
        this.metalCost = metalCost;
        this.crystalCost = crystalCost;
        this.deuteriumCost = deuteriumCost;
        this.constructSpeedValue = constructSpeedValue;
        this.capacity = capacity;
    }

    protected int attack;
    protected int structure;
    protected int shieldStructure;
    protected int speed;
    protected int metalCost;
    protected int crystalCost;
    protected int deuteriumCost;
    protected double constructSpeedValue;
    protected int capacity;

}
