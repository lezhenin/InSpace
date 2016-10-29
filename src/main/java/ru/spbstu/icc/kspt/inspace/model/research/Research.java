package ru.spbstu.icc.kspt.inspace.model.research;


import ru.spbstu.icc.kspt.inspace.model.Resources;
import ru.spbstu.icc.kspt.inspace.model.utils.Time;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgradable;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Research implements Upgradable{

    protected int level;
    protected ResearchDepartment department;

    public Research(ResearchDepartment department) {
        this.department = department;
    }

    abstract void updateDependencies();

    @Override
    public void startUpgrade() {
        LocalDateTime upgradeTime = Time.now().plus(getUpgradeDuration());
        department.startUpgrade(new ResearchUpgrade(this, upgradeTime) {
            @Override
            public void execute(LocalDateTime now) {
                super.execute(now);
                level++;
            }
        });
    }

    abstract public Duration getUpgradeDuration();

    abstract public Resources getUpgradeCost();

    public int getLevel() {
        return level;
    }
}
