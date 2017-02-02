package ru.spbstu.icc.kspt.inspace.api;

import ru.spbstu.icc.kspt.inspace.model.utils.*;

import java.time.LocalDateTime;

public interface AConstruct {
    LocalDateTime getTime();

    AConstructable getConstructable();

    int getNumberOfUnits();
}
