package ru.spbstu.icc.kspt.inspace.model.research;

import ru.spbstu.icc.kspt.inspace.model.exception.UpgradeException;
import ru.spbstu.icc.kspt.inspace.model.utils.Time;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgradable;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgrade;

import java.time.LocalDateTime;

public abstract class Research implements Upgradable{

    protected int level;
    protected ResearchDepartment department;
    private ResearchType type;

    public Research(ResearchDepartment department, ResearchType type) {
        this.department = department;
        this.type = type;
    }

    abstract void updateDependencies();

    public boolean canBeUpgraded() {
        return department.canBeUpgraded(this);
    }

    @Override
    public void startUpgrade() throws UpgradeException {
        department.startUpgrade(new Upgrade(this) {
            @Override
            public void onExecute() {
                level++;
            }
        });
    }

    public ResearchType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }
}
