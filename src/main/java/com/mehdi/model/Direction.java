package com.mehdi.model;

public enum Direction {
    UNDEFINED("UNDEFINED"),
    NORTH("NORTH"),
    SOUTH("SOUTH"),
    EAST("EAST"),
    WEST("WEST");

    private String name;

    Direction(String name) {
        this.name = name;
    }
}
