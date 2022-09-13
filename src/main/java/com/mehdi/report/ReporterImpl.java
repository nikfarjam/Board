package com.mehdi.report;

import com.mehdi.model.PositionDetails;

import java.util.List;

public class ReporterImpl implements Reporter {

    private static final String OUTPUT_PREFIX = "";

    @Override
    public void report(List<PositionDetails> positions, int activeRobot) {
        showReport(generateReport(positions, activeRobot));
    }

    public void showReport(String report) {
        System.out.println(report);
    }

    public String generateReport(List<PositionDetails> positions, int activeRobot) {
        StringBuffer result = new StringBuffer(OUTPUT_PREFIX);
        if (positions == null) {
            return result.toString().strip();
        }
        if (positions.size() == 1) {
            PositionDetails position = positions.get(0);
            result.append(position.getColumn())
                    .append(",")
                    .append(position.getRow())
                    .append(",")
                    .append(position.getFacing());
        } else if (positions.size() > 1) {
            int index = 1;
            result.append(positions.size()).append(" ROBOTS").append("\n");
            result.append(activeRobot).append(" IS ACTIVE");
            for (PositionDetails position : positions) {
                result.append("\nROBOT").append(index)
                        .append(" ")
                        .append(position.getColumn())
                        .append(",")
                        .append(position.getRow())
                        .append(",")
                        .append(position.getFacing());
                index += 1;
            }
        }
        return result.toString().strip();
    }
}
