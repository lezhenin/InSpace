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
import ru.spbstu.icc.kspt.inspace.service.exceptions.UnitTypeException;

import java.util.*;

@RestController
@RequestMapping(value = "planets/{numberOfSystem}/{numberOfPlanet}/")
public class ConstructablesController {

    private final Map<String, ShipType> shipTypeTable = new HashMap<>();

    {
        for (ShipType value: ShipType.values()) {
            shipTypeTable.put(value.toString().toLowerCase().replace('_','-'), value);
        }
    }

    @RequestMapping(value = "ships",
            method = RequestMethod.GET)
    List<Ship> ships(@PathVariable("numberOfSystem") int numberOfSystem,
                     @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist {

        List<Ship> ships = new ArrayList<>();
        APlanet planet = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet);
        planet.getShips().values().forEach(ship -> ships.add(new Ship((ship))));
        return ships;
    }

    @RequestMapping(value = "ships/{shipType}",
            method = RequestMethod.GET)
    Ship ship(@PathVariable("numberOfSystem") int numberOfSystem,
              @PathVariable("numberOfPlanet") int numberOfPlanet,
              @PathVariable("shipType") String typeString)
            throws PlanetDoesntExist, UnitTypeException {

        ShipType type;
        if (shipTypeTable.containsKey(typeString)){
            type = shipTypeTable.get(typeString);
        } else {
            throw new UnitTypeException(typeString);
        }

        return new Ship(Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getShips().get(type));
    }

    @RequestMapping(value = "ships/start-construction",
            method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    ShipConstruction constructShip(@PathVariable("numberOfSystem") int numberOfSystem,
                                   @PathVariable("numberOfPlanet") int numberOfPlanet,
                                   @RequestParam("shipType") String typeString,
                                   @RequestParam("number") int number)
            throws PlanetDoesntExist, AssertionError, ConstructException, UnitTypeException {

        ShipType type;
        if (shipTypeTable.containsKey(typeString)){
            type = shipTypeTable.get(typeString);
        } else {
            throw new UnitTypeException(typeString);
        }

        APlanet planet = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet);
        planet.getShips().get(type).startConstruction(number);

        try {
            return new ShipConstruction(planet.getCurrentConstruct());
        } catch (ActionIsNotPerforming e) {
            throw new AssertionError();
        }
    }

    @RequestMapping(value = "ships/current-construction",
            method = RequestMethod.GET)
    ShipConstruction shipConstruction(@PathVariable("numberOfSystem") int numberOfSystem,
                                      @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist, ActionIsNotPerforming {

        AConstruct construction = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getCurrentConstruct();
        return new ShipConstruction(construction);
    }

}
