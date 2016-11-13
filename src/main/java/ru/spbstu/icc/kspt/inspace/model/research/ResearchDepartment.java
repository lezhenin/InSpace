package ru.spbstu.icc.kspt.inspace.model.research;

import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.buildings.Building;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgradable;
import ru.spbstu.icc.kspt.inspace.model.utils.Upgrade;
import ru.spbstu.icc.kspt.inspace.model.utils.UpgradeDepartment;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class ResearchDepartment extends UpgradeDepartment {

    private Map<ResearchType, Research> researches = new EnumMap<>(ResearchType.class);

    public ResearchDepartment(Planet planet) {
        super(planet);
        researches.put(ResearchType.ENERGY, new EnergyTechnology(this));
    }

    public void updateDependencies() {
        researches.values().forEach(Research::updateDependencies);
    }

    @Override
    protected boolean canBeUpgraded(Upgradable upgradable) {
        return super.canBeUpgraded(upgradable);
    }

    @Override
    protected void startUpgrade(Upgrade upgrade) {
        super.startUpgrade(upgrade);
    }

    public Research getResearch(ResearchType researchType) {
        update();
        return researches.get(researchType);
    }

    public Set<Map.Entry<ResearchType, Research>> getResearches() {
        update();
        return researches.entrySet();
    }

    Building getBuilding(BuildingType buildingType) {
        return planet.getBuilding(buildingType);
    }

}
