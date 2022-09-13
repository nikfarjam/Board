package com.mehdi.command;

import com.mehdi.board.BoardImpl;
import com.mehdi.model.Rotate;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RightTest {

    @Test
    void rotateLeft() {
        BoardImpl board = mock(BoardImpl.class);
        Right command = new Right(board);

        command.execute();

        verify(board, times(1)).rotateActiveRobot(Rotate.RIGHT);
    }

}