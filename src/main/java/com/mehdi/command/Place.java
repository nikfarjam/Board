package com.mehdi.command;

import com.mehdi.board.Board;
import com.mehdi.model.Position;

public class Place implements Command {

    private Board board;

    private Position position;

    public Place(Board board, Position position) {
        this.board = board;
        this.position = position;
    }

    @Override
    public void execute() {
        this.board.addRobot(position);
    }
}
