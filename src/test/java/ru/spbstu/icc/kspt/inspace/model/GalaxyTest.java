package ru.spbstu.icc.kspt.inspace.model;


import org.junit.Test;
import static org.junit.Assert.*;

public class GalaxyTest {

    Galaxy galaxy = new Galaxy();
    Position position = new Position(5, 7);

    @Test
    public void testAdd() {
        Planet planet = new Planet();
        galaxy.addPlanet(planet, position);
        Planet gottenPlanet = galaxy.getPlanet(position);
        assertTrue(planet == gottenPlanet);
    }
    @Test
    public void testDelete() {
        galaxy.deletePlanet(position);
        assertNull(galaxy.getPlanet(position));
    }
}
