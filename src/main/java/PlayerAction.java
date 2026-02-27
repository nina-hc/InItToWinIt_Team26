package main.java;

import java.util.ArrayList;
import java.util.List;

/**
 * playerAction handles one full simulated turn for a player tries to build as
 * much as possible in a single turn
 * 
 * @author Marva Hassan
 */
public class PlayerAction {

	private final Player player;
	private final Board board;
	private final Randomizer randomizer;
	private final Bank bank;
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
	 * execute one full turn for the player attempts to build as much as possible
	 * each turn
	 */
	public void executeTurn() {
		boolean canBuild = true;

		while (canBuild) {
			List<Build> possibleActions = chooseActions();

			// nothing can be built
			if (possibleActions.isEmpty()) {
				canBuild = false;
				break;
			}

			// prioritize builds city > settlement > road
			Build selected = null;
			for (Build b : possibleActions) { // pick city first
				if (b instanceof BuildCity) {
					selected = b;
					break;
				}
			}
			if (selected == null) {
				for (Build b : possibleActions) { // pick settlement
					if (b instanceof BuildSettlement) {
						selected = b;
						break;
					}
				}
			}
			if (selected == null) {
				for (Build b : possibleActions) { // pick road last
					if (b instanceof BuildRoad) {
						selected = b;
						break;
					}
				}
			}

			// execute selected build
			if (selected != null) {
				boolean built = selected.execute(); // attempt to build
				if (!built) {
					// could not place the building
					canBuild = false;
				}
			} else {
				canBuild = false;
			}
		}
	}

	/**
	 * determine builds player can afford and place
	 * 
	 * @return list of build actions
	 */
	private List<Build> chooseActions() {
		List<Build> actions = new ArrayList<>();
		ResourceHand hand = player.getResourceHand();

		// city only if player has settlements to upgrade
		if (hand.canBuyCity() && player.getPlayerCitiesLeft() > 0 && player.getPlayerSettlements().size() > 0) {
			actions.add(new BuildCity(player, board, randomizer));
		}

		// settlement if affordable
		if (hand.canBuySettlement() && player.getPlayerSettlementsLeft() > 0) {
			actions.add(new BuildSettlement(player, board, randomizer));
		}

		// road if affordable
		if (hand.canBuyRoad() && player.getPlayerRoadsLeft() > 0) {
			actions.add(new BuildRoad(player, board, randomizer,bank,placementValidator));
		}

		return actions;
	}
}
