package com.mehdi.board;

import com.mehdi.error.BoardException;
import com.mehdi.model.Direction;
import com.mehdi.model.Position;
import com.mehdi.model.PositionDetails;

import java.util.List;

public class RobotImpl implements Robot {

    private Position position;

    private MoveValidator validator;

    private List<Direction> directions;

    public RobotImpl(Position position, MoveValidator validator) {
        this.position = position;
        this.validator = validator;
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
        Position newPosition = switch (this.position.getFacing()) {
            case NORTH -> Position.Builder.aPosition()
                    .row(this.position.getRow() + 1)
                    .column(this.position.getColumn())
                    .facing(this.position.getFacing())
                    .build();
            case EAST -> Position.Builder.aPosition()
                    .row(this.position.getRow())
                    .column(this.position.getColumn() + 1)
                    .facing(this.position.getFacing())
                    .build();
            case SOUTH -> Position.Builder.aPosition()
                    .row(this.position.getRow() - 1)
                    .column(this.position.getColumn())
                    .facing(this.position.getFacing())
                    .build();
            case WEST -> Position.Builder.aPosition()
                    .row(this.position.getRow())
                    .column(this.position.getColumn() - 1)
                    .facing(this.position.getFacing())
                    .build();
            default -> throw new BoardException("Invalid position");
        };
        if (validator.isAllowed(newPosition)) {
            this.position = newPosition;
            return true;
        }
        return false;
    }
}
