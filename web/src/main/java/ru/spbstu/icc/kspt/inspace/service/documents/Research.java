package ru.spbstu.icc.kspt.inspace.service.documents;


import ru.spbstu.icc.kspt.inspace.api.AResearch;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;

public class Research {
    private final ResearchType type;
    private final int level;
    private final Resources upgradeCost;
    private final int upgradeDuration;

    public Research(AResearch research) {
        type = research.getType();
        level = research.getLevel();
        upgradeCost = new Resources(research.getUpgradeCost());
        upgradeDuration = (int) research.getUpgradeDuration().getSeconds();

    }

    public ResearchType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public Resources getUpgradeCost() {
        return upgradeCost;
    }

    public int getUpgradeDuration() {
        return upgradeDuration;
    }
}
