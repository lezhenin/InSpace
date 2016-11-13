package ru.spbstu.icc.kspt.inspace.model.utils;


import java.time.LocalDateTime;
import java.util.*;

public abstract class Upgrade implements Action {

    private Upgradable upgradable;
    private LocalDateTime time;

    private Queue<Action> beforeExecution = new LinkedList<>();
    private Queue<Action> afterExecution = new LinkedList<>();

    public void addActionBeforeExecution(Action action) {
        beforeExecution.add(action);
    }

    public void addActionAfterExecution(Action action) {
        afterExecution.add(action);
    }

    @Override
    final public void execute() {
        beforeExecution.forEach(Action::execute);
        onExecute();
        afterExecution.forEach(Action::execute);
    }

    public abstract void onExecute();

    public Upgrade(Upgradable upgradable, LocalDateTime time) {
        this.upgradable = upgradable;
        this.time = time;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Upgradable getUpgradable() {
        return upgradable;
    }
}
