package ru.spbstu.icc.kspt.inspace.model.buildings;


import ru.spbstu.icc.kspt.inspace.model.exception.UpgradeException;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgradable;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgrade;

abstract public class Building implements Upgradable {

    protected BuildingDepartment department;
    private int level;
    private BuildingType type;

    protected void upgrade() {
        level++;
    }

    public Building(BuildingDepartment department, BuildingType type) {
        this.department = department;
        this.type = type;
    }

    void updateDependencies() {}

    public boolean canBeUpgraded() {
        department.updatePlanet();
        return department.canBeUpgraded(this);
    }

    @Override
    public void startUpgrade() throws UpgradeException {
        if(!canBeUpgraded()) {
            throw new UpgradeException(this);
        }
        department.startUpgrade(new Upgrade(this) {
            @Override
            public void onExecute() {
                upgrade();
            }
        });
    }

    public BuildingType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }
}
