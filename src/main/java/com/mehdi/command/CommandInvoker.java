package com.mehdi.command;

import java.util.stream.Stream;

public interface CommandInvoker {

    void runCommands(Iterable<Command> commands);

    void runCommands(Stream<Command> commands);
}
