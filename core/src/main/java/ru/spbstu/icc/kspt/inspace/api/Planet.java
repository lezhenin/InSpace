package ru.spbstu.icc.kspt.inspace.api;

import ru.spbstu.icc.kspt.inspace.model.Galaxy;
import ru.spbstu.icc.kspt.inspace.model.Position;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.exception.CapacityExcessException;
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

    public void rename(String name) {
        planet.rename(name);
    }

    public Building getBuilding(BuildingType type) {
        return new Building(planet.getBuilding(type));
    }

    public Map<BuildingType, Building> getBuildings() {
        Map<BuildingType, Building> map = new EnumMap<>(BuildingType.class);
        planet.getBuildings().forEach((key, value) -> map.put(key, new Building(value)));
        return map;
    }

    public Optional<Upgrade> getCurrentBuildingUpgrade() {
        Optional<Upgrade> upgrade;
        ru.spbstu.icc.kspt.inspace.model.utils.Upgrade currentBuildingUpgrade = planet.getCurrentBuildingUpgrade();
        if (currentBuildingUpgrade != null) {
            upgrade = Optional.of(new Upgrade(currentBuildingUpgrade));
        } else {
            upgrade = Optional.empty();
        }
        return upgrade;
    }

    public Research getResearch(ResearchType type) {
        return new Research(planet.getResearch(type));
    }

    public Map<ResearchType, Research> getResearches() {
        Map<ResearchType, Research> map = new EnumMap<>(ResearchType.class);
        planet.getResearches().forEach((key, value) -> map.put(key, new Research(value)));
        return map;
    }

    public Optional<Upgrade> getCurrentResearchUpgrade() {
        Optional<Upgrade> upgrade;
        ru.spbstu.icc.kspt.inspace.model.utils.Upgrade currentResearchUpgrade = planet.getCurrentResearchUpgrade();
        if (currentResearchUpgrade != null) {
            upgrade = Optional.of(new Upgrade(currentResearchUpgrade));
        } else {
            upgrade = Optional.empty();
        }
        return upgrade;
    }

    public Map<ShipType, Ship> getShips() {
        Map<ShipType, Ship> map = new EnumMap<>(ShipType.class);
        planet.getShips().forEach((key, value) -> map.put(key, new Ship(value)));
        return map;
    }

    public Optional<Construct> getCurrentConstruct(){
        Optional<Construct> construct;
        ru.spbstu.icc.kspt.inspace.model.utils.Construct currentConstruct = planet.getCurrentConstruct();
        if(currentConstruct != null) {
            construct = Optional.of(new Construct(currentConstruct));
        } else {
            construct = Optional.empty();
        }
        return construct;
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
        planet.startAttack(destination, numbersOfShips);
    }

    public void startTransportation(Position destination, Map<ShipType, Integer> numbersOfShips, int metal, int crystal, int deuterium ) throws FleetDetachException, CapacityExcessException {
      planet.startTransportation(destination, numbersOfShips, metal, crystal, deuterium);
    }
}
