package com.mehdi.report;

import com.mehdi.model.PositionDetails;

import java.util.List;

public interface Reporter {

    void report(List<PositionDetails> positions);

}
