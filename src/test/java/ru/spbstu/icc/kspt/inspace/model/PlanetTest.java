package ru.spbstu.icc.kspt.inspace.model;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;
import ru.spbstu.icc.kspt.inspace.model.buildings.Building;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.research.Research;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;
import ru.spbstu.icc.kspt.inspace.model.utils.Time;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Time.class)
public class PlanetTest {

    private Planet planet = new Planet("Nibiru");

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
        System.out.println(planet.getEmptyFields() + " fields are empty");
        System.out.println("Buildings:");
        System.out.println();
        for(Map.Entry<BuildingType, Building> entry: planet.getBuildings()) {
            Building building = entry.getValue();
            System.out.println(entry.getKey());
            System.out.println("Level: " + building.getLevel());
            System.out.println("Cost: " + building.getUpgradeCost());
            System.out.println("Duration: " + building.getUpgradeDuration());
            System.out.println();
        }
    }

    @Test
    public void testResearchUpgrade() {
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
        assertEquals(planet.getResources(), new Resources(231, 160, 115));
    }

    @Test
    public void testBuildingUpgrade() {

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
    public void testEnergySystem() {
        
        PowerMockito.mockStatic(Time.class);

        assertEquals(planet.getEnergyLevel(), 0);

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
        assertEquals(planet.getEnergyLevel(), -18);
        assertEquals(planet.getEnergyProduction(), 100);
        assertEquals(planet.getEnergyConsumption(), 118);

        planet.balanceEnergyConsumption();
        assertEquals(planet.getEnergyLevel(), 0);
        assertEquals(planet.getEnergyConsumption(), planet.getEnergyProduction());
        assertEquals(planet.getEnergyConsumption(), 100);


    }
}
