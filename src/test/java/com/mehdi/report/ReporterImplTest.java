package com.mehdi.report;

import com.mehdi.model.Direction;
import com.mehdi.model.Position;
import com.mehdi.model.PositionDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReporterImplTest {

    ReporterImpl reporter;

    @BeforeEach
    void setUp() {
        reporter = new ReporterImpl();
    }

    @Test
    void emptyRobotReport() {
        assertEquals("", reporter.generateReport(List.of(), 0));
    }

    @Test
    void nullRobotReport() {
        assertEquals("", reporter.generateReport(null, 0));
    }

    @Test
    void singleRobotReport() {
        Position position = Position.Builder.aPosition().row(2).column(3).facing(Direction.SOUTH).build();

        String report = reporter.generateReport(List.of(position), 0);

        assertEquals("3,2,SOUTH", report);
    }

    @Test
    void multiRobotReport() {
        List<PositionDetails> positions = List.of(
                Position.Builder.aPosition().row(2).column(3).facing(Direction.SOUTH).build(),
                Position.Builder.aPosition().row(0).column(1).facing(Direction.WEST).build(),
                Position.Builder.aPosition().row(4).column(1).facing(Direction.EAST).build()
        );

        String report = reporter.generateReport(positions, 1);

        String expected = """
                3 ROBOTS
                1 IS ACTIVE
                ROBOT1 3,2,SOUTH
                ROBOT2 1,0,WEST
                ROBOT3 1,4,EAST""";
        assertEquals(expected, report);
    }

}