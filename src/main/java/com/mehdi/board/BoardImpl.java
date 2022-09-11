package com.mehdi.board;

import com.mehdi.error.BoardException;
import com.mehdi.model.Position;
import com.mehdi.model.PositionDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardImpl implements Board {

    private int numberOfColumns;

    private int numberOfRows;

    private List<Robot> robots;

    private int active;

    public BoardImpl(int numberOfColumns, int numberOfRows) {
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        this.robots = new ArrayList<>();
        this.active = -1;
    }

    @Override
    public boolean isAllowed(PositionDetails position) {
        if (position.getRow() < 0 || position.getRow() > numberOfRows - 1) {
            return false;
        }
        if (position.getColumn() < 0 || position.getColumn() > numberOfColumns - 1) {
            return false;
        }
        Optional<Robot> existInPosition = this.robots.stream()
                .filter(r -> existInRoom(position, r))
                .findFirst();
        return existInPosition.isEmpty();
    }

    private boolean existInRoom(PositionDetails position, Robot r) {
        return r.getPosition().getColumn() == position.getColumn() && r.getPosition().getRow() == position.getRow();
    }

    public void addRobot(Position position) {
        if (!isAllowed(position)) {
            throw new BoardException("Invalid position");
        }
        this.robots.add(new RobotImpl(position, this));
        this.active = robots.size() - 1;
    }

    public void report() {

    }

    public boolean moveActiveRobot() {
        if (active < 0 || active > robots.size() + 1) {
            throw new BoardException("There's no active robot");
        }
        return this.robots.get(active).move();
    }

    public void rotateActiveRobot(Rotate direction) {
        if (active < 0 || active > robots.size() + 1) {
            throw new BoardException("There's no active robot");
        }
        if (Rotate.LEFT == direction) {
            this.robots.get(active).rotateLeft();
        } else {
            this.robots.get(active).rotateRight();
        }
    }
}
