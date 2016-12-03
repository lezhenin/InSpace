package ru.spbstu.icc.kspt.inspace.api;

import java.time.LocalDateTime;
import java.util.Objects;

public class Mission {

    final private ru.spbstu.icc.kspt.inspace.model.fleet.missions.Mission mission;

    Mission(ru.spbstu.icc.kspt.inspace.model.fleet.missions.Mission mission) {
        this.mission = mission;
    }

    public Planet getDestination() {
        return new Planet((mission.getDestination()));
    }

    public Planet getSource() {
        return new Planet(mission.getSource());
    }

    public LocalDateTime getEndTime() {
        return LocalDateTime.of(mission.getTime().toLocalDate(), mission.getTime().toLocalTime());
    }

    public Fleet getFleet() {
        return new Fleet(mission.getFleet());
    }
}
