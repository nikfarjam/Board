package com.mehdi.command;

import com.mehdi.AbstractFactory;
import com.mehdi.board.Board;
import com.mehdi.error.InvalidCommand;
import com.mehdi.model.Direction;
import com.mehdi.model.Position;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandFactory implements AbstractFactory {
    private Board board;
    private Pattern placeCommandPattern;
    private Pattern robotCommandPattern;
    private Command leftCommand;
    private Command rightCommand;
    private Command moveCommand;
    private Command reportCommand;

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public Optional<Command> create(String command) {
        sanitationCommandString(command);
        validateCommandString(command);
        var cmd = command.trim().toUpperCase();

        Matcher placeCommandMatcher = getPlaceCommandPattern().matcher(cmd);
        if (placeCommandMatcher.find()) {
            int column = Integer.parseInt(placeCommandMatcher.group(1));
            int row = Integer.parseInt(placeCommandMatcher.group(2));
            Direction facing = Direction.valueOf(placeCommandMatcher.group(3));
            Position p = Position.Builder.aPosition().column(column).row(row).facing(facing).build();
            return Optional.of(new Place(this.board, p));
        }
        if (cmd.equals("LEFT")) {
            return Optional.of(getLeftCommand());
        }
        if (cmd.equals("RIGHT")) {
            return Optional.of(getRightCommand());
        }
        Matcher robotMatcher = getRobotCommandPattern().matcher(cmd);
        if (robotMatcher.find()) {
            int index = Integer.parseInt(robotMatcher.group(1));
            return Optional.of(new Active(this.board, index));
        }
        if (cmd.equals("MOVE")) {
            return Optional.of(getMoveCommand());
        }
        if (cmd.startsWith("REPORT")) {
            return Optional.of(getReport());
        }
        throw new InvalidCommand("Command is not in valid list");
    }

    private Command getReport() {
        if (reportCommand == null) {
            reportCommand = new Report(this.board);
        }
        return reportCommand;
    }

    private Command getMoveCommand() {
        if (moveCommand == null) {
            moveCommand = new Move(this.board);
        }
        return moveCommand;
    }

    private Command getRightCommand() {
        if (rightCommand == null) {
            rightCommand = new Right(this.board);
        }
        return rightCommand;
    }

    private Command getLeftCommand() {
        if (leftCommand == null) {
            leftCommand = new Left(this.board);
        }
        return leftCommand;
    }

    private Pattern getPlaceCommandPattern() {
        if (placeCommandPattern == null) {
            placeCommandPattern = Pattern.compile("^PLACE\\s+(\\d)\\s*,\\s*(\\d)\\s*,\\s*(NORTH|EAST|SOUTH|WEST)");
        }
        return placeCommandPattern;
    }

    private Pattern getRobotCommandPattern() {
        if (robotCommandPattern == null) {
            robotCommandPattern = Pattern.compile("^ROBOT\\s+(\\d)");
        }
        return robotCommandPattern;
    }


    private void validateCommandString(String cmd) throws InvalidCommand {
        if (cmd == null || "".equals(cmd.trim())) {
            throw new InvalidCommand("Command is empty");
        }
    }

    private void sanitationCommandString(String cmd) throws InvalidCommand {
        // Use RegEX to check if a command contains harmful script
    }
}
