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
        assertEquals("OUTPUT", reporter.generateReport(List.of()));
    }

    @Test
    void nullRobotReport() {
        assertEquals("OUTPUT", reporter.generateReport(null));
    }

    @Test
    void singleRobotReport() {
        Position position = Position.Builder.aPosition().row(2).column(3).facing(Direction.SOUTH).build();

        String report = reporter.generateReport(List.of(position));

        assertEquals("OUTPUT 2,3,SOUTH", report);
    }

    @Test
    void multiRobotReport() {
        List<PositionDetails> positions = List.of(
                Position.Builder.aPosition().row(2).column(3).facing(Direction.SOUTH).build(),
                Position.Builder.aPosition().row(0).column(1).facing(Direction.WEST).build(),
                Position.Builder.aPosition().row(4).column(1).facing(Direction.EAST).build()
        );

        String report = reporter.generateReport(positions);

        String expected = """
                OUTPUT
                ROBOT1 2,3,SOUTH
                ROBOT2 0,1,WEST
                ROBOT3 4,1,EAST""";
        assertEquals(expected, report);
    }

}