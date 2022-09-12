package com.mehdi.command;

import com.mehdi.board.Board;
import com.mehdi.error.InvalidCommand;
import com.mehdi.model.Direction;
import com.mehdi.model.Position;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandFactory {
    private Board board;
    private Pattern placeCommand;
    private Pattern robotCommand;

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return this.board;
    }

    public Pattern getPlaceCommand() {
        if (placeCommand == null) {
            placeCommand = Pattern.compile("^PLACE\\s+(\\d)\\s*,\\s*(\\d)\\s*,\\s*(NORTH|EAST|SOUTH|WEST)");
        }
        return placeCommand;
    }

    public Pattern getRobotCommand() {
        if (robotCommand == null) {
            robotCommand = Pattern.compile("^ROBOT\\s+(\\d)");
        }
        return robotCommand;
    }

    public Command createCommand(String command) {
        sanitationCommandString(command);
        validateCommandString(command);
        var cmd = command.toUpperCase();

        Matcher placeCommandMatcher = getPlaceCommand().matcher(cmd);
        if (placeCommandMatcher.find()) {
            int column = Integer.parseInt(placeCommandMatcher.group(1));
            int row = Integer.parseInt(placeCommandMatcher.group(2));
            Direction facing = Direction.valueOf(placeCommandMatcher.group(3));
            Position p = Position.Builder.aPosition().column(column).row(row).facing(facing).build();
            return new Place(this.board, p);
        }
        if (cmd.equals("LEFT")) {
            return new Left(this.board);
        }
        if (cmd.equals("RIGHT")) {
            return new Right(this.board);
        }
        Matcher robotMatcher = getRobotCommand().matcher(cmd);
        if (robotMatcher.find()) {
            int index = Integer.parseInt(robotMatcher.group(1));
            return new Active(this.board, index);
        }
        if (cmd.equals("MOVE")) {
            return new Move(this.board);
        }
        if (cmd.startsWith("REPORT")) {
            return new Report(this.board);
        }
        throw new InvalidCommand("Command is not in valid list");
    }

    private void validateCommandString(String cmd) throws InvalidCommand {
        if (cmd == null || "".equals(cmd.trim())) {
            throw new InvalidCommand("Command is empty");
        }
    }

    private void sanitationCommandString(String cmd) throws InvalidCommand {
        // Use regular exprestion to check if a command contains harmful script
    }
}
