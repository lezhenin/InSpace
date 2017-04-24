package ru.spbstu.icc.kspt.inspace.service.documents.responses;

import ru.spbstu.icc.kspt.inspace.api.AResearch;
import ru.spbstu.icc.kspt.inspace.api.AUpgrade;

public class ResearchUpgrade {
    private final Research research;
    private final int levelAfterUpgrade;
    private final String startDate;
    private final String endDate;

    public ResearchUpgrade(AUpgrade upgrade) {
        this.research = new Research((AResearch) upgrade.getUpgradable());
        levelAfterUpgrade = research.getLevel() + 1;
        endDate = upgrade.getTime().toString();
        startDate = upgrade.getTime().minusSeconds((long) research.getUpgradeDuration()).toString();
    }

    public Research getResearch() {
        return research;
    }

    public int getLevelAfterUpgrade() {
        return levelAfterUpgrade;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
