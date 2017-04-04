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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
public class PlanetsController {

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

    @RequestMapping("/planets")
    List<PlanetSystem> planets(){

        List<PlanetSystem> planetSystems = new ArrayList<>();
        for (int i = 0; i < Galaxy.MAX_SYSTEM_NUMBER; i++) {
            List<PlanetInfo> infos = new ArrayList<>();
            for (APlanet planet: Galaxy.getInstance().getPlanets(i)) {
                infos.add(new PlanetInfo(planet));
            }
            planetSystems.add(new PlanetSystem("PlanetSystem " + String.valueOf(i), i, infos));
        }
        return planetSystems;
    }

    @RequestMapping("/planets/{system-number}")
    PlanetSystem system(@PathVariable("system-number") int systemNumber, HttpServletRequest request,
                        HttpServletResponse response) {
        System.out.println(request.getServletPath());
        System.out.println(request.getContextPath());
        System.out.println(request.getRequestURI());
        List<PlanetInfo> infos = new ArrayList<>();
        for (APlanet planet : Galaxy.getInstance().getPlanets(systemNumber)) {
            infos.add(new PlanetInfo(planet));
        }
        return new PlanetSystem("PlanetSystem " + String.valueOf(systemNumber), systemNumber, infos);
    }

    @RequestMapping("/test")
    Map<String, Integer> test() {
        Map<String, Integer> map = new HashMap<>();
        map.put("key1", 23);
        map.put("key2", 25);
        return map;
    }
}
