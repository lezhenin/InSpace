package ru.spbstu.icc.kspt.inspace.api;

import ru.spbstu.icc.kspt.inspace.model.utils.Upgradable;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgrade;

import java.time.LocalDateTime;

public class UpgradeInfo {
    final private LocalDateTime endTime;

    public UpgradeInfo(Upgrade upgrade) {
        endTime = upgrade.getTime();
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
