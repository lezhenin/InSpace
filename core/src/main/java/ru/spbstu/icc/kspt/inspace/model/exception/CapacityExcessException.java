package ru.spbstu.icc.kspt.inspace.model.exception;


public class CapacityExcessException extends Exception{
    int capacity;
    int resourcesAmount;

    public CapacityExcessException(int capacity, int resourcesAmount) {
        this.capacity = capacity;
        this.resourcesAmount = resourcesAmount;
    }

    public CapacityExcessException(String message, int capacity, int resourcesAmount) {
        super(message);
        this.capacity = capacity;
        this.resourcesAmount = resourcesAmount;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getResourcesAmount() {
        return resourcesAmount;
    }
}
