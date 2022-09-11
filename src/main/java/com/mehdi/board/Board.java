package com.mehdi.board;

import com.mehdi.model.Position;
import com.mehdi.model.Rotate;

public interface Board extends MoveValidator {

    void addRobot(Position position);

    void report();

    boolean moveActiveRobot();

    void rotateActiveRobot(Rotate direction);

    void changeActiveRobot(int index);
}
