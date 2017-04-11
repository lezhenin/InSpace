package ru.spbstu.icc.kspt.inspace.api;

import ru.spbstu.icc.kspt.inspace.model.Position;
import ru.spbstu.icc.kspt.inspace.model.buildings.*;
import ru.spbstu.icc.kspt.inspace.model.exception.CapacityExcessException;
import ru.spbstu.icc.kspt.inspace.model.exception.FleetDetachException;
import ru.spbstu.icc.kspt.inspace.model.exception.PlanetDoesntExist;
import ru.spbstu.icc.kspt.inspace.model.fleet.*;
import ru.spbstu.icc.kspt.inspace.model.research.*;

import java.util.List;
import java.util.Map;


public interface APlanet {

    Position getPosition();

    AResources getResources();

    int getEnergyProduction();

    int getEnergyConsumption();

    int getEnergyLevel();

    double getProductionPower();

    int getNumberOfFields();

    int getNumberOfEmptyFields();

    String getName();

    ABuilding getBuilding(BuildingType type);

    Map<BuildingType, ? extends ABuilding> getBuildings();

    AUpgrade getCurrentBuildingUpgrade();

    AResearch getResearch(ResearchType type);

    Map<ResearchType, ? extends AResearch> getResearches();

    AUpgrade getCurrentResearchUpgrade();

    AConstruct getCurrentConstruct();

    Map<ShipType, ? extends AShipModel> getShips();

    AFleet getFleetOnPlanet();

    void startAttack(Position destination, Map<ShipType, Integer> numbersOfShips)
            throws FleetDetachException, PlanetDoesntExist;

    public void startTransportation(Position destination, Map<ShipType, Integer> numbersOfShips, int metal, int crystal, int deuterium)
            throws FleetDetachException, CapacityExcessException, PlanetDoesntExist;

    List<? extends AMission> getMissions();

    List<? extends AMission> getExternalMissions();

    void update();

    void rename(String newName);
}
