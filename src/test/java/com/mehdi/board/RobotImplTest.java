package com.mehdi.board;

import com.mehdi.model.Direction;
import com.mehdi.model.Position;
import com.mehdi.model.PositionDetails;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotImplTest {

    @Test
    void positionMustBeCorrect() {
        Position position = Position.Builder.aPosition().row(1).column(2).facing(Direction.NORTH).build();
        RobotImpl robot = new RobotImpl(position);

        PositionDetails robotPosition = robot.getPosition();

        assertEquals(1, robotPosition.getRow());
        assertEquals(2, robotPosition.getColumn());
        assertEquals(Direction.NORTH, robotPosition.getFacing());
    }

    @Test
    void changeDirectionAfterEachRotateLeft() {
        Position position = Position.Builder.aPosition().row(1).column(2).facing(Direction.NORTH).build();
        RobotImpl robot = new RobotImpl(position);

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
        RobotImpl robot = new RobotImpl(position);

        robot.rotateLeft();

        PositionDetails robotPosition = robot.getPosition();
        assertEquals(1, robotPosition.getRow());
        assertEquals(2, robotPosition.getColumn());
    }

    @Test
    void changeDirectionAfterEachRotateRight() {
        Position position = Position.Builder.aPosition().row(1).column(2).facing(Direction.NORTH).build();
        RobotImpl robot = new RobotImpl(position);

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
        RobotImpl robot = new RobotImpl(position);

        robot.rotateRight();

        PositionDetails robotPosition = robot.getPosition();
        assertEquals(1, robotPosition.getRow());
        assertEquals(2, robotPosition.getColumn());
    }

    
}