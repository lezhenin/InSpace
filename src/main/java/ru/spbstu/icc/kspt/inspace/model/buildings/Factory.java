package ru.spbstu.icc.kspt.inspace.model.buildings;

import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.Resources;

import java.time.Duration;
import java.time.LocalDateTime;

public class Factory extends Building {

    private static final int METAL_COST_VALUE = 60;
    private static final int CRYSTAL_COST_VALUE = 50;

    private final static int UPGRADE_SPEED_VALUE = 3000;

    private Planet planet;

    private BuildingUpgrade upgrading;

    private int fields = 0;

    public Factory(Planet planet) {
        this(planet, null);
        factory = this;
    }

    public Factory(Planet planet, Factory factory)
    {
        super(factory);
        this.planet = planet;
    }

    public int getFields() {
        return fields;
    }

    public boolean isBusy() {
        return upgrading != null;
    }

    public void startUpgrade(BuildingUpgrade upgrading) {

        Building building = upgrading.getUpgradable();

        if (!checkUpgradability(building)){
            //TODO exception
            return;
        }

        planet.getResources().getResources(building.getUpgradeCost());
        this.upgrading = upgrading;
    }

    public boolean checkUpgradability(Building building) {
        return !(building.getUpgradeCost().compareTo(planet.getResources()) == -1 &&
                !isBusy() && planet.getSize()-fields > 0);
    }

    public BuildingUpgrade getCurrentUpgrade() {
        return upgrading;
    }

    public void updateBuildings() {
        if (isBusy() && upgrading.getTime().compareTo(LocalDateTime.now()) >= 0 ){
            upgrading.execute(LocalDateTime.now());
            upgrading = null;
            fields++;
        }
    }

    @Override
    public Duration getUpgradeDuration() {
        Resources cost = getUpgradeCost();
        double summaryCost = cost.getMetal() + cost.getCrystals() + cost.getMetal();
        double hours = summaryCost / (UPGRADE_SPEED_VALUE * (1 + factory.getLevel()));
        return Duration.ofSeconds(Math.round(hours * 3600));
    }

    @Override
    public Resources getUpgradeCost() {
        int metal = (int)Math.round(METAL_COST_VALUE * Math.pow(1.6, level));
        int crystal = (int)Math.round(CRYSTAL_COST_VALUE * Math.pow(1.6, level));
        return new Resources(metal, crystal, 0);
    }

    @Override
    public int getEnergyConsumption() {
        return 0;
    }
}
