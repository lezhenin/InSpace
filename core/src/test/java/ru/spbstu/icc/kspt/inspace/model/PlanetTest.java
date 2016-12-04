package ru.spbstu.icc.kspt.inspace.model;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;
import ru.spbstu.icc.kspt.inspace.model.buildings.Building;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.exception.ConstructException;
import ru.spbstu.icc.kspt.inspace.model.exception.UpgradeException;
import ru.spbstu.icc.kspt.inspace.model.fleet.Fleet;
import ru.spbstu.icc.kspt.inspace.model.fleet.Ship;
import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;
import ru.spbstu.icc.kspt.inspace.model.fleet.missions.Attack;
import ru.spbstu.icc.kspt.inspace.model.fleet.missions.Comeback;
import ru.spbstu.icc.kspt.inspace.model.fleet.missions.Mission;
import ru.spbstu.icc.kspt.inspace.model.research.Research;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;
import ru.spbstu.icc.kspt.inspace.model.resources.Resources;
import ru.spbstu.icc.kspt.inspace.model.utils.Time;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;


@RunWith(PowerMockRunner.class)
@PrepareForTest(Time.class)
public class PlanetTest {

    private Planet planet = new Planet("Nibiru", new Position(2, 4));
    private Planet anotherPlanet = new Planet("Another", new Position(3, 5));

    @Test
    public void testUpdate() {

        assertEquals(planet.getResources(), new Resources(0, 0, 0));

        PowerMockito.mockStatic(Time.class);
        when(Time.now()).thenReturn(LocalDateTime.now().plus(Duration.ofMinutes(531)));

        assertEquals(planet.getResources(), new Resources(266, 221, 177));
    }

    @Test
    public void testGetBuildings() {
        PowerMockito.mockStatic(Time.class);
        when(Time.now()).thenReturn(LocalDateTime.now().plus(Duration.ofMinutes(531)));
        System.out.println("Planet: " + planet.getName());
        System.out.println("Resources: " + planet.getResources());
        System.out.println("Energy level: " + planet.getEnergyLevel());
        System.out.println(planet.getNumberOfEmptyFields() + " fields are empty");
        System.out.println("Buildings:");
        System.out.println();
        for(Map.Entry<BuildingType, Building> entry: planet.getBuildings().entrySet()) {
            Building building = entry.getValue();
            System.out.println(entry.getKey());
            System.out.println("Level: " + building.getLevel());
            System.out.println("Cost: " + building.getUpgradeCost());
            System.out.println("Duration: " + building.getUpgradeDuration());
            System.out.println();
        }
    }

    @Test
    public void testResearchUpgrade() throws UpgradeException {
        PowerMockito.mockStatic(Time.class);
        when(Time.now()).thenReturn(LocalDateTime.now().plus(Duration.ofMinutes(531)));

        Research research = planet.getResearch(ResearchType.ENERGY);
        assertTrue(research.canBeUpgraded());
        assertEquals(research.getLevel(), 0);
        research.startUpgrade();

        PowerMockito.mockStatic(Time.class);
        when(Time.now()).thenReturn(LocalDateTime
                .now()
                .plus(Duration.ofMinutes(531))
                .plus(research.getUpgradeDuration()));
        planet.update();
        assertEquals(research.getLevel(), 1);
        assertEquals(planet.getResources(), new Resources(226, 160, 130));
    }

    @Test
    public void testBuildingUpgrade() throws UpgradeException {

        PowerMockito.mockStatic(Time.class);
        when(Time.now()).thenReturn(LocalDateTime.now().plus(Duration.ofMinutes(531)));

        Building building = planet.getBuilding(BuildingType.FACTORY);
        assertTrue(building.canBeUpgraded());
        assertEquals(building.getLevel(), 0);
        building.startUpgrade();

        PowerMockito.mockStatic(Time.class);
        when(Time.now()).thenReturn(LocalDateTime
                .now()
                .plus(Duration.ofMinutes(531))
                .plus(building.getUpgradeDuration()));
        planet.update();
        assertEquals(building.getLevel(), 1);
        assertEquals(planet.getResources(), new Resources(208, 172, 178));
    }

