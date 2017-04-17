package ru.spbstu.icc.kspt.inspace.service.documents;


import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;
import ru.spbstu.icc.kspt.inspace.model.fleet.missions.MissionType;

import java.util.Map;

public class MissionStartInfo {

    private final Map<ShipType, Integer> numbersOfShips;
    private final Position destination;
    private final Resources resources;
    private final MissionType type;

    public MissionStartInfo(Map<ShipType, Integer> numbersOfShips, Position destination,
                            Resources resources, MissionType type) {
        this.numbersOfShips = numbersOfShips;
        this.destination = destination;
        this.resources = resources;
        this.type = type;
    }

    public Map<ShipType, Integer> getNumbersOfShips() {
        return numbersOfShips;
    }

    public Position getDestination() {
        return destination;
    }

    public Resources getResources() {
        return resources;
    }

    public MissionType getType() {
        return type;
    }
}
