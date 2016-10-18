package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.utils.Upgrade;

import java.time.LocalDateTime;

public class BuildingUpgrade implements Upgrade {
    private Building building;
    private LocalDateTime time;


    public BuildingUpgrade(Building building, LocalDateTime time) {
        this.building = building;
        this.time = time;
    }

    @Override
    public void execute(LocalDateTime now) {
        if (time.compareTo(now) == -1) {
            //TODO throw
            return;
        }

    }

    @Override
    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public Building getUpgradable() {
        return building;
    }
}
