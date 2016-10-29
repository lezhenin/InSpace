package ru.spbstu.icc.kspt.inspace.model.research;

import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.buildings.Building;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.utils.Time;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class ResearchDepartment {

    private Planet planet;
    private Map<ResearchType, Research> researches = new EnumMap<>(ResearchType.class);
    private ResearchUpgrade upgrading;

    public ResearchDepartment(Planet planet) {
        this.planet = planet;
        researches.put(ResearchType.ENERGY, new EnergyTechnology(this));
    }

    public boolean checkUpgradability(Research research){
        updateResearches();
        return (planet.getResources().isEnough(research.getUpgradeCost()) && upgrading == null);
    }

    void startUpgrade(ResearchUpgrade upgrade) {
        Research research = upgrade.getUpgradable();
        if (!checkUpgradability(research)){
            //TODO exception
            return;
        }
        planet.getResources().takeResources(upgrade.getUpgradable().getUpgradeCost());
        upgrading = upgrade;
    }


    public ResearchUpgrade getCurrentUpgrade() {
        updateResearches();
        return upgrading;
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
        if (upgrading != null && upgrading.getTime().compareTo(Time.now()) <= 0 ){
            upgrading.execute(Time.now());
            upgrading = null;
        }
    }
}