    @Test
    public void testEnergySystem() throws UpgradeException {

        assertEquals(planet.getEnergyLevel(), 0);

        PowerMockito.mockStatic(Time.class);
        when(Time.now()).thenReturn(LocalDateTime.now().plus(Duration.ofMinutes(531)));
        planet.getBuilding(BuildingType.POWER_STATION).startUpgrade();

        when(Time.now()).thenReturn(LocalDateTime.now().plus(Duration.ofMinutes(540)));
        assertEquals(planet.getEnergyLevel(), 100);
        assertEquals(planet.getEnergyProduction(), 100);
        assertEquals(planet.getEnergyConsumption(), 0);
        planet.getBuilding(BuildingType.DEUTERIUM_MINE).startUpgrade();

        when(Time.now()).thenReturn(LocalDateTime.now().plus(Duration.ofMinutes(550)));
        assertEquals(planet.getEnergyLevel(), 38);
        assertEquals(planet.getEnergyProduction(), 100);
        assertEquals(planet.getEnergyConsumption(), 62);
        planet.getBuilding(BuildingType.CRYSTAL_MINE).startUpgrade();

        when(Time.now()).thenReturn(LocalDateTime.now().plus(Duration.ofMinutes(560)));
        assertEquals(planet.getEnergyLevel(), 0);
        assertEquals(planet.getEnergyProduction(), 100);
        assertEquals(planet.getEnergyConsumption(), 100);
        assertTrue(planet.getProductionPower() < 1);

        planet.balanceEnergyConsumption();
        assertEquals(planet.getEnergyLevel(), 0);
        assertEquals(planet.getEnergyConsumption(), planet.getEnergyProduction());
        assertEquals(planet.getEnergyConsumption(), 100);


    }

    @Test
    public void testFleets() throws ConstructException {

        planet.getResources().addResources(new Resources(100000, 100000, 100000));
        anotherPlanet.getResources().addResources(new Resources(100000, 100000, 100000));

        Iterator<Map.Entry<ShipType, Ship>> iterator = planet.getShips().entrySet().iterator();
        iterator.next().getValue().startConstruction(15);

        iterator = anotherPlanet.getShips().entrySet().iterator();
        iterator.next().getValue().startConstruction(14);

        PowerMockito.mockStatic(Time.class);
        when(Time.now()).thenReturn(LocalDateTime.now().plus(Duration.ofMinutes(531)));

        Fleet fleet1 = planet.getFleetOnPlanet().detachFleet();
        assertEquals(15, fleet1.getNumberOfShips());
        assertEquals(0, planet.getFleetOnPlanet().getNumberOfShips());

        Fleet fleet2 = anotherPlanet.getFleetOnPlanet().detachFleet();
        assertEquals(14, fleet2.getNumberOfShips());
        assertEquals(0, anotherPlanet.getFleetOnPlanet().getNumberOfShips());

        fleet1.attack(fleet2);
        assertEquals(13, fleet1.getNumberOfShips());
        assertEquals(0, fleet2.getNumberOfShips());

        planet.getFleetOnPlanet().addFleet(fleet1);
        anotherPlanet.getFleetOnPlanet().addFleet(fleet2);
        assertEquals(13, planet.getFleetOnPlanet().getNumberOfShips());
        assertEquals(0, anotherPlanet.getFleetOnPlanet().getNumberOfShips());

    }

    @Test
    public void testMissions() throws ConstructException {

        planet.getResources().addResources(new Resources(100000, 100000, 100000));
        anotherPlanet.getResources().addResources(new Resources(100000, 100000, 100000));

        Iterator<Map.Entry<ShipType, Ship>> iterator;
        iterator = planet.getShips().entrySet().iterator();
        iterator.next().getValue().startConstruction(15);
        iterator = anotherPlanet.getShips().entrySet().iterator();
        iterator.next().getValue().startConstruction(14);

        PowerMockito.mockStatic(Time.class);
        when(Time.now()).thenReturn(LocalDateTime.now().plus(Duration.ofMinutes(531)));

        planet.update();
        anotherPlanet.update();

        //TODO empty fleets
        Mission attack = new Attack(planet, anotherPlanet, planet.getFleetOnPlanet().detachFleet());
        planet.startMission(attack);

        assertTrue(!planet.getMissions().isEmpty());
        Mission currentMission = planet.getMissions().get(0);
        assertTrue(currentMission == anotherPlanet.getExternalMissions().get(0));
        assertTrue(currentMission.getClass().equals(Attack.class));
        assertTrue(anotherPlanet == currentMission.getDestination());
        assertTrue(planet == currentMission.getSource());
        assertEquals(15, currentMission.getFleet().getNumberOfShips());

        when(Time.now()).thenReturn(currentMission.getTime().plus(Duration.ofMinutes(1)));

        currentMission = planet.getMissions().get(0);
        assertTrue(anotherPlanet.getExternalMissions().isEmpty());
        assertTrue(planet.getExternalMissions().isEmpty());
        assertTrue(currentMission.getClass().equals(Comeback.class));
        assertTrue(anotherPlanet == currentMission.getSource());
        assertTrue(planet == currentMission.getDestination());
        assertEquals(13, currentMission.getFleet().getNumberOfShips());

    }
}
