package catan;

/**
 * Executes one full AI turn for a player using rule-based strategies.
 * The player keeps applying the best available strategy until no
 * beneficial actions remain.
 *
 * @author Marva Hassan
 */
public class AITurnSimulator {

    private final Player player;
    private final Board board;
    private final Randomizer randomizer;
    private final Bank bank;
    private final PlacementValidator placementValidator;

    /**
     * Constructor initializes required game components.
     */
    public AITurnSimulator(Player player, Board board,
                           Randomizer randomizer, Bank bank,
                           PlacementValidator placementValidator) {

        this.player = player;
        this.board = board;
        this.randomizer = randomizer;
        this.bank = bank;
        this.placementValidator = placementValidator;
    }

    /**
     * Executes one full AI turn for the player.
     * Keeps applying the best strategy until none remain.
     */
    public void executeTurn() {

        StrategyChooser chooser = new StrategyChooser(randomizer);

        System.out.println("---- TESTING STRATEGY CHOOSER FOR PLAYER "
                + player.getPlayerID() + " ----");

        boolean actionTaken = false;

        while (true) {

            StrategyEvaluator bestStrategy =
                    chooser.chooseBestStrategy(player, board, bank, placementValidator);

            System.out.println("Best strategy value: " + chooser.getBestValue());

            if (bestStrategy == null) {
                if (!actionTaken) {
                    System.out.println("Player " + player.getPlayerID()
                            + " chose to take no action this turn.");
                }

                System.out.println("No more valid strategies for "
                        + player.getPlayerID());
                break;
            }

            bestStrategy.executeStrategy(player, board, randomizer, bank, placementValidator);
            actionTaken = true;
        }
    }
}