package ru.spbstu.icc.kspt.inspace.service.documents;

public class System {

    private final String name;
    private final int number;

    public System(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }
}
