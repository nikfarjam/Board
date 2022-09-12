package com.mehdi.board;

import com.mehdi.error.BoardException;
import com.mehdi.model.Direction;
import com.mehdi.model.Position;
import com.mehdi.model.PositionDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RobotImplTest {

    private static Logger logger = LogManager.getLogger(RobotImplTest.class);
    
    private MoveValidator validator;

    @BeforeEach
    void setUp() {
        this.validator = position -> true;
    }

    @Test
    void positionMustBeCorrect() {
        Position position = Position.Builder.aPosition().row(1).column(2).facing(Direction.NORTH).build();
        RobotImpl robot = new RobotImpl(position, validator);

        PositionDetails robotPosition = robot.getPosition();

        assertEquals(1, robotPosition.getRow());
        assertEquals(2, robotPosition.getColumn());
        assertEquals(Direction.NORTH, robotPosition.getFacing());
    }

    @Test
    void changeDirectionAfterEachRotateLeft() {
        Position position = Position.Builder.aPosition().row(1).column(2).facing(Direction.NORTH).build();
        RobotImpl robot = new RobotImpl(position, validator);

        robot.rotateLeft();
        assertEquals(Direction.EAST, robot.getPosition().getFacing());

        robot.rotateLeft();
        assertEquals(Direction.SOUTH, robot.getPosition().getFacing());

        robot.rotateLeft();
        assertEquals(Direction.WEST, robot.getPosition().getFacing());

        robot.rotateLeft();
        assertEquals(Direction.NORTH, robot.getPosition().getFacing());
    }

    @Test
    void doNotMoveAfterRotateLeft() {
        Position position = Position.Builder.aPosition().row(1).column(2).facing(Direction.NORTH).build();
        RobotImpl robot = new RobotImpl(position, validator);

        robot.rotateLeft();

        PositionDetails robotPosition = robot.getPosition();
        assertEquals(1, robotPosition.getRow());
        assertEquals(2, robotPosition.getColumn());
    }

    @Test
    void changeDirectionAfterEachRotateRight() {
        Position position = Position.Builder.aPosition().row(1).column(2).facing(Direction.NORTH).build();
        RobotImpl robot = new RobotImpl(position, validator);

        robot.rotateRight();
        assertEquals(Direction.WEST, robot.getPosition().getFacing());

        robot.rotateRight();
        assertEquals(Direction.SOUTH, robot.getPosition().getFacing());

        robot.rotateRight();
        assertEquals(Direction.EAST, robot.getPosition().getFacing());

        robot.rotateRight();
        assertEquals(Direction.NORTH, robot.getPosition().getFacing());
    }

    @Test
    void doNotMoveAfterRotateRight() {
        Position position = Position.Builder.aPosition().row(1).column(2).facing(Direction.NORTH).build();
        RobotImpl robot = new RobotImpl(position, validator);

        robot.rotateRight();

        PositionDetails robotPosition = robot.getPosition();
        assertEquals(1, robotPosition.getRow());
        assertEquals(2, robotPosition.getColumn());
    }

    @Test
    void doNotMoveWhenItIsNotValid() {
        validator = position -> false;
        Position position = Position.Builder.aPosition().row(1).column(2).facing(Direction.NORTH).build();
        RobotImpl robot = new RobotImpl(position, validator);

        boolean result = robot.move();

        assertFalse(result);
        assertEquals(position.getValue(), robot.getPosition());
    }

    @Test
    void whenPositionIsNotValidMoveThrowsException() {
        Position position = Position.Builder.aPosition().row(1).column(2).facing(Direction.UNDEFINED).build();
        RobotImpl robot = new RobotImpl(position, validator);

        assertThrows(BoardException.class, robot::move);
    }

    @ParameterizedTest
    @MethodSource("provideSamplePosition")
    void moveRobot(Position position, Position expected) {
        logger.info("testing {}", position);
        RobotImpl robot = new RobotImpl(position, validator);

        boolean result = robot.move();

        assertTrue(result);
        assertEquals(expected.getValue(), robot.getPosition());
    }

    private static Stream<Arguments> provideSamplePosition() {
        return Stream.of(
                Arguments.of(Position.Builder.aPosition().row(2).column(2).facing(Direction.NORTH).build(), Position.Builder.aPosition().row(3).column(2).facing(Direction.NORTH).build()),
                Arguments.of(Position.Builder.aPosition().row(2).column(2).facing(Direction.EAST).build(), Position.Builder.aPosition().row(2).column(3).facing(Direction.EAST).build()),
                Arguments.of(Position.Builder.aPosition().row(2).column(2).facing(Direction.SOUTH).build(), Position.Builder.aPosition().row(1).column(2).facing(Direction.SOUTH).build()),
                Arguments.of(Position.Builder.aPosition().row(2).column(2).facing(Direction.WEST).build(), Position.Builder.aPosition().row(2).column(1).facing(Direction.WEST).build())
        );
    }
}