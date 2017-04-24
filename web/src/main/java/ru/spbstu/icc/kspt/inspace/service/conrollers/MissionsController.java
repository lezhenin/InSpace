package ru.spbstu.icc.kspt.inspace.service.conrollers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.spbstu.icc.kspt.inspace.api.AMission;
import ru.spbstu.icc.kspt.inspace.api.APlanet;
import ru.spbstu.icc.kspt.inspace.model.Galaxy;
import ru.spbstu.icc.kspt.inspace.model.Position;
import ru.spbstu.icc.kspt.inspace.model.exception.CapacityExcessException;
import ru.spbstu.icc.kspt.inspace.model.exception.FleetDetachException;
import ru.spbstu.icc.kspt.inspace.model.exception.PlanetDoesntExist;
import ru.spbstu.icc.kspt.inspace.service.documents.requests.MissionStartInfo;
import ru.spbstu.icc.kspt.inspace.service.documents.responses.Fleet;
import ru.spbstu.icc.kspt.inspace.service.documents.responses.Mission;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("planets/{numberOfSystem}/{numberOfPlanet}")
public class MissionsController {

    @RequestMapping(value = "fleet",
            method = RequestMethod.GET)
    Fleet fleet(@PathVariable("numberOfSystem") int numberOfSystem,
                @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist {

        return new Fleet(Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet).getFleetOnPlanet());
    }

    @RequestMapping(value = "missions",
            method = RequestMethod.GET)
    List<Mission> missions(@PathVariable("numberOfSystem") int numberOfSystem,
                           @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist {

        List<Mission> list = new ArrayList<>();
        APlanet planet = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet);
        planet.getMissions().forEach(mission -> list.add(new Mission(mission)));
        return list;
    }

    @RequestMapping(value = "external-missions",
            method = RequestMethod.GET)
    List<Mission> externalMissions(@PathVariable("numberOfSystem") int numberOfSystem,
                                   @PathVariable("numberOfPlanet") int numberOfPlanet)
            throws PlanetDoesntExist {

        List<Mission> list = new ArrayList<>();
        APlanet planet = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet);
        planet.getExternalMissions().forEach(mission -> list.add(new Mission(mission)));
        return list;
    }

    @RequestMapping(value = "start-mission",
            method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    Mission startMission(@PathVariable("numberOfSystem") int numberOfSystem,
                         @PathVariable("numberOfPlanet") int numberOfPlanet,
                         @RequestBody MissionStartInfo info)
            throws PlanetDoesntExist, CapacityExcessException, FleetDetachException {

        APlanet planet = Galaxy.getInstance().getPlanet(numberOfSystem, numberOfPlanet);
        Position position = new Position(info.getNumberOfSystem(),
                info.getNumberOfPlanet());

        AMission mission = planet.startMission(info.getType(), position, info.getNumbersOfShips(),
                info.getMetal(), info.getCrystals(), info.getDeuterium());

        if (mission == null) {
            throw new AssertionError();
        }

        return new Mission(mission);
    }
}
