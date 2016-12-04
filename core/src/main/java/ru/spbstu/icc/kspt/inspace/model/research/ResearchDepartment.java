package ru.spbstu.icc.kspt.inspace.model.research;

import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.buildings.Building;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.exception.UpgradeException;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgradable;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgrade;
import ru.spbstu.icc.kspt.inspace.model.utils.UpgradeDepartment;

import java.util.EnumMap;
import java.util.Map;

public class ResearchDepartment extends UpgradeDepartment {

    private Map<ResearchType, Research> researches = new EnumMap<>(ResearchType.class);

    public ResearchDepartment(Planet planet) {
        super(planet);
        researches.put(ResearchType.ENERGY, new Research(this, ResearchType.ENERGY));
        researches.put(ResearchType.LASER, new Research(this, ResearchType.LASER));
    }

    public void updateDependencies() {
        researches.values().forEach(Research::updateDependencies);
    }

    @Override
    protected boolean canBeUpgraded(Upgradable upgradable) {
        return super.canBeUpgraded(upgradable);
    }

    @Override
    protected void startUpgrade(Upgrade upgrade) throws UpgradeException {
        super.startUpgrade(upgrade);
    }

    public Research getResearch(ResearchType researchType) {
        return researches.get(researchType);
    }

    public Map<ResearchType, Research> getResearches() {
        return researches;
    }

    Building getBuilding(BuildingType buildingType) {
        return planet.getBuilding(buildingType);
    }

}
