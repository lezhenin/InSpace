package ru.spbstu.icc.kspt.inspace.api;

import java.time.LocalDateTime;

public class Construct {
    private final ru.spbstu.icc.kspt.inspace.model.utils.Construct construct;

    Construct(ru.spbstu.icc.kspt.inspace.model.utils.Construct construct) {
        this.construct = construct;
    }

    public LocalDateTime getEndTime() {
        return LocalDateTime.of(construct.getTime().toLocalDate(), construct.getTime().toLocalTime());
    }

    public Constructable getConstructable() {
        return new Ship((ru.spbstu.icc.kspt.inspace.model.fleet.Ship)construct.getConstructable());
    }

    public int getNumberOfUnits() {
        return construct.getNumberOfUnits();
    }
}
