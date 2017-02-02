package ru.spbstu.icc.kspt.inspace.api;

import ru.spbstu.icc.kspt.inspace.model.Position;
import ru.spbstu.icc.kspt.inspace.model.buildings.*;
import ru.spbstu.icc.kspt.inspace.model.buildings.Building;
import ru.spbstu.icc.kspt.inspace.model.exception.CapacityExcessException;
import ru.spbstu.icc.kspt.inspace.model.exception.FleetDetachException;
import ru.spbstu.icc.kspt.inspace.model.fleet.*;
import ru.spbstu.icc.kspt.inspace.model.fleet.Fleet;
import ru.spbstu.icc.kspt.inspace.model.fleet.missions.*;
import ru.spbstu.icc.kspt.inspace.model.fleet.missions.Mission;
import ru.spbstu.icc.kspt.inspace.model.research.*;
import ru.spbstu.icc.kspt.inspace.model.research.Research;
import ru.spbstu.icc.kspt.inspace.model.resources.*;
import ru.spbstu.icc.kspt.inspace.model.utils.*;
import ru.spbstu.icc.kspt.inspace.model.utils.Construct;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgrade;

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

    Map<ShipType, ? extends AShip> getShips();

    AFleet getFleetOnPlanet();

    void startAttack(Position destination, Map<ShipType, Integer> numbersOfShips)
            throws FleetDetachException;

    public void startTransportation(Position destination, Map<ShipType, Integer> numbersOfShips, int metal, int crystal, int deuterium)
            throws FleetDetachException, CapacityExcessException;

    List<? extends AMission> getMissions();

    List<? extends AMission> getExternalMissions();

    void update();

    void rename(String newName);
}
