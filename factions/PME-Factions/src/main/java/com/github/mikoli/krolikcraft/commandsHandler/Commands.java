package com.github.mikoli.krolikcraft.commandsHandler;

import java.util.stream.Stream;

public enum Commands {

    NULL("");

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
