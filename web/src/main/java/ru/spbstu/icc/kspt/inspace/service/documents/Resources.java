package ru.spbstu.icc.kspt.inspace.service.documents;


import ru.spbstu.icc.kspt.inspace.api.AResources;

public class Resources {

    private final int metal;
    private final int crystals;
    private final int deuterium;

    public Resources(AResources resources) {
        this.metal = resources.getMetal();
        this.crystals = resources.getCrystals();
        this.deuterium = resources.getDeuterium();
    }

    public int getMetal() {
        return metal;
    }

    public int getCrystals() {
        return crystals;
    }

    public int getDeuterium() {
        return deuterium;
    }
}
