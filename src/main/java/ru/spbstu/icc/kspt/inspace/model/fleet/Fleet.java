package ru.spbstu.icc.kspt.inspace.model.fleet;


import java.util.EnumMap;
import java.util.Map;

public class Fleet {

    private Map<ShipType, Integer> numbersOfShips = new EnumMap<>(ShipType.class);
    private Map<ShipType, Ship> ships;
    private FleetDepartment department;

    public Fleet(FleetDepartment department) {
        ships = department.getShips();
        this.department = department;
    }

    private Fleet(Map<ShipType, Ship> ships) {
        this.ships = ships;

    }

    void addShips(ShipType type, int number) {
        Integer currentNumber = numbersOfShips.get(type);
        numbersOfShips.put(type, currentNumber + number);
    }

    public void addFleet(Fleet fleet) {
        for(Map.Entry<ShipType, Integer> entry: fleet.numbersOfShips.entrySet()) {
            addShips(entry.getKey(), entry.getValue());
            fleet.numbersOfShips.put(entry.getKey(), 0);
        }
    }

    public int getSpeed() {
        int minSpeed = 0;

        for (Map.Entry<ShipType, Integer> entry: numbersOfShips.entrySet()) {
            if (entry.getValue() != 0) {
                minSpeed = ships.get(entry.getKey()).getSpeed();
            }
        }

        for (Map.Entry<ShipType, Integer> entry: numbersOfShips.entrySet()) {
            if (entry.getValue() != 0 && minSpeed > ships.get(entry.getKey()).getSpeed()) {
                minSpeed = ships.get(entry.getKey()).getSpeed();
            }
        }

        return minSpeed;
    }

    public int getNumberOfShips() {
        int number = 0;
        for(Integer n: numbersOfShips.values()) {
            number += n;
        }
        return number;
    }

    public int getSummaryStructure() {
        int structure = 0;
        for (Map.Entry<ShipType, Integer> entry: numbersOfShips.entrySet()) {
            Ship ship = ships.get(entry.getKey());
            structure += (ship.getStructure() + ship.getShieldStructure())  * entry.getValue();
        }
        return structure;
    }

    public int getSummaryAttack() {
        int attack = 0;
        for (Map.Entry<ShipType, Integer> entry: numbersOfShips.entrySet()) {
            Ship ship = ships.get(entry.getKey());
            attack += ship.getAttack() * entry.getValue();
        }
        return attack;
    }



    public void attack(Fleet fleet) {

        //TODO make smaller methods

        if (getNumberOfShips() == 0 || fleet.getNumberOfShips() == 0) {
            return;
        }

        if (department == fleet.department) {
            //TODO exception
        }

        int enemyAttack = fleet.getSummaryAttack();
        int enemyStructure = fleet.getSummaryStructure();
        int attack = getSummaryAttack();
        int structure = getSummaryStructure();
        double tempStructure = 0;

        while(structure > 0 || enemyStructure > 0) {

            tempStructure = enemyStructure - attack;
            if (tempStructure < 0) {
                tempStructure = 0;
            }
            enemyAttack = (int)(enemyAttack * tempStructure / enemyStructure);
            enemyStructure = (int)tempStructure;

            tempStructure = structure - enemyAttack;
            if (tempStructure < 0) {
                tempStructure = 0;
            }
            attack = (int)(attack * tempStructure / structure);
            structure = (int)tempStructure;
        }
        //TODO finish
        if (enemyStructure == 0) {

            for(Map.Entry<ShipType, Integer> entry: fleet.numbersOfShips.entrySet()) {
                fleet.numbersOfShips.put(entry.getKey(), 0);
            }
            for(Map.Entry<ShipType, Integer> entry: numbersOfShips.entrySet()) {
                Integer number = entry.getValue();
                numbersOfShips.put(entry.getKey(), number);
            }

        } else {
            for(Map.Entry<ShipType, Integer> entry: numbersOfShips.entrySet()) {
                numbersOfShips.put(entry.getKey(), 0);
            }

            for(Map.Entry<ShipType, Integer> entry: fleet.numbersOfShips.entrySet()) {
                Integer number = entry.getValue();
                fleet.numbersOfShips.put(entry.getKey(), number);
            }
        }
    }

}
