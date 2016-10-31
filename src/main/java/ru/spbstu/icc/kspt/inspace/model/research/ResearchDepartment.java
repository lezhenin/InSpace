package ru.spbstu.icc.kspt.inspace.model.research;

import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.buildings.Building;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
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

    boolean checkUpgradability(Research research){
        updateResearches();
        return super.checkUpgradability(research);
    }

    void startUpgrade(ResearchUpgrade upgrade) {
        super.startUpgrade(upgrade);
    }

    public ResearchUpgrade getCurrentUpgrade() {
        updateResearches();
        return (ResearchUpgrade)super.getCurrentUpgrade();
    }

    public void updateDependencies() {
        researches.values().forEach(Research::updateDependencies);
    }

    public Research getResearch(ResearchType researchType) {
        updateResearches();
        return researches.get(researchType);
    }

    public Set<Map.Entry<ResearchType, Research>> getResearches() {
        updateResearches();
        return researches.entrySet();
    }

    Building getBuilding(BuildingType buildingType) {
        return planet.getBuilding(buildingType);
    }

    public void updateResearches() {
        super.update();
    }
}
