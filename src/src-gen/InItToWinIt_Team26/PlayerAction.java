package InItToWinIt_Team26;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerAction {

    private Player player;
    private Board board;
    private Randomizer randomizer;
    private Random random;

    public PlayerAction(Player player, Board board, Randomizer randomizer) {
        this.player = player;
        this.board = board;
        this.randomizer = randomizer;
        this.random = new Random();
    }

    /*
     * Executes one full AI turn decision
     */
    public void executeTurn() {

        boolean turnOver = false;

        while (!turnOver) {

            int totalCards = player.getResourceHand().totalPlayerCard();

            boolean mustBuild = false;

            if (totalCards > 7) {
                mustBuild = true;
            }

            List<Build> possibleActions = chooseActions();

            if (possibleActions.isEmpty()) {
                // If player must build but cannot, turn still ends
                return;
            }

            // Randomly select action
            int choice = random.nextInt(possibleActions.size());
            Build selected = possibleActions.get(choice);

            boolean built = selected.execute(player, board);

            if (!built) {
                return;
            }

            // If player was NOT forced to build, end after one action
            if (!mustBuild) {
                turnOver = true;
            }

        }
    }


    /*
     * Determine what player can afford
     */
    private List<Build> chooseActions() {

        List<Build> actions = new ArrayList<>();

        ResourceHand hand = player.getResourceHand();

        if (hand.canBuyRoad()) {
            actions.add(new BuildRoad());
        }

        if (hand.canBuySettlement()) {
            actions.add(new BuildSettlement());
        }

        if (hand.canBuyCity()) {
            actions.add(new BuildCity());
        }

        return actions;
    }
}
