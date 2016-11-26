package ru.spbstu.icc.kspt.inspace.model.utils;

import java.time.LocalDateTime;

public abstract class Construct extends TimeAction {

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

    public LocalDateTime getTime() {
        return time;
    }

    public Constructable getConstructable() {
        return constructable;
    }

    public int getNumberOfUnits() {
        return numberOfUnits;
    }
}
