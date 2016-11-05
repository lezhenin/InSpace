package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.Resources;

import java.time.Duration;

public class MetalMine extends Mine {

    //TODO ВЕЗДЕ подумать об вынесении заинлайненых констант в переменные
    private static final int PRODUCTION_SPEED_VALUE = 30;
    private static final int METAL_COST_VALUE = 40;
    private static final int CRYSTAL_COST_VALUE = 35;
    private static final int UPGRADE_SPEED_VALUE = 2700;
    private static final int ENERGY_CONSUMPTION_VALUE = 30;

    public MetalMine(BuildingDepartment department) {
        super(department);
    }


    //TODO три метода ниже выглядят одинаковыми во всех наследниках Mine. Различны лишь константы.
    //TODO может, сделать в Mine параметризованную реализацию и потом использовать?
    //TODO не уверен, что будет красивее.
    //TODO возможно, все масштабнее. И можно даже в Building такое сделть.
    //TODO надо подумать.

    //TODO вроде не нужна аннотация, когда реализуешь интерфейс.
    @Override
    public Duration getUpgradeDuration() {
        Resources cost = getUpgradeCost();
        double summaryCost = cost.getMetal() + cost.getCrystals() + cost.getMetal();
        double hours = summaryCost / (UPGRADE_SPEED_VALUE * (1 + factory.getLevel()));
        return Duration.ofSeconds(Math.round(hours * 3600));
    }

    //TODO вроде когда реализуешь метод интерфейса, не нужна аннотация override.
    @Override
    public Resources getUpgradeCost() {
        int metal = (int)Math.round(METAL_COST_VALUE * Math.pow(1.5, getLevel()));
        int crystal = (int)Math.round(CRYSTAL_COST_VALUE * Math.pow(1.5, getLevel()));
        return new Resources(metal, crystal, 0);
    }

    //TODO вроде когда реализуешь метод интерфейса, не нужна аннотация override.
    @Override
    //TODO мб лучше getProductionPerDuration ?
    protected Resources getProductionPerPeriod(Duration duration) {
        double metal = PRODUCTION_SPEED_VALUE * (getLevel()+1) * Math.pow(1.1, getLevel());
        metal *= duration.getSeconds() / 3600.0;
        return new Resources((int)Math.round(metal), 0, 0);
    }

    //TODO вроде ВЕЗДЕ когда реализуешь метод интерфейса, не нужна аннотация override.
    @Override
    public int getEnergyConsumption() {
        //TODO меня ругал за такие длинные выражения.
        //TODO но мне кажется, что нормально
        return (int)(ENERGY_CONSUMPTION_VALUE * getLevel() * Math.pow(1.4, getLevel()) * power);
    }
}
