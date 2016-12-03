package ru.spbstu.icc.kspt.inspace.model.utils;

import ru.spbstu.icc.kspt.inspace.model.Planet;

abstract public class Department {
    protected Planet planet;

    public Department(Planet planet) {
        this.planet = planet;
    }

    protected void updatePlanet() {
        planet.update();
    }
}
