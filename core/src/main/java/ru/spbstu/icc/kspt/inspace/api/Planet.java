package ru.spbstu.icc.kspt.inspace.api;

import ru.spbstu.icc.kspt.inspace.model.Galaxy;
import ru.spbstu.icc.kspt.inspace.model.Position;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.exception.ExcessCapacityException;
import ru.spbstu.icc.kspt.inspace.model.exception.FleetDetachException;
import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;
import ru.spbstu.icc.kspt.inspace.model.fleet.missions.Attack;
import ru.spbstu.icc.kspt.inspace.model.fleet.missions.Transportation;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;

import java.util.*;

public class Planet {
    private ru.spbstu.icc.kspt.inspace.model.Planet planet;

    Planet(ru.spbstu.icc.kspt.inspace.model.Planet planet) {
        this.planet = planet;
    }

    public Planet(String name, Position position) {
        planet = new ru.spbstu.icc.kspt.inspace.model.Planet(name, position);
    }

    public Planet(String name, Position position, int size) {
        planet = new ru.spbstu.icc.kspt.inspace.model.Planet(name, position, size);


    }
    public Position getPosition() {
        return planet.getPosition();
    }

    public Resources getResources() {
        return new Resources(planet.getResources());
    }

    public void balanceEnergyConsumption() {
        planet.balanceEnergyConsumption();
    }

    public int getEnergyProduction() {
        return planet.getEnergyProduction();
    }

    public int getEnergyConsumption() {
        return planet.getEnergyConsumption();
    }

    public int getEnergyLevel() {
        return planet.getEnergyLevel();
    }

    public double getProductionPower() {
        return planet.getProductionPower();
    }

    public int getNumberOfFields() {
        return planet.getNumberOfFields();
    }

    public int getNumberOfEmptyFields() {
        return planet.getNumberOfEmptyFields();
    }

    public String getName() {
        return planet.getName();
    }

    public Building getBuilding(BuildingType type) {
        return new Building(planet.getBuilding(type));
    }

    public Map<BuildingType, Building> getBuildings() {
        Map<BuildingType, Building> map = new EnumMap<>(BuildingType.class);
        planet.getBuildings().forEach((key, value) -> map.put(key, new Building(value)));
        return map;
    }

    public Upgrade getCurrentBuildingUpgrade() {
        return new Upgrade(planet.getCurrentBuildingUpgrade());
    }

    public Research getResearch(ResearchType type) {
        return new Research(planet.getResearch(type));
    }

    public Map<ResearchType, Research> getResearches() {
        Map<ResearchType, Research> map = new EnumMap<>(ResearchType.class);
        planet.getResearches().forEach((key, value) -> map.put(key, new Research(value)));
        return map;
    }

    public Upgrade getCurrentResearchUpgrade() {
        return new Upgrade(planet.getCurrentResearchUpgrade());
    }

    public Map<ShipType, Ship> getShips() {
        Map<ShipType, Ship> map = new EnumMap<>(ShipType.class);
        planet.getShips().forEach((key, value) -> map.put(key, new Ship(value)));
        return map;
    }

    public Construct getCurrentConstruct(){
        return new Construct(planet.getCurrentConstuct());
    }

    public Fleet getFleetOnPlanet() {
        return new Fleet(planet.getFleetOnPlanet());
    }

    public List<Mission> getMissions() {
        List<Mission> list = new ArrayList<>();
        planet.getMissions().forEach(m -> list.add(new Mission(m)));
        return list;
    }

    public List<Mission> getExternalMissions() {
        List<Mission> list = new ArrayList<>();
        planet.getExternalMissions().forEach(m -> list.add(new Mission(m)));
        return list;
    }

    public void startAttack(Position destination, Map<ShipType, Integer> numbersOfShips) throws FleetDetachException {
        ru.spbstu.icc.kspt.inspace.model.fleet.Fleet fleet =
                planet.getFleetOnPlanet().detachFleet(numbersOfShips);
        planet.startMission(new Attack(planet, Galaxy.getInstance().getPlanet(destination), fleet));
    }

    public void startTransportation(Position destination, Map<ShipType, Integer> numbersOfShips, int metal, int crystal, int deuterium ) throws FleetDetachException, ExcessCapacityException {
        ru.spbstu.icc.kspt.inspace.model.fleet.Fleet fleet =
                planet.getFleetOnPlanet().detachFleet(numbersOfShips);
        ru.spbstu.icc.kspt.inspace.model.Resources resources =
                new ru.spbstu.icc.kspt.inspace.model.Resources(metal, crystal, deuterium);
        fleet.addResources(resources);
        new Transportation(planet, Galaxy.getInstance().getPlanet(destination), fleet);
    }
}
