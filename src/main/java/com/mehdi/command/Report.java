package com.mehdi.command;

import com.mehdi.board.Board;

public class Report implements Command {

    private Board board;

    public Report(Board board) {
        this.board = board;
    }

    @Override
    public void execute() {
        this.board.report();
    }
}
