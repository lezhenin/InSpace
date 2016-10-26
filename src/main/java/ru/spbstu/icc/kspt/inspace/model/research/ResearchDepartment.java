package ru.spbstu.icc.kspt.inspace.model.research;

import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.buildings.Building;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class ResearchDepartment {

    private Planet planet;
    private Map<ResearchType, Research> researches = new EnumMap<>(ResearchType.class);

    public ResearchDepartment(Planet planet) {
        this.planet = planet;
        researches.put(ResearchType.ENERGY, new EnergyTechnology(this));
    }

    public Research getResearch(ResearchType researchType) {
        return researches.get(researchType);
    }

    public Set<Map.Entry<ResearchType, Research>> getResearches() {
        return researches.entrySet();
    }

    Building getBuilding(BuildingType buildingType) {
        return planet.getBuilding(buildingType);
    }
}
