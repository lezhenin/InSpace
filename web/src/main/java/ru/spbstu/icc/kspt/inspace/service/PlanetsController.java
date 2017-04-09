package ru.spbstu.icc.kspt.inspace.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.icc.kspt.inspace.api.ABuilding;
import ru.spbstu.icc.kspt.inspace.model.Galaxy;
import ru.spbstu.icc.kspt.inspace.model.Position;
import ru.spbstu.icc.kspt.inspace.service.documents.Building;


import java.util.ArrayList;
import java.util.List;

@RestController
public class PlanetsController {

    @RequestMapping("planets/{system-number}/{planet-number}/buildings")
    List<Building> buildings(@PathVariable("system-number") int numberOfSystem,
                             @PathVariable("planet-number") int numberOfPlanet) {

        List<Building> buildings = new ArrayList<>();

        for (ABuilding building: Galaxy.getInstance().
                                getPlanet(new Position(numberOfSystem, numberOfPlanet)).
                                getBuildings().values()) {
            buildings.add(new Building(building));
        }

        return buildings;
    }

}
