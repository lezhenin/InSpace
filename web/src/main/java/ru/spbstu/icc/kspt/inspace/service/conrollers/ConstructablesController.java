package ru.spbstu.icc.kspt.inspace.service.conrollers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.icc.kspt.inspace.api.AConstruct;
import ru.spbstu.icc.kspt.inspace.api.APlanet;
import ru.spbstu.icc.kspt.inspace.model.Galaxy;
import ru.spbstu.icc.kspt.inspace.model.exception.ActionIsNotPerforming;
import ru.spbstu.icc.kspt.inspace.model.exception.ConstructException;
import ru.spbstu.icc.kspt.inspace.model.exception.PlanetDoesntExist;
import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;
import ru.spbstu.icc.kspt.inspace.service.documents.responses.Ship;
import ru.spbstu.icc.kspt.inspace.service.documents.responses.ShipConstruction;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ConstructablesController {


    @RequestMapping(value = "{numberOfSystem}/{numberOfPlanet}/ships",
            method = RequestMethod.GET)
    List<Ship> ships(@PathVariable("numberOfSystem") int numberOfSystem,
                     @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist {

        List<Ship> ships = new ArrayList<>();
        APlanet planet = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet);
        planet.getShips().values().forEach(ship -> ships.add(new Ship((ship))));
        return ships;
    }

    //fixme 10.04.17 path variable to lower case
    @RequestMapping(value = "{numberOfSystem}/{numberOfPlanet}/ships/{shipType}",
            method = RequestMethod.GET)
    Ship ship(@PathVariable("numberOfSystem") int numberOfSystem,
              @PathVariable("numberOfPlanet") int numberOfPlanet,
              @PathVariable("researchType") ShipType shipType)
            throws PlanetDoesntExist {

        return new Ship(Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getShips().get(shipType));
    }

    @RequestMapping(value = "{numberOfSystem}/{numberOfPlanet}/ships/start-construction",
            method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    ShipConstruction constructShip(@PathVariable("numberOfSystem") int numberOfSystem,
                                   @PathVariable("numberOfPlanet") int numberOfPlanet,
                                   @RequestParam("shipType") ShipType type,
                                   @RequestParam("number") int number)
            throws PlanetDoesntExist, AssertionError, ConstructException {

        APlanet planet = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet);
        planet.getShips().get(type).startConstruction(number);

        try {
            return new ShipConstruction(planet.getCurrentConstruct());
        } catch (ActionIsNotPerforming e) {
            throw new AssertionError();
        }
    }

    @RequestMapping(value = "{numberOfSystem}/{numberOfPlanet}/ships/current-construction",
            method = RequestMethod.GET)
    ShipConstruction shipConstruction(@PathVariable("numberOfSystem") int numberOfSystem,
                                      @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist, ActionIsNotPerforming {

        AConstruct construction = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getCurrentConstruct();
        return new ShipConstruction(construction);
    }

}
