package ru.clevertec.check.controllers;

import ru.clevertec.check.services.CheckService;

import static ru.clevertec.check.services.CheckServiceImpl.getInstance;

public class GlobalController {

    private static final CheckService checkService = getInstance();

    public static void start(String[] parameters) {
        checkService.printCheck(parameters);
    }
}