package ru.spbstu.icc.kspt.inspace.service;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbstu.icc.kspt.inspace.model.Galaxy;
import ru.spbstu.icc.kspt.inspace.service.documents.System;

import java.util.HashSet;
import java.util.Set;

@RestController
public class PlanetsController {

    @RequestMapping("/planets")
    Set<System> planets(){
        Set<System> systems = new HashSet<>();

        return systems;
    }
}
