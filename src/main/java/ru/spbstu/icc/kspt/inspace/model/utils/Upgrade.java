package ru.spbstu.icc.kspt.inspace.model.utils;


import java.time.LocalDateTime;

public interface Upgrade {

    void execute(LocalDateTime now);

    LocalDateTime getTime();

    Upgradable getUpgradable();
}
