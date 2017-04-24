package ru.spbstu.icc.kspt.inspace.service.exceptions;

public class UnitTypeException extends Exception {
    public UnitTypeException(String typeString) {
        super("Can not transform " + typeString + " into game model unit type");
    }
}
