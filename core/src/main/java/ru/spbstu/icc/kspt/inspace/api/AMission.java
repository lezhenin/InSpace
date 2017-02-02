package ru.spbstu.icc.kspt.inspace.api;

import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.fleet.Fleet;

import java.time.LocalDateTime;


public interface AMission {
    LocalDateTime getTime();

    APlanet getSource();

    APlanet getDestination();

    AFleet getFleet();
}
