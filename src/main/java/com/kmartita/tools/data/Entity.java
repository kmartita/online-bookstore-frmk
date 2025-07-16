package com.kmartita.tools.data;

import lombok.Getter;

@Getter
public enum Entity {

    BOOKS("Books"),
    AUTHORS("Authors");

    private final String name;

    Entity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Entity[name=%s", name);
    }
}

