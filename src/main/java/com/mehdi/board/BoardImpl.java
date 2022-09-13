package com.mehdi.board;

import com.mehdi.error.BoardException;
import com.mehdi.model.Position;
import com.mehdi.model.PositionDetails;
import com.mehdi.model.Rotate;
import com.mehdi.report.Reporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardImpl implements Board {

    private final int numberOfColumns;

    private final int numberOfRows;

    private final List<Robot> robots;

    private final List<Reporter> reporters;

    private int active;

    public BoardImpl(int numberOfColumns, int numberOfRows) {
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        this.robots = new ArrayList<>();
        this.active = -1;
        this.reporters = new ArrayList<>();
    }

    @Override
    public void addReporter(Reporter reporter) {
        this.reporters.add(reporter);
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

    @Override
    public void addRobot(Position position) {
        if (!isAllowed(position)) {
            throw new BoardException("Invalid position");
        }
        this.robots.add(new RobotImpl(position, this));
        this.active = robots.size() - 1;
    }

    @Override
    public void report() {
        List<PositionDetails> positions = this.robots.stream()
                .map(Robot::getPosition)
                .toList();
        for (Reporter reporter : reporters) {
            reporter.report(positions, this.active + 1);
        }
    }

    @Override
    public void moveActiveRobot() {
        validateActiveRobotIndex(this.active);
        this.robots.get(active).move();
    }

    @Override
    public void rotateActiveRobot(Rotate direction) {
        validateActiveRobotIndex(this.active);
        if (Rotate.LEFT == direction) {
            this.robots.get(active).rotateLeft();
        } else {
            this.robots.get(active).rotateRight();
        }
    }

    @Override
    public void changeActiveRobot(int index) {
        validateActiveRobotIndex(index);
        this.active = index - 1;
    }

    private void validateActiveRobotIndex(int index) {
        if (index < 0 || index > robots.size() + 1) {
            throw new BoardException("There's no active robot");
        }
    }
}
