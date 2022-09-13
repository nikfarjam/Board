package com.mehdi.command;

import com.mehdi.board.Board;
import com.mehdi.error.CommandReaderException;
import com.mehdi.reader.CommandReader;
import com.mehdi.report.Reporter;

import java.util.Optional;

public class BoardClientImpl implements BoardClient {

    private final Board board;
    private final Reporter reporter;
    private final CommandInvoker invoker;
    private final CommandReader reader;

    private final CommandFactory commandFactory;

    public BoardClientImpl(Board board, Reporter reporter, CommandInvoker invoker, CommandReader reader, CommandFactory commandFactory) {
        this.board = board;
        this.reporter = reporter;
        this.invoker = invoker;
        this.reader = reader;
        this.commandFactory = commandFactory;
    }

    @Override
    public void runRobot() throws CommandReaderException {
        board.addReporter(reporter);
        commandFactory.setBoard(board);

        invoker.runCommands(
                reader.getCommandStream()
                        .map(commandFactory::create)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
        );
    }
}
