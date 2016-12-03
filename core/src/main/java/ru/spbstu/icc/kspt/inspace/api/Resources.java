package ru.spbstu.icc.kspt.inspace.api;


public class Resources {
    final private int metal;
    final private int crystals;
    final private int deuterium;

    Resources(ru.spbstu.icc.kspt.inspace.model.Resources resources) {
        metal = resources.getMetal();
        crystals = resources.getCrystals();
        deuterium = resources.getDeuterium();
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
