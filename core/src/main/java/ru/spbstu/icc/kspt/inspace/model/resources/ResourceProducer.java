package ru.spbstu.icc.kspt.inspace.model.resources;


import java.time.Duration;

@FunctionalInterface
interface ResourceProducer {

    Resources getProduction(Duration duration);
}
