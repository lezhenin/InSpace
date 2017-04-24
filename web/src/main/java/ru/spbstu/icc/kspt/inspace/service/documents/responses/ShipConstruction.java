package ru.spbstu.icc.kspt.inspace.service.documents.responses;

import ru.spbstu.icc.kspt.inspace.api.AConstruct;
import ru.spbstu.icc.kspt.inspace.api.AShipModel;

public class ShipConstruction {
    private final Ship ship;
    private final int numberOfUnits;
    private final String startDate;
    private final String endDate;

    public ShipConstruction(AConstruct construct) {
        this.ship = new Ship((AShipModel)construct.getConstructable());
        this.numberOfUnits = construct.getNumberOfUnits();
        this.endDate = construct.getTime().toString();
        this.startDate = construct.getTime().minusSeconds(ship.getConstructionDuration()).toString();
    }

    public Ship getShip() {
        return ship;
    }

    public int getNumberOfUnits() {
        return numberOfUnits;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
