package ru.spbstu.icc.kspt.inspace.model.research;

import ru.spbstu.icc.kspt.inspace.model.utils.Upgrade;

import java.time.LocalDateTime;

public class ResearchUpgrade implements Upgrade{

    Research research;
    LocalDateTime time;

    public ResearchUpgrade(Research research, LocalDateTime time) {
        this.research = research;
        this.time = time;
    }

    @Override
    public void execute(LocalDateTime now) {
        if (time.compareTo(now) == -1) {
            //TODO throw
        }
    }

    @Override
    public LocalDateTime getTime() {
       return time;
    }

    @Override
    public Research getUpgradable() {
        return research;
    }
}
