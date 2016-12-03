package ru.spbstu.icc.kspt.inspace.api;


import ru.spbstu.icc.kspt.inspace.model.exception.ConstructException;

import java.time.Duration;

abstract public class Constructable {
    private final ru.spbstu.icc.kspt.inspace.model.utils.Constructable constructable;

    Constructable(ru.spbstu.icc.kspt.inspace.model.utils.Constructable constructable) {
        this.constructable = constructable;
    }

    public Resources getConstructCost() {
        return new Resources(constructable.getConstructCost());
    }

    public void construct(int number) throws ConstructException {
        constructable.startConstruction(number);
    }

    public Duration getConstructDuration() {
        return Duration.ofSeconds(constructable.getConstructDuration().getSeconds());
    }
}
