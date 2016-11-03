package ru.spbstu.icc.kspt.inspace.model.utils;


import ru.spbstu.icc.kspt.inspace.model.Resources;

import java.time.Duration;

public interface Constructable {

    Resources getConstructCost();

    Duration getConstructDuration();
}
