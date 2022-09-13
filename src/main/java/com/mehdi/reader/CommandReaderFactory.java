package com.mehdi.reader;

import com.mehdi.AbstractFactory;

import java.util.Optional;

public class CommandReaderFactory implements AbstractFactory<CommandReader> {

    public static final String FILE = "FILE";

    private static CommandReaderFactory instance;

    private CommandReaderFactory() {
    }

    public static synchronized CommandReaderFactory getInstance() {
        if (instance == null)
            instance = new CommandReaderFactory();
        return instance;
    }


    @Override
    public Optional<CommandReader> create(String type) {
        // TODO: To have a CLI, implement ConsoleCommandReader
        return switch (type) {
            case FILE -> Optional.of(new FileCommandReader());
            default -> Optional.empty();
        };
    }

}
