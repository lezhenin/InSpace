package ru.spbstu.icc.kspt.inspace.service.documents;


import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;
import ru.spbstu.icc.kspt.inspace.model.fleet.missions.MissionType;

import java.util.Map;

public class MissionStartInfo {

    private Map<ShipType, Integer> numbersOfShips;
    private Position destination;
    private Resources resources;
    private MissionType type;

    public MissionStartInfo(Map<ShipType, Integer> numbersOfShips, Position destination,
                            Resources resources, MissionType type) {
        this.numbersOfShips = numbersOfShips;
        this.destination = destination;
        this.resources = resources;
        this.type = type;
    }

    public MissionStartInfo() {
    }

    public void setNumbersOfShips(Map<ShipType, Integer> numbersOfShips) {
        this.numbersOfShips = numbersOfShips;
    }

    public void setDestination(Position destination) {
        this.destination = destination;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public void setType(MissionType type) {
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
