package ru.spbstu.icc.kspt.inspace.api;

import ru.spbstu.icc.kspt.inspace.api.Planet;
import ru.spbstu.icc.kspt.inspace.model.fleet.missions.Mission;

import java.time.LocalDateTime;

public class MissionInfo {

    final private Planet destination;
    final private Planet source;
    final private LocalDateTime endTime;
    final private FleetInfo fleet;

    MissionInfo (Mission mission) {
        source = new Planet(mission.getSource());
        destination = new Planet(mission.getDestination());
        endTime = mission.getTime();
        fleet = new FleetInfo(mission.getFleet());
    }

    public Planet getDestination() {
        return destination;
    }

    public Planet getSource() {
        return source;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public FleetInfo getFleet() {
        return fleet;
    }
}
