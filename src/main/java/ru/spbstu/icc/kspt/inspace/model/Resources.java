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

    public Resources takeResources(Resources resources) {
        return takeResources(resources.metal, resources.crystals, resources.deuterium);
    }

    //TODO идея пишет, что можно метод сделать private, т.к только здесь его используешь.
    //TODO мб ты и сам видел, но решил, что понадобится использовать и эту реализацию
    public Resources takeResources(int metal, int crystals, int deuterium) {

        //TODO думаю, что тут лучше будет назвать переменные на подобии забираемыйРесурс.
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

    //
    //TODO вот тут мне кажется немного странным, что ресурсы знают, достаточно ли их для чего-то.
    //TODO UPD: на самом деле они не знают, но название говорит об обратном.
    //
    //TODO если рассуждать в этом ключе, то ресурсы могут знать, больше ли их, чем сколько-то
    //TODO areMoreThan(Resources resources), что, в общем-то, в данном решении и требуется
    //
    //TODO Я бы скорее всего переложил бы ответственность на проверку, достаточно ли ресурсов для апгрейда
    //TODO на UpgradeDepartment. А в нем уже, дергая за геттеры этот класс, осуществлял проверку
    //TODO смотри класс UpgradeDepartment
    //
    public boolean isEnough(Resources o) {
        return  (metal >= o.metal && crystals >= o.crystals && deuterium >= o.deuterium);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resources resources = (Resources) o;

        //TODO идея подсказывает, что можно упростить конструкцию
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
