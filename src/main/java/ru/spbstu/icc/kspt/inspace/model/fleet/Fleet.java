package ru.spbstu.icc.kspt.inspace.model.fleet;

import ru.spbstu.icc.kspt.inspace.model.Planet;

import java.util.EnumMap;
import java.util.Map;

public class Fleet {

    private Map<ShipType, Integer> shipNumbers = new EnumMap<>(ShipType.class);
    private Planet planet;

    public Fleet(Planet planet) {
        this.planet = planet;
    }

    private Fleet(Planet planet, Map<ShipType, Ship> ships) {
        this(planet);

    }

    public void add(Fleet fleet) {

    }


}
