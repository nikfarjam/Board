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

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws CommandReaderException {
        // Check if everything is fine
        Optional<CommandReader> commandReader = CommandReaderFactory.getInstance().create(CommandReaderFactory.FILE);
        if (commandReader.isEmpty()) {
            logger.warn("Could not create reader factory");
            return;
        }

        // Initiate beans and pass to BoardClient to run the application
        // Not an elegant use of abstract factory
        // To run application, pass full path of input file as argument
        CommandReader reader = commandReader.get();
        if (reader instanceof FileCommandReader fileCommandReader) {
            String inputFilePath = null;
            if (args != null && args.length > 0) {
                inputFilePath = args[0];
            } else {
                logger.warn("No file to load commands");
                throw new CommandReaderException("No file to load commands");
            }
            fileCommandReader.setPath(Path.of(inputFilePath));
            logger.info("Load commands from {}", inputFilePath);
        }

        CommandFactory commandFactory = new CommandFactory();
        CommandInvoker invoker = new CommandInvokerImpl();
        Reporter reporter = new ReporterImpl();
        int numberOfColumns = Optional.ofNullable(System.getenv("number_of_columns"))
                .map(Integer::parseInt)
                .orElse(5);
        int numberOfRows = Optional.ofNullable(System.getenv("number_of_rows"))
                .map(Integer::parseInt)
                .orElse(5);
        Board board = new BoardImpl(numberOfColumns, numberOfRows);
        logger.info("Initiate Board");

        // Create App and run it
        BoardClient boardClient = new BoardClientImpl(board, reporter, invoker, reader, commandFactory);
        boardClient.runRobot();
    }
}