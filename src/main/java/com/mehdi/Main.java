package com.mehdi;

import com.mehdi.board.Board;
import com.mehdi.board.BoardImpl;
import com.mehdi.command.*;
import com.mehdi.error.CommandReaderException;
import com.mehdi.reader.CommandReader;
import com.mehdi.reader.CommandReaderFactory;
import com.mehdi.reader.FileCommandReader;
import com.mehdi.report.Reporter;
import com.mehdi.report.ReporterImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.Optional;

public class Main {

    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws CommandReaderException {
        // Check if everything is fine
        // To run application, pass full path of input file as argument
        if (args == null || args.length == 0) {
            return;
        }
        Optional<CommandReader> commandReader = CommandReaderFactory.getInstance().create(CommandReaderFactory.FILE);
        if (commandReader.isEmpty()) {
            logger.warn("Could not create reader factory");
            return;
        }

        // Initiate beans
        // Not an elegant use of abstract factory
        CommandReader reader = commandReader.get();
        if (reader instanceof FileCommandReader fileCommandReader) {
            fileCommandReader.setPath(Path.of(args[0]));
        }

        CommandFactory commandFactory = new CommandFactory();
        CommandInvoker invoker = new CommandInvokerImpl();
        Reporter reporter = new ReporterImpl();
        Board board = new BoardImpl(5, 5);

        // Create App and run it
        BoardClient boardClient = new BoardClientImpl(board, reporter, invoker, reader, commandFactory);
        boardClient.runRobot();
    }
}