package ru.spbstu.icc.kspt.inspace.model.exception;

import ru.spbstu.icc.kspt.inspace.model.Position;

public class ActionIsNotPerforming extends Exception {
    public ActionIsNotPerforming(Position position) {
        super("Action is not performing now at planet at " + position);
    }
}
