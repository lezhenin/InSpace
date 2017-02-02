package ru.spbstu.icc.kspt.inspace.api;

import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;
import ru.spbstu.icc.kspt.inspace.model.utils.*;


public interface AResearch extends AUpgradable {

    ResearchType getType();
}
