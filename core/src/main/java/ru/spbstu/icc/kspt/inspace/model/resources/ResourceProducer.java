package ru.spbstu.icc.kspt.inspace.model.resources;


import java.time.Duration;

public interface ResourceProducer {

    Resources getProduction(Duration duration);
}
