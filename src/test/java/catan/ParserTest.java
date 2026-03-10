package catan;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * testing Parser Class
 * @author Marva Hassan
 */

class ParserTest {

    private final Parser parser = new Parser();

    //BASIC COMMAND TESTS

    @Test
    void parseRollCommand() {
        Command cmd = parser.parse("roll");

        assertTrue(cmd.valid);
        assertEquals("Roll", cmd.type);
    }

    @Test
    void parseGoCommand() {
        Command cmd = parser.parse("go");

        assertTrue(cmd.valid);
        assertEquals("Go", cmd.type);
    }

    @Test
    void parseListCommand() {
        Command cmd = parser.parse("list");

        assertTrue(cmd.valid);
        assertEquals("List", cmd.type);
    }



    //BUILD SETTLEMENT

    //Parse build settlement with valid nodeId
    @Test
    void parseBuildSettlementValid() {
        Command cmd = parser.parse("build settlement 5");

        assertTrue(cmd.valid);
        assertEquals("Build", cmd.type);
        assertEquals("settlement", cmd.buildType);
        assertEquals(5, cmd.nodeId);
    }

    //Boundary test: build settlement with nodeId = 0
    @Test
    void parseBuildSettlementBoundaryZero() {
        Command cmd = parser.parse("build settlement 0");

        assertTrue(cmd.valid);
        assertEquals(0, cmd.nodeId);
    }


    //BUILD CITY

    //Parse build city with large nodeId
    @Test
    void parseBuildCityLargeNode() {
        Command cmd = parser.parse("build city 9999");

        assertTrue(cmd.valid);
        assertEquals("city", cmd.buildType);
        assertEquals(9999, cmd.nodeId);
    }



    //BUILD ROAD


    //Parse build road with spaces around comma
    @Test
    void parseBuildRoadWithSpaces() {
        Command cmd = parser.parse("build road 3 , 8");

        assertTrue(cmd.valid);
        assertEquals("road", cmd.buildType);
        assertEquals(3, cmd.fromNodeId);
        assertEquals(8, cmd.toNodeId);
    }

    //Parse build road without spaces around comma
    @Test
    void parseBuildRoadNoSpaces() {
        Command cmd = parser.parse("build road 2,4");

        assertTrue(cmd.valid);
        assertEquals(2, cmd.fromNodeId);
        assertEquals(4, cmd.toNodeId);
    }




    //INVALID COMMANDS


    //Invalid command: unknown keyword
    @Test
    void parseInvalidUnknownCommand() {
        Command cmd = parser.parse("attack 5");

        assertFalse(cmd.valid);
    }


    //Invalid command: build settlement missing nodeId
    @Test
    void parseInvalidMissingArgument() {
        Command cmd = parser.parse("build settlement");

        assertFalse(cmd.valid);
    }


    //Invalid command: null input
    @Test
    void parseNullInput() {
        Command cmd = parser.parse(null);

        assertFalse(cmd.valid);
    }


    //Invalid command: negative nodeId
    @Test
    void parseNegativeNodeId() {
        Command cmd = parser.parse("build city -3");

        assertFalse(cmd.valid);
    }

    // Boundary: nodeId = 0 (valid)
    @Test
    void settlementNodeIdZeroValid() {
        Command cmd = parser.parse("build settlement 0");
        assertTrue(cmd.valid);
    }

    // Boundary: nodeId = 53 (valid)
    @Test
    void settlementNodeIdMaxValid() {
        Command cmd = parser.parse("build settlement 53");
        assertTrue(cmd.valid);
    }

    // Boundary: nodeId = -1 (invalid)
    @Test
    void settlementNodeIdNegativeInvalid() {
        Command cmd = parser.parse("build settlement -1");
        assertFalse(cmd.valid);
    }

    // Boundary: nodeId = 54 (invalid)
    @Test
    void settlementNodeIdAboveMaxInvalid() {
        Command cmd = parser.parse("build settlement 54");
        assertFalse(cmd.valid);
    }

    // Road boundary test
    @Test
    void roadNodeOutOfRangeInvalid() {
        Command cmd = parser.parse("build road 3, 60");
        assertFalse(cmd.valid);
    }
}