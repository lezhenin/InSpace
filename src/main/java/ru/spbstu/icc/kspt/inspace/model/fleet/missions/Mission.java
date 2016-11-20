package ru.spbstu.icc.kspt.inspace.model.fleet.missions;


import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.fleet.Fleet;
import ru.spbstu.icc.kspt.inspace.model.utils.TimeAction;
import ru.spbstu.icc.kspt.inspace.model.utils.Time;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Mission extends TimeAction {

    private LocalDateTime time;
    private Planet source;
    private Planet destination;
    private Fleet fleet;

    public Mission(Planet source, Planet destination, Fleet fleet) {
        this.source = source;
        this.destination = destination;
        this.fleet = fleet;

        int hours = source.getPosition().getDistanceTo(destination.getPosition()) / fleet.getSpeed();
        time = Time.now().plus(Duration.ofHours(hours));
    }

    public Mission(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public LocalDateTime getTime() {
        return time;
    }

    public Planet getSource() {
        return source;
    }

    public Planet getDestination() {
        return destination;
    }

    public Fleet getFleet() {
        return fleet;
    }
}
