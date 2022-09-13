package com.mehdi.command;

import java.util.stream.Stream;

public class CommandInvokerImpl implements CommandInvoker {
    @Override
    public void runCommands(Iterable<Command> commands) {
        commands.forEach(Command::execute);
    }

    @Override
    public void runCommands(Stream<Command> commands) {
        commands.forEach(Command::execute);
    }
}
