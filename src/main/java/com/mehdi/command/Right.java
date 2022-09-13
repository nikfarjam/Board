package com.mehdi.command;

import com.mehdi.board.Board;
import com.mehdi.model.Rotate;

public class Right implements Command {

    private final Board board;

    public Right(Board board) {
        this.board = board;
    }

    @Override
    public void execute() {
        this.board.rotateActiveRobot(Rotate.RIGHT);
    }
}
