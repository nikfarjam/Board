package com.mehdi.command;

import com.mehdi.board.Board;
import com.mehdi.board.BoardImpl;
import com.mehdi.error.InvalidCommand;
import com.mehdi.model.Position;

public class CommandFactory {

    private static CommandFactory instance;

    private Board board;

    private CommandFactory() {
    }

    public static CommandFactory getInstance() {
        if (instance == null) {
            //synchronized block to remove overhead
            synchronized (CommandFactory.class) {
                if (instance == null) {
                    // if instance is null, initialize
                    instance = new CommandFactory();
                }
            }
        }
        return instance;
    }

    public Board getBoard() {
        if (board == null) {
            board = new BoardImpl(5, 5);
        }
        return board;
    }

    public Command createCommand(String cmd) {
        Board localBoard = getBoard();

        sanitationCommandString();
        validateCommandString();
        cmd = cmd.toUpperCase();

        if (cmd.startsWith("PLACE")) {
            return new Place(localBoard, Position.Builder.aPosition().build());
        }
        if (cmd.equals("LEFT")) {
            return new Left(localBoard);
        }
        if (cmd.equals("RIGHT")) {
            return new Right(localBoard);
        }
        if (cmd.startsWith("ROBOT")) {
            return new Active(localBoard, 0);
        }
        if (cmd.equals("MOVE")) {
            return new Move(localBoard);
        }
        if (cmd.startsWith("REPORT")) {
            return new Report(localBoard);
        }
        throw new InvalidCommand("Command is not in valid list");
    }

    private void validateCommandString() throws InvalidCommand {
    }

    private void sanitationCommandString() throws InvalidCommand {

    }
}
