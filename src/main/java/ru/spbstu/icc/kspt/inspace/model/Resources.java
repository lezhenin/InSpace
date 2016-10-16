package ru.spbstu.icc.kspt.inspace.model;

public class Resources {
    private int metal;
    private int crystals;
    private int deuterium;

    public Resources(int metal, int crystals, int deuterium) {
        this.metal = metal;
        this.crystals = crystals;
        this.deuterium = deuterium;
    }

    public void addResources(Resources resources) {
        metal += resources.metal;
        crystals += resources.crystals;
        deuterium += resources.deuterium;
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

    public Resources getResources(int metal, int crystals, int deuterium) {

        int metalAmount = (metal <= this.metal) ? metal : this.metal;
        int crystalsAmount = (crystals <= this.metal) ? crystals : this.metal;
        int deuteriumAmount = (deuterium <= this.metal) ? deuterium : this.metal;

        this.metal -= metalAmount;
        this.crystals -= crystalsAmount;
        this.deuterium -= deuteriumAmount;

        return new Resources(metalAmount, crystalsAmount, deuteriumAmount);
    }

    @Override
    public String toString() {
        return "Resources{" +
                "Metal:" + metal +
                ", Crystals: " + crystals +
                ", Deuterium: " + deuterium +
                '}';
    }
}
