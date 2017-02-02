package ru.spbstu.icc.kspt.inspace.model.utils;


import ru.spbstu.icc.kspt.inspace.api.AConstructable;
import ru.spbstu.icc.kspt.inspace.model.resources.Resources;


public interface Constructable extends AConstructable {

    @Override
    Resources getConstructCost();
}
