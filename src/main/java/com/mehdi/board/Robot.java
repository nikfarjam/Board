package com.mehdi.board;

import com.mehdi.model.PositionDetails;

public interface Robot {

    PositionDetails getPosition();

    void rotateLeft();

    void rotateRight();

    boolean move();
}
