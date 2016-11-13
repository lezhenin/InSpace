package ru.spbstu.icc.kspt.inspace.model.buildings;


import ru.spbstu.icc.kspt.inspace.model.Resources;
import ru.spbstu.icc.kspt.inspace.model.energy.EnergyConsumer;
import ru.spbstu.icc.kspt.inspace.model.utils.Time;

import java.time.Duration;
import java.time.LocalDateTime;

//TODO Шахта это Постройка, Здание? Ну, в принципе, да.
abstract public class Mine extends Building implements EnergyConsumer {;

    private LocalDateTime lastProductionGetting;
    private Resources temporaryStorage;
    double power = 1.0;

    protected Factory factory;

    public Mine(BuildingDepartment department) {
        super(department);

        lastProductionGetting = Time.now();
        temporaryStorage = new Resources(0,0,0);
    }

    @Override
    public void updateDependencies() {
        factory = (Factory)department.getBuilding(BuildingType.FACTORY);
    }

    public Resources getProduction() {
        Resources resources = getProductionPerPeriod(Duration.between(lastProductionGetting, Time.now()));
        resources.addResources(temporaryStorage);
        temporaryStorage = new Resources(0,0,0);
        lastProductionGetting = Time.now();
        return resources;
    }

    @Override
    protected void upgrade() {
        temporaryStorage.addResources(getProduction());
        super.upgrade();
    }

    //TODO вроде не нужна аннотация, когда реализуешь метод интерфейса. (?)
    @Override
    public void setProductionPower(double power) {
        if (power <= 1 && power >=0 ) {
            this.power = power;
        }
    }

    abstract protected Resources getProductionPerPeriod(Duration duration);
}
