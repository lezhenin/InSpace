package ru.spbstu.icc.kspt.inspace.model.fleet;


import ru.spbstu.icc.kspt.inspace.model.Resources;

import java.util.EnumMap;
import java.util.Map;

public class Fleet {

    private Map<ShipType, Integer> numbersOfShips = new EnumMap<>(ShipType.class);
    private Map<ShipType, Ship> ships;
    private FleetDepartment department;
    private Resources resources = new Resources(0, 0, 0);

    public Fleet(FleetDepartment department) {
        this.department = department;
        ships = department.getShips();
        for(ShipType value: ShipType.values()) {
            numbersOfShips.put(value, 0);
        }
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

    public void addResources(Resources resources) {
        if (resources.getAmount() > getCapacity()) {
            //TODO exception
        }
        this.resources.addResources(resources);
    }

    public Resources takeAllResources() {
        return resources.takeAllResources();
    }

    public void takeResources(Resources resources) {
        this.resources.takeResources(resources);
    }

    public Map<ShipType, Integer> getNumbersOfShips() {
        return numbersOfShips;
    }

    public int getCapacity() {
        int capacity = 0;
        for (Map.Entry<ShipType, Integer> entry : numbersOfShips.entrySet()) {
            capacity += entry.getValue() * entry.getKey().capacity;
        }
        return capacity;
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

        while(structure > 0 && enemyStructure > 0) {
            double tempStructure;

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

        if (enemyStructure == 0) {
            calculateNumbersOfShips(this, fleet, structure);
        } else {
            calculateNumbersOfShips(fleet, this, structure);
        }
    }

    private void calculateNumbersOfShips(Fleet winner, Fleet looser, double winnerStructure) {
        for(Map.Entry<ShipType, Integer> entry: winner.numbersOfShips.entrySet()) {
            Integer number = entry.getValue();
            number = (int)Math.round(number * winnerStructure/getSummaryStructure());
            winner.numbersOfShips.put(entry.getKey(), number);
        }
        for(Map.Entry<ShipType, Integer> entry: looser.numbersOfShips.entrySet()) {
            looser.numbersOfShips.put(entry.getKey(), 0);
        }
    }

}
