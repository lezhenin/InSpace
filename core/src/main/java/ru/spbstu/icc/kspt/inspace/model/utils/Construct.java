package ru.spbstu.icc.kspt.inspace.model.utils;

import ru.spbstu.icc.kspt.inspace.api.AConstruct;

import java.time.LocalDateTime;

public abstract class Construct extends TimeAction implements AConstruct {

    private Constructable constructable;
    private LocalDateTime time;
    private int numberOfUnits;

    public abstract void onExecute();

    public Construct(Constructable constructable, int numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
        this.constructable = constructable;
        this.time = Time.now();
        for (int i = 0; i < numberOfUnits; i++) {
            time = time.plus(constructable.getConstructDuration());
        }
    }

    @Override
    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public Constructable getConstructable() {
        return constructable;
    }

    @Override
    public int getNumberOfUnits() {
        return numberOfUnits;
    }
}
