package com.mehdi.board;

import com.mehdi.error.BoardException;
import com.mehdi.model.Direction;
import com.mehdi.model.Position;
import com.mehdi.model.Rotate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardImplTest {

    @Test
    void notAllowedToMoveToNegativePosition() {
        BoardImpl board = new BoardImpl(2, 2);

        assertFalse(board.isAllowed(Position.Builder.aPosition().facing(Direction.NORTH).column(-1).row(1).build()));
        assertFalse(board.isAllowed(Position.Builder.aPosition().facing(Direction.NORTH).column(2).row(-1).build()));
    }

    @Test
    void notAllowedToMoveToPassBorders() {
        BoardImpl board = new BoardImpl(2, 2);

        assertFalse(board.isAllowed(Position.Builder.aPosition().facing(Direction.NORTH).column(3).row(1).build()));
        assertFalse(board.isAllowed(Position.Builder.aPosition().facing(Direction.NORTH).column(2).row(3).build()));
    }

    @Test
    void allowedToMove() {
        BoardImpl board = new BoardImpl(2, 2);

        assertTrue(board.isAllowed(Position.Builder.aPosition().facing(Direction.NORTH).column(0).row(0).build()));
    }

    @Test
    void notAllowedToMoveToAnOccupiedRoom() {
        BoardImpl board = new BoardImpl(2, 2);
        Position position = Position.Builder.aPosition().row(1).column(1).facing(Direction.NORTH).build();
        board.addRobot(position);

        assertFalse(board.isAllowed(Position.Builder.aPosition().facing(Direction.EAST).row(1).column(1).build()));
    }

    @Test
    void whenNoActiveRobotMoveThrowsException() {
        BoardImpl board = new BoardImpl(2, 2);

        assertThrows(BoardException.class, board::moveActiveRobot);
    }

    @Test
    void whenNoActiveRobotRotateThrowsException() {
        BoardImpl board = new BoardImpl(2, 2);

        assertThrows(BoardException.class, () -> board.rotateActiveRobot(Rotate.LEFT));
    }

    @Test
    void whenAddARobotToOccupiedRoomThrowsException() {
        BoardImpl board = new BoardImpl(2, 2);
        Position position = Position.Builder.aPosition().row(1).column(1).facing(Direction.NORTH).build();
        board.addRobot(position);

        assertThrows(BoardException.class, () -> board.addRobot(position));
    }
}