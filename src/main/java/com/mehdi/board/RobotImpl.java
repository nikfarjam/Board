package com.mehdi.board;

import com.mehdi.model.Direction;
import com.mehdi.model.Position;
import com.mehdi.model.PositionDetails;

import java.util.List;

public class RobotImpl implements Robot {

    private Position position;

    private List<Direction> directions;

    public RobotImpl(Position position) {
        this.position = position;
        this.directions = List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
    }

    @Override
    public PositionDetails getPosition() {
        return this.position.getValue();
    }

    @Override
    public void rotateLeft() {
        int facingIndex = this.directions.indexOf(getPosition().getFacing());
        int newFacingIndex = (facingIndex + 1) == this.directions.size() ? 0 : facingIndex + 1;
        this.position.setFacing(this.directions.get(newFacingIndex));
    }

    @Override
    public void rotateRight() {
        int facingIndex = this.directions.indexOf(getPosition().getFacing());
        int newFacingIndex = (facingIndex - 1) < 0 ? this.directions.size() - 1 : facingIndex - 1;
        this.position.setFacing(this.directions.get(newFacingIndex));
    }

    @Override
    public boolean move() {
        return false;
    }
}
