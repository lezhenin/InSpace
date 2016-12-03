package ru.spbstu.icc.kspt.inspace.api;

import java.time.LocalDateTime;

public class Upgrade {
    final private ru.spbstu.icc.kspt.inspace.model.utils.Upgrade upgrade;

    Upgrade(ru.spbstu.icc.kspt.inspace.model.utils.Upgrade upgrade) {
        this.upgrade = upgrade;
    }

    public Upgradable getUpgradable() {
        if (upgrade.getUpgradable() instanceof ru.spbstu.icc.kspt.inspace.model.buildings.Building) {
            return new Building((ru.spbstu.icc.kspt.inspace.model.buildings.Building)upgrade.getUpgradable());
        } else {
            return new Research((ru.spbstu.icc.kspt.inspace.model.research.Research)upgrade.getUpgradable());
        }
    }

    public LocalDateTime getEndTime() {
        return LocalDateTime.of(upgrade.getTime().toLocalDate(), upgrade.getTime().toLocalTime());
    }
}
