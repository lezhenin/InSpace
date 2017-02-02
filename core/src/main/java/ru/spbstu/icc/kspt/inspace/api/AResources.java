package ru.spbstu.icc.kspt.inspace.api;

import ru.spbstu.icc.kspt.inspace.model.resources.*;


public interface AResources extends Comparable<ru.spbstu.icc.kspt.inspace.model.resources.Resources> {

    int getMetal();

    int getCrystals();

    int getDeuterium();

    int getAmount();
}
