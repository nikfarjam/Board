package com.mehdi.board;

import com.mehdi.model.PositionDetails;

public interface MoveValidator {

    boolean isAllowed(PositionDetails position);
}
