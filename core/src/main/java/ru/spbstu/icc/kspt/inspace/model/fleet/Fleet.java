package ru.spbstu.icc.kspt.inspace.model.fleet;


import ru.spbstu.icc.kspt.inspace.api.AFleet;
import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.resources.Resources;
import ru.spbstu.icc.kspt.inspace.model.exception.CapacityExcessException;
import ru.spbstu.icc.kspt.inspace.model.exception.FleetDetachException;

import java.util.EnumMap;
import java.util.Map;

public class Fleet implements AFleet {

    private Map<ShipType, Integer> numbersOfShips = new EnumMap<>(ShipType.class);
    private Map<ShipType, ShipModel> ships;
    private FleetDepartment department;
    private Resources resources = new Resources(0, 0, 0);

    @Override
    public Resources getResources() {
        return resources;
    }

    public Planet getOwner() {
       return department.getPlanet();
    }

    Fleet(FleetDepartment department) {
        this.department = department;
        ships = department.getShips();
        for(ShipType value: ShipType.values()) {
            numbersOfShips.put(value, 0);
        }
    }

    void addShips(ShipType type, int number) {
        Integer currentNumber = numbersOfShips.get(type);
        numbersOfShips.put(type, currentNumber + number);
    }

    public Fleet detachFleet() {
        Fleet fleet = new Fleet(department);
        fleet.attachFleet(this);
        return fleet;
    }

    public Fleet detachFleet(Map<ShipType, Integer> numbersOfShips) throws FleetDetachException {

        for(Map.Entry<ShipType, Integer> entry: numbersOfShips.entrySet()) {
            if (this.numbersOfShips.get(entry.getKey()) < entry.getValue()) {
                throw new FleetDetachException(this.numbersOfShips, numbersOfShips);
            }
        }

        Fleet fleet = new Fleet(department);

        for(Map.Entry<ShipType, Integer> entry: numbersOfShips.entrySet()) {
            fleet.addShips(entry.getKey(), entry.getValue());
            Integer number = this.numbersOfShips.get(entry.getKey());
            this.numbersOfShips.put(entry.getKey(), number - entry.getValue());
        }


        return fleet;
    }

    public void attachFleet(Fleet fleet) {
        for(Map.Entry<ShipType, Integer> entry: fleet.numbersOfShips.entrySet()) {
            addShips(entry.getKey(), entry.getValue());
            fleet.numbersOfShips.put(entry.getKey(), 0);
        }
    }

    public void putResources(Resources resources) throws CapacityExcessException {
        if (resources.getAmount() > getCapacity()) {
            throw new CapacityExcessException(getCapacity(), resources.getAmount());
        }
        this.resources.putResources(resources);
    }

    public Resources takeAllResources() {
        return resources.takeAllResources();
    }

    public void takeResources(Resources resources) {
        this.resources.takeResources(resources);
    }

    @Override
    public Map<ShipType, Integer> getNumbersOfShips() {
        return numbersOfShips;
    }

    @Override
    public int getCapacity() {
        int capacity = 0;
        for (Map.Entry<ShipType, Integer> entry : numbersOfShips.entrySet()) {
            capacity += entry.getValue() * entry.getKey().CAPACITY;
        }
        return capacity;
    }

    @Override
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

    @Override
    public int getNumberOfShips() {
        int number = 0;
        for(Integer n: numbersOfShips.values()) {
            number += n;
        }
        return number;
    }

    @Override
    public int getSummaryStructure() {
        int structure = 0;
        for (Map.Entry<ShipType, Integer> entry: numbersOfShips.entrySet()) {
            ShipModel ship = ships.get(entry.getKey());
            structure += (ship.getStructure() + ship.getShieldStructure())  * entry.getValue();
        }
        return structure;
    }

    @Override
    public int getSummaryAttack() {
        int attack = 0;
        for (Map.Entry<ShipType, Integer> entry: numbersOfShips.entrySet()) {
            ShipModel ship = ships.get(entry.getKey());
            attack += ship.getAttack() * entry.getValue();
        }
        return attack;
    }



    public void attack(Fleet fleet) {

        //TODO make smaller methods

        assert (department != fleet.department);

        if (getNumberOfShips() == 0 || fleet.getNumberOfShips() == 0) {
            return;
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
