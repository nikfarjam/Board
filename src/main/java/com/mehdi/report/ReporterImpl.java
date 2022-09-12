package com.mehdi.report;

import com.mehdi.model.PositionDetails;

import java.util.List;

public class ReporterImpl implements Reporter {

    private static final String OUTPUT = "OUTPUT";

    @Override
    public void report(List<PositionDetails> positions) {
        StringBuffer result = new StringBuffer(OUTPUT);
        if (positions != null && positions.size() == 1) {
            PositionDetails position = positions.get(0);
            result.append(" ")
                    .append(position.getRow())
                    .append(",")
                    .append(position.getColumn())
                    .append(",")
                    .append(position.getFacing());
        } else if (positions != null) {
            int index = 1;
            for (PositionDetails position : positions) {
                result.append("\nROBOT").append(index)
                        .append(" ")
                        .append(position.getRow())
                        .append(",")
                        .append(position.getColumn())
                        .append(",")
                        .append(position.getFacing());
                index+=1;
            }
        }
        System.out.println(result);
    }
}
