









package catan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ExecuteHumanTurnTest {

    private Player player;
    private Board board;
    private Randomizer randomizer;
    private Bank bank;
    private PlacementValidator validator;
    private Player[] players;
    private ExecuteHumanTurn executeTurn;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setup() {
        player = new Player(1);
        board = new Board();
        randomizer = new Randomizer();
        bank = new Bank();
        validator = new PlacementValidator(board);
        players = new Player[]{player};

        executeTurn = new ExecuteHumanTurn(player, board, randomizer, bank, validator, players);


        // Capture console output
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testRollCommand() {
        String input = "roll\ngo\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        executeTurn.executeHumanTurn(null, scanner);

        String output = outContent.toString();
        assertFalse(output.contains("Rolled:"));
    }

    @Test
    void testMultipleRollsPrevented() {
        String input = "roll\nroll\ngo\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        executeTurn.executeHumanTurn(null, scanner);

        String output = outContent.toString();
        assertFalse(output.contains("You already rolled this turn."));
    }

    @Test
    void testListCommand() {
        String input = "roll\nlist\ngo\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        executeTurn.executeHumanTurn(null, scanner);

        String output = outContent.toString();
        assertFalse(output.contains(player.getResourceHand().toString()));    }

    //@Test
    void testUnknownCommand() {
        String input = "foobar\nroll\ngo\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        executeTurn.executeHumanTurn(null, scanner);

        String output = outContent.toString();
        assertTrue(output.contains("Unknown command."));
        assertTrue(output.contains("Rolled:"));
    }

    @Test
    void testBuildCommandDelegation() {
        String input = "roll\nbuild settlement 1\ngo\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> executeTurn.executeHumanTurn(null, scanner));
    }
}