package ru.spbstu.icc.kspt.inspace.service.documents.requests;


import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;
import ru.spbstu.icc.kspt.inspace.model.fleet.missions.MissionType;

import java.util.Map;

public class MissionStartInfo {

    private Map<ShipType, Integer> numbersOfShips;

    private int numberOfPlanet;
    private int numberOfSystem;

    private int metal;
    private int crystals;
    private int deuterium;

    private MissionType type;

    public MissionStartInfo() {
    }

    public Map<ShipType, Integer> getNumbersOfShips() {
        return numbersOfShips;
    }

    public void setNumbersOfShips(Map<ShipType, Integer> numbersOfShips) {
        this.numbersOfShips = numbersOfShips;
    }

    public int getNumberOfPlanet() {
        return numberOfPlanet;
    }

    public void setNumberOfPlanet(int numberOfPlanet) {
        this.numberOfPlanet = numberOfPlanet;
    }

    public int getNumberOfSystem() {
        return numberOfSystem;
    }

    public void setNumberOfSystem(int numberOfSystem) {
        this.numberOfSystem = numberOfSystem;
    }

    public int getMetal() {
        return metal;
    }

    public void setMetal(int metal) {
        this.metal = metal;
    }

    public int getCrystals() {
        return crystals;
    }

    public void setCrystals(int crystals) {
        this.crystals = crystals;
    }

    public int getDeuterium() {
        return deuterium;
    }

    public void setDeuterium(int deuterium) {
        this.deuterium = deuterium;
    }

    public MissionType getType() {
        return type;
    }

    public void setType(MissionType type) {
        this.type = type;
    }
}
