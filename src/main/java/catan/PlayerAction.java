package catan;

import java.util.ArrayList;
import java.util.List;

/**
 * PlayerAction handles one full simulated turn for a player tries to build as
 * much as possible in a single turn
 * 
 * @author Marva Hassan
 * @version February 2026, McMaster University
 */
public class PlayerAction {

    /**
     * Player that is taking an action
     */
	private final Player player;

    /**
     * Game board
     */
    private final Board board;

    /**
     * Randomizer used to generate random moves
     */
    private final Randomizer randomizer;

    /**
     * Bank that's in charge of managing resources
     */
    private final Bank bank;

    /**
     * In charge of determining if placements are valid
     */
	private final PlacementValidator placementValidator;

	/**
	 * constructor initializes the player, board, and randomizer
	 *
	 * @param player             player taking the turn
	 * @param board              catan board
	 * @param randomizer         randomizer for dice or random choices
	 * @param bank the game bank
	 * @param placementValidator placement validatory rules
	 */
	public PlayerAction(Player player, Board board, Randomizer randomizer, Bank bank, PlacementValidator placementValidator) {
		this.player = player;
		this.board = board;
		this.randomizer = randomizer;
		this.bank = bank;
		this.placementValidator = placementValidator;
	}

	/**
	 * Execute one full turn for the player attempts to build as much as possible
	 * each turn
	 */
	public void executeTurn() {
        //check 7 card rule
//        ResourceHand hand = player.getResourceHand();
//        if (hand.totalPlayerCard() >= 7) {
//            int discardCount = hand.totalPlayerCard() / 2;
//            hand.discardRandom(discardCount);
//            System.out.println("Player " + player.getPlayerID() + " discarded " + discardCount + " cards due to 7-card rule.");
//        }


        boolean canBuild = true;

		while (canBuild) {
            List<Build> possibleActions = chooseActions();

            // Add "do nothing" as an option
            possibleActions.add(null);

            // nothing can be built or player chooses to skip
            if (possibleActions.isEmpty()) {
                canBuild = false;
                break;
            }

            // randomly select an action
            int index = randomizer.randomSelection(0, possibleActions.size() - 1);
            Build selected = possibleActions.get(index);

            if (selected != null) {
                boolean built = selected.execute(); // attempt to build
                if (!built) {
                    canBuild = false;
                }
            } else {
                // Player chose to do nothing
                canBuild = false;
                System.out.println("Player " + player.getPlayerID() + " chose to take no action this turn.");

            }
        }


    }

	/**
	 * Determine builds player can afford and place
	 * 
	 * @return list of build actions
	 */
	private List<Build> chooseActions() {
		List<Build> actions = new ArrayList<>();
		ResourceHand hand = player.getResourceHand();

		// city only if player has settlements to upgrade
		if (hand.canBuyCity() && player.getPlayerCitiesLeft() > 0 && player.getPlayerSettlements().size() > 0) {
			actions.add(new BuildCity(player, board, randomizer, bank, placementValidator));
		}

		// settlement if affordable
		if (hand.canBuySettlement() && player.getPlayerSettlementsLeft() > 0) {
			actions.add(new BuildSettlement(player, board, randomizer, bank, placementValidator));
		}

		// road if affordable
		if (hand.canBuyRoad() && player.getPlayerRoadsLeft() > 0) {
			actions.add(new BuildRoad(player, board, randomizer,bank,placementValidator));
		}

		return actions;
	}
}
