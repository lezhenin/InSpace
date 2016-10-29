package ru.spbstu.icc.kspt.inspace.model.research;

import ru.spbstu.icc.kspt.inspace.model.utils.Upgradable;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgrade;

import java.time.LocalDateTime;

public class ResearchUpgrade implements Upgrade{

    Research research;
    LocalDateTime upgradeTime;

    public ResearchUpgrade(Research research, LocalDateTime upgradeTime) {
        this.research = research;
        this.upgradeTime = upgradeTime;
    }

    @Override
    public void execute(LocalDateTime now) {

    }

    @Override
    public LocalDateTime getTime() {
       return upgradeTime;
    }

    @Override
    public Research getUpgradable() {
        return research;
    }
}
