package ru.spbstu.icc.kspt.inspace.model.utils;


import ru.spbstu.icc.kspt.inspace.model.resources.Resources;
import ru.spbstu.icc.kspt.inspace.model.exception.ConstructException;

import java.time.Duration;

public interface Constructable {

    Resources getConstructCost();

    void startConstruction(int number) throws ConstructException;

    Duration getConstructDuration();
}
