package ru.spbstu.icc.kspt.inspace.model.fleet.missions;


import ru.spbstu.icc.kspt.inspace.api.AMission;
import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.fleet.Fleet;
import ru.spbstu.icc.kspt.inspace.model.utils.TimeAction;
import ru.spbstu.icc.kspt.inspace.model.utils.Time;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Mission extends TimeAction implements AMission {

    private LocalDateTime time;
    private Planet source;
    private Planet destination;
    private Fleet fleet;
    private MissionType type;

    public Mission(Planet source, Planet destination, Fleet fleet, MissionType type) {
        this.source = source;
        this.destination = destination;
        this.fleet = fleet;
        this.type = type;

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

    @Override
    public Planet getSource() {
        return source;
    }

    @Override
    public Planet getDestination() {
        return destination;
    }

    @Override
    public Fleet getFleet() {
        return fleet;
    }

    @Override
    public MissionType getType() {
        return type;
    }
}
