package ru.spbstu.icc.kspt.inspace.model.fleet;


import java.util.EnumMap;
import java.util.Map;

public class Fleet {

    private Map<ShipType, Integer> numbersOfShips = new EnumMap<>(ShipType.class);
    private Map<ShipType, Ship> ships;

    public Fleet(FleetDepartment department) {
        ships = department.getShips();
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

}
