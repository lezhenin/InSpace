package ru.spbstu.icc.kspt.inspace.model;



import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;
import ru.spbstu.icc.kspt.inspace.model.buildings.Building;
import ru.spbstu.icc.kspt.inspace.model.utils.Time;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;
import static ru.spbstu.icc.kspt.inspace.model.Planet.BuildingType;

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
    public void testUpgrade() {

        PowerMockito.mockStatic(Time.class);
        when(Time.now()).thenReturn(LocalDateTime.now().plus(Duration.ofMinutes(531)));

        Building building = planet.getBuilding(BuildingType.FACTORY);
        assertTrue(building.canBeUpgraded());
        assertEquals(building.getLevel(), 0);
        building.startUpgrade();

        PowerMockito.mockStatic(Time.class);
        when(Time.now()).thenReturn(LocalDateTime
                .now()
                .plus(Duration.ofMinutes(532))
                .plus(building.getUpgradeDuration()));
        planet.update();
        assertEquals(building.getLevel(), 1);
    }
}
