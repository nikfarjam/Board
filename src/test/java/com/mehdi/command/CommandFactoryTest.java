package com.mehdi.command;

import com.mehdi.board.Board;
import com.mehdi.board.BoardImpl;
import com.mehdi.board.RobotImpl;
import com.mehdi.model.Direction;
import com.mehdi.model.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommandFactoryTest {

    @Test
    void createPlaceCommand() {
        CommandFactory factory = new CommandFactory();
        Board board = new BoardImpl(5 ,5);
        Board spyBoard = spy(board);
        factory.setBoard(spyBoard);
        Command placeCommand = factory.createCommand("PLACE 1,2,NORTH");

        placeCommand.execute();

        verify(spyBoard, times(1))
                .addRobot(Position.Builder.aPosition().column(1).row(2).facing(Direction.NORTH).build());
    }

    @Test
    void createActiveRobotCommand() {
        CommandFactory factory = new CommandFactory();
        Board board = new BoardImpl(5 ,5);
        Board spyBoard = spy(board);
        factory.setBoard(spyBoard);
        spyBoard.addRobot(Position.Builder.aPosition().column(1).row(2).facing(Direction.NORTH).build());
        Command activeCommand = factory.createCommand("ROBOT 1");

        activeCommand.execute();

        verify(spyBoard, times(1)).changeActiveRobot(1);
    }

    @ParameterizedTest
    @MethodSource("provideSampleCommandString")
    void createSimpleCommand(String commandMessage, String className) {
        CommandFactory factory = new CommandFactory();

        Command command = factory.createCommand(commandMessage);

        assertEquals(command.getClass().getName(), className);
    }

    private static Stream<Arguments> provideSampleCommandString() {
        return Stream.of(
                Arguments.of("RIGHT", "com.mehdi.command.Right"),
                Arguments.of("LEFT", "com.mehdi.command.Left"),
                Arguments.of("REPORT", "com.mehdi.command.Report"),
                Arguments.of("PLACE 0,0,NORTH", "com.mehdi.command.Place"),
                Arguments.of("ROBOT 2", "com.mehdi.command.Active"),
                Arguments.of("MOVE", "com.mehdi.command.Move")
        );
    }
}