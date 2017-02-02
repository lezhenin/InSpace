package ru.spbstu.icc.kspt.inspace.api;

import ru.spbstu.icc.kspt.inspace.model.exception.ConstructException;
import ru.spbstu.icc.kspt.inspace.model.resources.*;

import java.time.Duration;


public interface AConstructable {

    AResources getConstructCost();

    void startConstruction(int number) throws ConstructException;

    Duration getConstructDuration();
}
