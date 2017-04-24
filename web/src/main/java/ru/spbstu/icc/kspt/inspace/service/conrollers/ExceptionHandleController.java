package ru.spbstu.icc.kspt.inspace.service.conrollers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.spbstu.icc.kspt.inspace.model.exception.*;
import ru.spbstu.icc.kspt.inspace.service.documents.responses.Error;
import ru.spbstu.icc.kspt.inspace.service.exceptions.UnitTypeException;

@ControllerAdvice
public class ExceptionHandleController {

    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason = "Can not convert unit type")
    @ExceptionHandler(UnitTypeException.class)
    public Error unitTypeExceptionHandler(UnitTypeException e) {
        return new Error(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ResponseStatus(value=HttpStatus.FAILED_DEPENDENCY, reason="Can not detach fleet")
    @ExceptionHandler(FleetDetachException.class)
    public Error fleetDetachExceptionHandler(FleetDetachException e) {
        return new Error(e.getMessage(), HttpStatus.FAILED_DEPENDENCY.value());
    }

    @ResponseStatus(value=HttpStatus.FAILED_DEPENDENCY, reason="Can not put all resources on fleet")
    @ExceptionHandler(CapacityExcessException.class)
    public Error capacityExcessExceptionHandler(CapacityExcessException e) {
        return new Error(e.getMessage(), HttpStatus.FAILED_DEPENDENCY.value());
    }

    @ResponseStatus(value=HttpStatus.FAILED_DEPENDENCY, reason="Can not start upgrade")
    @ExceptionHandler(ConstructException.class)
    public Error constructExceptionHandler(ConstructException e) {
        return new Error(e.getMessage(), HttpStatus.FAILED_DEPENDENCY.value());
    }

    @ResponseStatus(value=HttpStatus.FAILED_DEPENDENCY, reason="Can not start upgrade")
    @ExceptionHandler(UpgradeException.class)
    public Error upgradeExceptionHandler(UpgradeException e) {
        return new Error(e.getMessage(), HttpStatus.FAILED_DEPENDENCY.value());
    }

    @ResponseStatus(value=HttpStatus.NO_CONTENT, reason="Planet does not exist")
    @ExceptionHandler(PlanetDoesntExist.class)
    public void planetDoesntExistHandler(PlanetDoesntExist e) {  }

    @ResponseStatus(value=HttpStatus.NO_CONTENT, reason="Requested action is not performing")
    @ExceptionHandler(ActionIsNotPerforming.class)
    public void actionIsNotPerformingHandler(ActionIsNotPerforming e) { }
}
