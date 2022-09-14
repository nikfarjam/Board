package com.mehdi.command;

import com.mehdi.board.Board;
import com.mehdi.board.BoardImpl;
import com.mehdi.error.BoardException;
import com.mehdi.error.CommandReaderException;
import com.mehdi.error.InvalidCommand;
import com.mehdi.reader.CommandReader;
import com.mehdi.report.ReporterImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BoardClientImplTest {

    private BoardClientImpl boardClient;

    private ReporterImpl reporter;
    private List<String> commands;

    @BeforeEach
    void setUp() {
        CommandFactory commandFactory = new CommandFactory();
        CommandInvoker invoker = new CommandInvokerImpl();
        Board board = new BoardImpl(5, 5);
        CommandReader reader = new CommandReader() {
            @Override
            public Stream<String> getCommandStream() throws CommandReaderException {
                return commands.stream();
            }

            @Override
            public Iterator<String> getCommandIterable() throws CommandReaderException {
                return commands.iterator();
            }
        };

        reporter = spy(ReporterImpl.class);
        boardClient = new BoardClientImpl(board, reporter, invoker, reader, commandFactory);
    }

    @Test
    void whenCommandsDoesNotHaveReport() {
        commands = List.of("PLACE 1,2,EAST", "LEFT");

        verify(reporter, never()).showReport(anyString());
    }

    @ParameterizedTest
    @MethodSource("provideSampleInvalidCommands")
    void whenCommandsAreNotValidReportMustThrowsError(List<String> emptyCommands) throws CommandReaderException {
        commands = emptyCommands;

        assertThrows(InvalidCommand.class, boardClient::runRobot);
    }


    @Nested
    @DisplayName("Single Robot will operate on the table")
    class SingleRobot {

        @Test
        @DisplayName("Ignore commands until first PLACE command")
        void ignoreCommandsBeforePlace() throws CommandReaderException {
            commands = List.of("LEFT", "MOVE", "REPORT", "PLACE 1,2,EAST", "MOVE", "MOVE", "LEFT", "MOVE", "REPORT");

            boardClient.runRobot();

            verify(reporter, times(1)).showReport("3,3,NORTH");
        }

        @Test
        void robotNeverMovesOutOfBoard() throws CommandReaderException {
            commands = List.of("PLACE 0,0,NORTH", "LEFT ", "MOVE", "MOVE", "REPORT");

            boardClient.runRobot();

            verify(reporter).showReport("0,0,WEST");
        }

        @ParameterizedTest
        @MethodSource("provideSampleSingleRobot")
        void showRobotPosition(List<String> validCommands, String expected) throws CommandReaderException {
            commands = validCommands;

            boardClient.runRobot();

            verify(reporter, times(1)).showReport(expected);
        }

        private static Stream<Arguments> provideSampleSingleRobot() {
            return Stream.of(
                    Arguments.of(List.of("PLACE 0,0,NORTH\n", "MOVE ", "report"), "0,1,NORTH"),
                    Arguments.of(List.of("PLACE 1,2,EAST", "MOVE ", "MOVE", "LEFT", "MOVE", "REPORT"), "3,3,NORTH"),
                    Arguments.of(List.of("PLACE 0,0,NORTH", "LEFT ", "REPORT"), "0,0,WEST")
            );
        }
    }

    @Nested
    @DisplayName("Multiple robots will operate on the table")
    class MultiRobots {

        @Test
        void whenPlaceARRobotInAnOccupiedRoom() {
            commands = List.of("PLACE 0,0,NORTH", "RIGHT", "PLACE 0,0,SOUTH", "REPORT");

            assertThrows(BoardException.class, boardClient::runRobot);
        }

        @Test
        void whenARobotGoesToAnOccupiedRoom() throws CommandReaderException {
            commands = List.of("PLACE 2,2,NORTH", "PLACE 2,3,SOUTH", "MOVE", "REPORT");

            boardClient.runRobot();

            verify(reporter).showReport("""
                    2 ROBOTS
                    2 IS ACTIVE
                    ROBOT1 2,2,NORTH
                    ROBOT2 2,3,SOUTH""");
        }

        @ParameterizedTest
        @MethodSource("provideSampleMultiRobots")
        void showMultiRobotPositions(List<String> validCommands, String expected) throws CommandReaderException {
            commands = validCommands;

            boardClient.runRobot();

            verify(reporter, times(1)).showReport(expected);
        }

        private static Stream<Arguments> provideSampleMultiRobots() {
            return Stream.of(
                    Arguments.of(List.of("PLACE 0,0,NORTH", "MOVE", "PLACE 2,3,NORTH", "MOVE", "RIGHT", "MOVE", "report"),
                            """
                                    2 ROBOTS
                                    2 IS ACTIVE
                                    ROBOT1 0,1,NORTH
                                    ROBOT2 3,4,EAST"""),
                    Arguments.of(List.of("PLACE 0,0,NORTH", "MOVE", "PLACE 2,3,NORTH", "MOVE", "RIGHT", "ROBOT 1", "MOVE", "report"),
                            """
                                    2 ROBOTS
                                    1 IS ACTIVE
                                    ROBOT1 0,2,NORTH
                                    ROBOT2 2,4,EAST""")
            );
        }
    }

    private static Stream<Arguments> provideSampleInvalidCommands() {
        return Stream.of(
                Arguments.of(List.of("")),
                Arguments.of(List.of("ZZZZ"))
        );
    }


}