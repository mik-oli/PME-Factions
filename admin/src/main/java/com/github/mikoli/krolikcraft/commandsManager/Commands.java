package com.github.mikoli.krolikcraft.commandsManager;

import java.util.stream.Stream;

public enum Commands {

    STAFFWLSTATUS("status"),
    STAFFWLADD("add"),
    STAFFWLREMOVE("remove"),
    STAFFWLLIST("list"),
    STAFFWLON("on"),
    STAFFWLOFF("off");

    private final String command;

    Commands(String command) {
        this.command = command;
    }

    public String getCmd() {
        return command;
    }

    public static Stream<Commands> commandsStream() {
        return Stream.of(Commands.values());
    }
}
