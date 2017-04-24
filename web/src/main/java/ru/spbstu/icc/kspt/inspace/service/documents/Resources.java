package ru.spbstu.icc.kspt.inspace.service.documents;


import ru.spbstu.icc.kspt.inspace.api.AResources;

public class Resources {

    private int metal;
    private int crystals;
    private int deuterium;

    public Resources(AResources resources) {
        this.metal = resources.getMetal();
        this.crystals = resources.getCrystals();
        this.deuterium = resources.getDeuterium();
    }

    public Resources() {
    }

    public void setMetal(int metal) {
        this.metal = metal;
    }

    public void setCrystals(int crystals) {
        this.crystals = crystals;
    }

    public void setDeuterium(int deuterium) {
        this.deuterium = deuterium;
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
