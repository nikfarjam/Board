package com.mehdi.command;

import com.mehdi.board.Board;

public class Move implements Command {

    private Board board;

    public Move(Board board) {
        this.board = board;
    }

    @Override
    public void execute() {
        this.board.moveActiveRobot();
    }
}
