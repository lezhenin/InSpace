package ru.spbstu.icc.kspt.inspace.model.utils;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

public abstract class TimeAction extends Action {

    public abstract LocalDateTime getTime();
}
