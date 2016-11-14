package ru.spbstu.icc.kspt.inspace.model.utils;

import java.util.LinkedList;
import java.util.Queue;

public abstract class Action {

    private Queue<Action> beforeExecution = new LinkedList<>();
    private Queue<Action> afterExecution = new LinkedList<>();

    public void addActionBeforeExecution(Action action) {
        beforeExecution.add(action);
    }

    public void addActionAfterExecution(Action action) {
        afterExecution.add(action);
    }

    public void execute() {
        beforeExecution.forEach(Action::execute);
        onExecute();
        afterExecution.forEach(Action::execute);
    }

    protected abstract void onExecute();
}
