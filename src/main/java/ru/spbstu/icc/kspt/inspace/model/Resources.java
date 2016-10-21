package ru.spbstu.icc.kspt.inspace.model;

public class Resources implements Comparable<Resources>{
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

    public Resources getResources(Resources resources) {
        return getResources(resources.metal, resources.crystals, resources.deuterium);
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

    @Override
    public int compareTo(Resources o) {
        if (metal == o.metal && crystals == o.crystals && deuterium == o.deuterium) {
            return 0;
        }
        else  if (metal <= o.metal && crystals <= o.crystals && deuterium <= o.deuterium) {
            return -1;
        }
        else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resources resources = (Resources) o;

        if (metal != resources.metal) return false;
        if (crystals != resources.crystals) return false;
        return deuterium == resources.deuterium;

    }

    @Override
    public int hashCode() {
        int result = metal;
        result = 31 * result + crystals;
        result = 31 * result + deuterium;
        return result;
    }
}
