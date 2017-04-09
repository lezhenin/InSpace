package ru.spbstu.icc.kspt.inspace.service;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.icc.kspt.inspace.api.APlanet;
import ru.spbstu.icc.kspt.inspace.model.Galaxy;
import ru.spbstu.icc.kspt.inspace.model.Planet;
import ru.spbstu.icc.kspt.inspace.model.Position;
import ru.spbstu.icc.kspt.inspace.service.documents.PlanetInfo;
import ru.spbstu.icc.kspt.inspace.service.documents.PlanetSystem;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.*;

@RestController
public class SystemsController {

    {
        Random random = new Random(214);
        for (int i = 0; i < 30; i++) {
            int numberOfSystem;
            int numberOfPlanet;
            numberOfPlanet = random.nextInt(10);
            numberOfSystem = random.nextInt(10);
            new Planet("planet" + random.nextInt(), new Position(numberOfSystem, numberOfPlanet));
        }
    }

    @RequestMapping(value = {"/planets", "/galaxy", "/systems"} )
    List<PlanetSystem> planets(HttpServletRequest request) {

        List<PlanetSystem> planetSystems = new ArrayList<>();

        for (int i = 0; i < Galaxy.MAX_SYSTEM_NUMBER; i++) {
            List<PlanetInfo> infos = new ArrayList<>();
            for (APlanet planet: Galaxy.getInstance().getPlanets(i)) {
                infos.add(new PlanetInfo(planet, getBaseURL(request) + "planets/"));
            }
            planetSystems.add(new PlanetSystem("PlanetSystem " + String.valueOf(i), i, infos,
                    getBaseURL(request) + "planets/"));
        }

        return planetSystems;
    }

    @RequestMapping("/planets/{system-number}")
    PlanetSystem system(@PathVariable("system-number") int systemNumber, HttpServletRequest request)
            throws MalformedURLException {

        List<PlanetInfo> infos = new ArrayList<>();
        for (APlanet planet : Galaxy.getInstance().getPlanets(systemNumber)) {
            infos.add(new PlanetInfo(planet, getBaseURL(request) + "planets/"));
        }
        return new PlanetSystem("PlanetSystem " + String.valueOf(systemNumber), systemNumber, infos,
                getBaseURL(request) + "planets/");
    }

    private String getBaseURL(HttpServletRequest request) {
        return String.format("%s://%s:%d/", request.getScheme(), request.getServerName(), request.getServerPort());
    }
}
