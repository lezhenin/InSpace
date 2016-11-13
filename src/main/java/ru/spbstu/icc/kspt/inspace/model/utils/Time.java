package ru.spbstu.icc.kspt.inspace.model.utils;


import java.time.LocalDateTime;

//TODO Не уверен в целесобразности такого класса. В местах вызова читаемость и понятность вроде не улучшается

public class Time {
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
}
