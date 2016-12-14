package ru.spbstu.icc.kspt.inspace.api;


import ru.spbstu.icc.kspt.inspace.model.Position;

public class Galaxy {

    private static ru.spbstu.icc.kspt.inspace.model.Galaxy galaxy = ru.spbstu.icc.kspt.inspace.model.Galaxy.getInstance();

    public static Planet getPlanet(Position position) {
        if (galaxy.getPlanet(position) == null) {
            return null;
        }
        return new Planet(galaxy.getPlanet(position));
    }
}
