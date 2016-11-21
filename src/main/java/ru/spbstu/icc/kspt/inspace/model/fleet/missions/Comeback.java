package ru.spbstu.icc.kspt.inspace.model.fleet.missions;

import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.fleet.Fleet;

public class Comeback extends Mission{

    public Comeback(Planet source, Planet destination, Fleet fleet) {
        super(source, destination, fleet);
    }

    @Override
    protected void onExecute() {
        getDestination().getResources().addResources(getFleet().takeAllResources());
        getDestination().getFleetOnPlanet().addFleet(getFleet());
    }
}
