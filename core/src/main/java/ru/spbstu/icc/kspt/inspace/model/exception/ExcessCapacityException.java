package ru.spbstu.icc.kspt.inspace.model.exception;


public class ExcessCapacityException extends Exception{
    int capacity;
    int resourcesAmount;

    public ExcessCapacityException(int capacity, int resourcesAmount) {
        this.capacity = capacity;
        this.resourcesAmount = resourcesAmount;
    }

    public ExcessCapacityException(String message, int capacity, int resourcesAmount) {
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
