package ru.spbstu.icc.kspt.inspace.model.exception;


import ru.spbstu.icc.kspt.inspace.model.Position;

public class PlanetDoesntExist extends Exception {

    public PlanetDoesntExist(Position position) {
        super("Planet on position " + position + " doesn't exist.");
    }
}
