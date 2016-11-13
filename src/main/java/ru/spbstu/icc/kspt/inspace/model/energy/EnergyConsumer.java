package ru.spbstu.icc.kspt.inspace.model.energy;

//TODO Из-за того, что в названии есть Consumer, может показаться, что это что-то с лямбдами
//TODO а не потребитель энергии.


public interface EnergyConsumer {
    int getEnergyConsumption();

    void setProductionPower(double power);
}
