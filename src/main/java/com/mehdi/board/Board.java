package com.mehdi.board;

import com.mehdi.model.Position;
import com.mehdi.model.Rotate;
import com.mehdi.report.Reporter;

public interface Board extends MoveValidator {

    void addRobot(Position position);

    void report();

    boolean moveActiveRobot();

    void rotateActiveRobot(Rotate direction);

    void changeActiveRobot(int index);

    void addReporter(Reporter reporter);
}
