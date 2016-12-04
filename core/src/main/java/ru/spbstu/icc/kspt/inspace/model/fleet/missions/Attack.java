package ru.spbstu.icc.kspt.inspace.model.fleet.missions;


import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.resources.Resources;
import ru.spbstu.icc.kspt.inspace.model.exception.CapacityExcessException;
import ru.spbstu.icc.kspt.inspace.model.fleet.Fleet;

public class Attack extends Mission{

    public Attack(Planet source, Planet destination, Fleet fleet) {
        super(source, destination, fleet);
    }

    @Override
    protected void onExecute() {
        getFleet().attack(getDestination().getFleetOnPlanet());

        if (getFleet().getNumberOfShips() != 0) {
            int amount = getFleet().getCapacity() / 3;
            Resources loot = getDestination().getResources().takeResources(amount, amount, amount);
            try {
                getFleet().addResources(loot);
            } catch (CapacityExcessException e) {
                throw new AssertionError();
            }
            getSource().startMission(new Comeback(getDestination(), getSource(), getFleet()));
        }
    }
}
