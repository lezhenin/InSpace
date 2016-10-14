package ru.spbstu.icc.kspt.inspace.model;


import org.junit.Test;
import static org.junit.Assert.*;

public class GalaxyTest {

    private Galaxy galaxy = new Galaxy();
    private Position position = new Position(5, 7);

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

    @Test(expected = IndexOutOfBoundsException.class)
    public void testWrongPositionGet() {
        galaxy.getPlanet(new Position(15, -2));
    }
}
