package ru.spbstu.icc.kspt.inspace.model;


import org.junit.Test;
import ru.spbstu.icc.kspt.inspace.model.buildings.Building;

import static ru.spbstu.icc.kspt.inspace.model.Planet.BuildingType;

import java.util.Map;

public class PlanetTest {

    private Planet planet = new Planet();

    @Test
    public void testUpdate() {

        System.out.println(planet.getResources());

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        planet.update();
        System.out.println(planet.getResources());
    }

    @Test
    public void testGetBuildings() {
        for(Map.Entry<BuildingType, Building> entry: planet.getBuildings()) {
            Building building = entry.getValue();
            System.out.println(entry.getKey());
            System.out.println("Level: " + building.getLevel());
            System.out.println("Cost: " + building.getUpgradeCost());
            System.out.println("Duration: " + building.getUpgradeDuration());
            System.out.println();
        }
    }
}
