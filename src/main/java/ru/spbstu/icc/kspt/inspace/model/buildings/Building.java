package ru.spbstu.icc.kspt.inspace.model.buildings;


import ru.spbstu.icc.kspt.inspace.model.utils.Time;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgradable;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgrade;

import java.time.LocalDateTime;

abstract public class Building implements Upgradable {

    protected BuildingDepartment department;
    private int level;

    protected void upgrade() {
        level++;
    }

    public Building(BuildingDepartment department) {
        this.department = department;
    }

    public void updateDependencies() {}

    public boolean canBeUpgraded() {
        return department.canBeUpgraded(this);
    }

    @Override
    public void startUpgrade() {
        if(!canBeUpgraded()) {
            //TODO exception
            return;
        }
        department.startUpgrade(new Upgrade(this) {
            @Override
            public void onExecute() {
                upgrade();
            }
        });
    }

    public int getLevel() {
        return level;
    }
}
