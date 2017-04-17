package ru.spbstu.icc.kspt.inspace.service.documents;

import ru.spbstu.icc.kspt.inspace.api.AMission;
import ru.spbstu.icc.kspt.inspace.model.fleet.missions.MissionType;

public class Mission {
    private final Fleet fleet;
    private final PlanetDescription source;
    private final PlanetDescription destination;
    private final String endDate;
    private final MissionType type;

    public Mission(AMission mission) {
        this.fleet = new Fleet(mission.getFleet());
        this.source = new PlanetDescription(mission.getSource());
        this.destination = new PlanetDescription(mission.getDestination());
        this.endDate = mission.getTime().toString();
        this.type = mission.getType();
    }

    public Fleet getFleet() {
        return fleet;
    }

    public PlanetDescription getSource() {
        return source;
    }

    public PlanetDescription getDestination() {
        return destination;
    }

    public String getEndDate() {
        return endDate;
    }

    public MissionType getType() {
        return type;
    }
}
