package ru.spbstu.icc.kspt.inspace.model;


import org.junit.Test;
import ru.spbstu.icc.kspt.inspace.model.exception.PlanetDoesntExist;

import static org.junit.Assert.*;


public class GalaxyTest {

    Galaxy galaxy = Galaxy.getInstance();
    private Position position = new Position(5, 7);

    @Test
    public void testAdd() throws PlanetDoesntExist {
        Planet planet = new Planet("MyPlanet", position);
        Planet gottenPlanet = (Planet) galaxy.getPlanet(position);
        assertTrue(planet == gottenPlanet);
    }

    @Test
    public void testDelete() throws PlanetDoesntExist {
        galaxy.deletePlanet(position);
        assertNull(galaxy.getPlanet(position));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testWrongPositionGet() throws PlanetDoesntExist {
        galaxy.getPlanet(new Position(15, -2));
    }
}
