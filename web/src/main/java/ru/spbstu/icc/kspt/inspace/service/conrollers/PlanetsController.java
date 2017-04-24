package ru.spbstu.icc.kspt.inspace.service.conrollers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.icc.kspt.inspace.api.*;
import ru.spbstu.icc.kspt.inspace.model.Galaxy;
import ru.spbstu.icc.kspt.inspace.model.Position;
import ru.spbstu.icc.kspt.inspace.model.buildings.BuildingType;
import ru.spbstu.icc.kspt.inspace.model.exception.*;
import ru.spbstu.icc.kspt.inspace.model.fleet.ShipType;
import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;
import ru.spbstu.icc.kspt.inspace.service.documents.requests.MissionStartInfo;
import ru.spbstu.icc.kspt.inspace.service.documents.requests.PlanetCreateInfo;
import ru.spbstu.icc.kspt.inspace.service.documents.responses.*;

import java.util.*;

@RestController
@RequestMapping(value = "planets")
public class PlanetsController {

    {
        Random random = new Random(214);
        for (int i = 0; i < 50; i++) {
            int numberOfSystem;
            int numberOfPlanet;
            numberOfPlanet = random.nextInt(15);
            numberOfSystem = random.nextInt(15);
            Galaxy.getInstance().
                    addPlanet(new Position(numberOfSystem, numberOfPlanet), "planet" + random.nextInt());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    Planet addPlanet(@RequestBody PlanetCreateInfo info)
        throws AssertionError{

        Position position = new Position(info.getNumberOfSystem(),
                                         info.getNumberOfPlanet());

        Galaxy.getInstance().addPlanet(position, info.getName());
        try {
            return new Planet(Galaxy.getInstance().getPlanet(position));
        } catch (PlanetDoesntExist planetDoesntExist) {
            throw new AssertionError();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    void deletePlanet(PlanetDescription description) {

        Position position = new Position(description.getPosition().getNumberOfSystem(),
                description.getPosition().getNumberOfPlanet());

        Galaxy.getInstance().deletePlanet(position);
    }

    @RequestMapping(method = RequestMethod.GET)
    List<PlanetDescription> planets() {

        List<PlanetDescription> allDescriptions = new ArrayList<>();
        Galaxy.getInstance().getAllPlanets().
                forEach(planet -> allDescriptions.add(new PlanetDescription(planet)));
        return allDescriptions;
    }

    @RequestMapping(value = {"{numberOfSystem}"},
                    method = RequestMethod.GET)
    List<PlanetDescription> system(@PathVariable("numberOfSystem") int numberOfSystem) {

        List<PlanetDescription> descriptions = new ArrayList<>();
        Galaxy.getInstance().getPlanets(numberOfSystem)
                .forEach(planet -> descriptions.add(new PlanetDescription(planet)));
        return descriptions;
    }

    @RequestMapping(value = "{numberOfSystem}/{numberOfPlanet}",
                    method = RequestMethod.GET)
    Planet planet(@PathVariable("numberOfSystem") int numberOfSystem,
                  @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist {

        APlanet planet = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet);
        return new Planet(planet);
    }

    @RequestMapping(value = "{numberOfSystem}/{numberOfPlanet}/rename",
                    method = RequestMethod.PUT)
    void renamePlanet(@PathVariable("numberOfSystem") int numberOfSystem,
                      @PathVariable("numberOfPlanet") int numberOfPlanet,
                      @RequestParam("name") String name)
            throws PlanetDoesntExist {

        Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).rename(name);
    }
}
