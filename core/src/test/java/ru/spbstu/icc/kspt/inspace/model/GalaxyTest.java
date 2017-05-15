package ru.spbstu.icc.kspt.inspace.model;


import org.junit.Test;
import ru.spbstu.icc.kspt.inspace.model.exception.PlanetDoesntExist;

import static org.junit.Assert.*;


public class GalaxyTest {

    Galaxy galaxy = Galaxy.getInstance();
    private Position position = new Position(5, 7);

    @Test
    public void testAdd() throws PlanetDoesntExist {
        galaxy.addPlanet(position, "nibiru");
        Planet gottenPlanet = (Planet) galaxy.getPlanet(position);
        assertTrue(gottenPlanet != null);
    }

    @Test(expected =PlanetDoesntExist.class)
    public void testDelete() throws PlanetDoesntExist {
        galaxy.deletePlanet(position);
        galaxy.getPlanet(position);
    }

    @Test(expected =PlanetDoesntExist.class)
    public void testWrongPositionGet() throws PlanetDoesntExist {
        galaxy.getPlanet(new Position(15, -2));
    }
}
