package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.utils.Upgrade;

import java.time.LocalDateTime;

public class BuildingUpgrade implements Upgrade {

    //TODO тут можно Upgradable вместо Building
    //Я подумал, вроде, можно.
    //И тесты не упали, когда поменял
    private Building building;
    private LocalDateTime time;


    BuildingUpgrade(Building building, LocalDateTime time) {
        this.building = building;
        this.time = time;
    }

    @Override
    public void execute(LocalDateTime now) {
        if (time.compareTo(now) == -1) {
            //TODO throw
        }

    }

    @Override
    public LocalDateTime getTime() {
        return time;
    }

    @Override
    //TODO тут можно Upgradable вместо Building
    public Building getUpgradable() {
        return building;
    }
}
