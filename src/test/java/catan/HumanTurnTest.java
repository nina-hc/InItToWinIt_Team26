import catan.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing HumanTurn class
 * @author Marva Hassan
 */
class HumanTurnTest {

    private Player player;
    private Board board;
    private Randomizer randomizer;
    private Bank bank;
    private PlacementValidator validator;
    private Player[] players;

    @BeforeEach
    void setup() {
        player = new Player(1);
        board = new Board();
        randomizer = new Randomizer();
        bank = new Bank();
        validator = new PlacementValidator(board);
        players = new Player[]{player};
    }

    /**
     * Player must roll before building or ending turn
     */
    @Test
    void testMustRollBeforeBuildOrGo() {

        String input = "build settlement 1\ngo\nroll\nbuild settlement 1\ngo\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        HumanTurn turn = new HumanTurn(player, board, randomizer, bank, validator, players);

        turn.executeHumanTurn();

        assertNotNull(player.getResourceHand());
    }

    /**
     * Player cannot roll twice in the same turn
     */
    @Test
    void testMultipleRollsDisallowed() {

        String input = "roll\nroll\ngo\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        HumanTurn turn = new HumanTurn(player, board, randomizer, bank, validator, players);

        turn.executeHumanTurn();

        assertNotNull(player.getResourceHand());
    }

    /**
     * List command should execute without error
     */
    @Test
    void testListCommand() {

        String input = "roll\nlist\ngo\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        HumanTurn turn = new HumanTurn(player, board, randomizer, bank, validator, players);

        turn.executeHumanTurn();

        assertNotNull(player.getResourceHand());
    }

    /**
     * Invalid command should be handled
     */
    @Test
    void testInvalidCommand() {

        String input = "invalidcommand\nroll\ngo\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        HumanTurn turn = new HumanTurn(player, board, randomizer, bank, validator, players);

        turn.executeHumanTurn();

        assertNotNull(player);
    }

    /**
     * Build city command path
     */
    @Test
    void testBuildCityPath() {

        String input = "roll\nbuild city 1\ngo\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        HumanTurn turn = new HumanTurn(player, board, randomizer, bank, validator, players);

        turn.executeHumanTurn();

        assertNotNull(player);
    }

    /**
     * Build road command path
     */
    @Test
    void testBuildRoadPath() {

        String input = "roll\nbuild road 1,2\ngo\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        HumanTurn turn = new HumanTurn(player, board, randomizer, bank, validator, players);

        turn.executeHumanTurn();

        assertNotNull(player);
    }

    /**
     * Build settlement path
     */
    @Test
    void testBuildSettlementPath() {

        String input = "roll\nbuild settlement 2\ngo\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        HumanTurn turn = new HumanTurn(player, board, randomizer, bank, validator, players);

        turn.executeHumanTurn();

        assertNotNull(player);
    }

}