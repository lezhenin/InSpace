package ru.spbstu.icc.kspt.inspace.model.exception;

import ru.spbstu.icc.kspt.inspace.model.utils.Constructable;

public class ConstructException extends Exception {
    private Constructable constructable;

    public ConstructException(Constructable constructable) {
        this.constructable = constructable;
    }

    public ConstructException(String message, Constructable constructable) {
        super(message);
        this.constructable = constructable;
    }

    public Constructable getConstructable() {
        return constructable;
    }
}
