package com.mehdi.command;

import com.mehdi.board.Board;

public class Active implements Command {

    private final Board board;

    private final int index;

    public Active(Board board, int index) {
        this.board = board;
        this.index = index;
    }

    @Override
    public void execute() {
        this.board.changeActiveRobot(index);
    }
}
