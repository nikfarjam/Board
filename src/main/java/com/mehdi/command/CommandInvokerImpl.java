package com.mehdi.command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CommandInvokerImpl implements CommandInvoker {
    @Override
    public void runCommands(Iterable<Command> commands) {
        boolean started = false;
        for (Command command : commands) {
            if (!started && command instanceof Place) {
                started = true;
            }
            if (started) {
                command.execute();
            }
        }
    }

    @Override
    public void runCommands(Stream<Command> commands) {
        final List<Boolean> started = new ArrayList<>();
        commands.forEach(command -> {
            if (started.isEmpty() && command instanceof Place) {
                started.add(Boolean.TRUE);
            }
            if (!started.isEmpty() && Boolean.TRUE.equals(started.get(0))) {
                command.execute();
            }
        });
    }
}
