package com.mehdi.command;

import com.mehdi.board.Board;
import com.mehdi.model.Rotate;

public class Left implements Command {

    private Board board;

    public Left(Board board) {
        this.board = board;
    }

    @Override
    public void execute() {
        this.board.rotateActiveRobot(Rotate.LEFT);
    }
}
