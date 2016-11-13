package ru.spbstu.icc.kspt.inspace.model.research;

import ru.spbstu.icc.kspt.inspace.model.utils.Time;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgradable;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgrade;

import java.time.LocalDateTime;

public abstract class Research implements Upgradable{

    protected int level;
    protected ResearchDepartment department;

    public Research(ResearchDepartment department) {
        this.department = department;
    }

    abstract void updateDependencies();

    public boolean canBeUpgraded() {
        return department.canBeUpgraded(this);
    }

    @Override
    public void startUpgrade() {
        LocalDateTime upgradeTime = Time.now().plus(getUpgradeDuration());
        department.startUpgrade(new Upgrade(this, upgradeTime) {
            @Override
            public void onExecute() {
                level++;
            }
        });
    }

    public int getLevel() {
        return level;
    }
}
