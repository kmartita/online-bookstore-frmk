package com.allwyn.tools.data;

import lombok.Getter;

@Getter
public enum Entity {

    TEAM("Books"),
    SPACE("Authors");

    private final String name;

    Entity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Entity[name=%s", name);
    }
}

