package ru.spbstu.icc.kspt.inspace.model.buildings;


import ru.spbstu.icc.kspt.inspace.model.utils.Time;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgradable;

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

    abstract public void updateDependencies();

    public boolean canBeUpgraded() {
        return department.checkUpgradability(this);
    }

    //TODO вроде когда реализуешь метод интерфейса, не нужна аннотация override.
    @Override
    public void startUpgrade() {
        if(!canBeUpgraded()) {
            //TODO exception
            return;
        }
        LocalDateTime upgradeTime = Time.now().plus(getUpgradeDuration());
        //TODO подумать на тем, как это сделать лямбдой. Вроде можно.
        department.startUpgrade(new BuildingUpgrade(this, upgradeTime) {
            @Override
            public void execute(LocalDateTime now) {
                super.execute(now);
                upgrade();
            }
        });
    }

    public int getLevel() {
        return level;
    }
}
