package com.mehdi.model;

/**
 * A simple immutable POJO to keep position of a Robot
 * It is immutable to be thread safe and reduce chance of side effect
 */
public class Position implements PositionDetails {
    private final int row;
    private final int column;
    private Direction facing;

    public Position(int row, int column, Direction facing) {
        this.row = row;
        this.column = column;
        this.facing = facing;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public Direction getFacing() {
        return facing;
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (row != position.row) return false;
        if (column != position.column) return false;
        return facing == position.facing;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        result = 31 * result + (facing != null ? facing.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Position{" +
                "column=" + column +
                ", row=" + row +
                ", facing=" + facing +
                '}';
    }

    public PositionDetails getValue() {
        return Builder.aPosition()
                .row(this.row)
                .column(this.column)
                .facing(this.facing)
                .build();
    }

    public static final class Builder {
        private int row;
        private int column;
        private Direction facing;

        private Builder() {
        }

        public static Builder aPosition() {
            return new Builder();
        }

        public Builder row(int row) {
            this.row = row;
            return this;
        }

        public Builder column(int column) {
            this.column = column;
            return this;
        }

        public Builder facing(Direction facing) {
            this.facing = facing;
            return this;
        }

        public Position build() {
            return new Position(row, column, facing);
        }
    }
}
