package com.mehdi.reader;

import com.mehdi.AbstractFactory;

import java.util.Optional;

public class CommandReaderFactory implements AbstractFactory {

    @Override
    public Optional<CommandReader> create(String type) {
        return switch (type) {
            case "FILE" -> Optional.of(new FileCommandReader());
            default -> Optional.empty();
        };
    }

}
