package ru.fmeter.utils;

public enum Locals {
    ENG("en"),
    ESP("es"),
    RUS("ru");

    private String code;

    Locals(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
