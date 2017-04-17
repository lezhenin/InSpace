package ru.spbstu.icc.kspt.inspace.service.documents;


import ru.spbstu.icc.kspt.inspace.api.APlanet;


public class PlanetDescription {

    private String name;
    private Position position;
    private String url;

    public PlanetDescription(APlanet planet) {
        this.name = planet.getName();
        position = new Position(planet.getPosition());
        url = "/planets/" + planet.getPosition().getNumberOfSystem()
                + "/" + planet.getPosition().getNumberOfPlanet();
    }

    public PlanetDescription(String name, Position position, String url) {
        this.name = name;
        this.position = position;
        this.url = url;
    }

    public PlanetDescription() {
        this.name = null;
        this.position = null;
        this.url = null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public String getUrl() {
        return url;
    }
}
