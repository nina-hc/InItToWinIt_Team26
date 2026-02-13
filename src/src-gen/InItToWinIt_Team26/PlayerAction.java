package InItToWinIt_Team26;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles AI decisions for a player in one full turn.
 */
public class PlayerAction {

    private Player player;
    private Board board;
    private Randomizer randomizer;

    public PlayerAction(Player player, Board board) {
        this.player = player;
        this.board = board;
    }

    /**
     * Executes one full AI turn, building as much as possible.
     */
    public void executeTurn() {

        boolean canBuild = true;

        while (canBuild) {
            List<Build> possibleActions = chooseActions();

            if (possibleActions.isEmpty()) {
                // Nothing affordable or placeable left
                canBuild = false;
                break;
            }

            // Prioritize builds: City > Settlement > Road
            Build selected = null;
            for (Build b : possibleActions) {
                if (b instanceof BuildCity) {
                    selected = b;
                    break;
                }
            }
            if (selected == null) {
                for (Build b : possibleActions) {
                    if (b instanceof BuildSettlement) {
                        selected = b;
                        break;
                    }
                }
            }
            if (selected == null) {
                for (Build b : possibleActions) {
                    if (b instanceof BuildRoad) {
                        selected = b;
                        break;
                    }
                }
            }

            if (selected != null) {
                boolean built = selected.execute();
                if (!built) {
                    //could not place the building (eg no valid location)
                    canBuild = false;
                }
            } else {
                //no valid action left
                canBuild = false;
            }
        }
    }

    /**
     * Determines what the player can afford and place on the board.
     * Returns a list of Build actions.
     */
    private List<Build> chooseActions() {
        List<Build> actions = new ArrayList<>();
        ResourceHand hand = player.getResourceHand();

        // Check build limits and resource availability
        if (hand.canBuyCity() && player.getPlayerCitiesLeft() > 0 && player.getPlayerSettlements().size() > 0) {
            actions.add(new BuildCity(player, board, randomizer));
        }
        if (hand.canBuySettlement() && player.getPlayerSettlementsLeft() > 0) {
            actions.add(new BuildSettlement(player, board, randomizer));
        }
        if (hand.canBuyRoad() && player.getPlayerRoadsLeft() > 0) {
            actions.add(new BuildRoad(player, board, randomizer));
        }

        return actions;
    }
}
