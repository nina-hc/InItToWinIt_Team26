
import java.util.EnumMap;
import java.util.Map;

/**
 * The following is the Bank class in Catan The number of each resource card
 * remaining in the bank can be found and updated here This class is souly
 * responsible for checking resource availability before distributing, and
 * updating resource values when cards are deposited into the bank
 *
 * @author Synthia Rosenberger
 * @version February 2026, McMaster University
 */
public class Bank {

	// *************************************************************************************
	// ~ Welcome to the Bank! ~
	// the bank begins with 19 cards of each resource
	// the bank will be stored in an EnumMap to keep track of the banks inventory:
	// ---------------------------------------------------------------
	// ---> 0 = lumber, 1 = wool, 2 = grain, 3 = brick, 4 = ore <---
	// ---------------------------------------------------------------
	// *************************************************************************************

	/**
	 * Variable to store map Map to store the current resources available in the
	 * Bank
	 */
	private Map<ResourceType, Integer> bankResources;

	/**
	 * Bank constructor, constructs a Bank and stores 19 cards in each resource
	 */
	public Bank() {
		bankResources = new EnumMap<>(ResourceType.class); // create a map object

		// initialize each resource type with 19 cards:
		for (ResourceType type : ResourceType.values()) {
			bankResources.put(type, 19);
		}
	}

	/**
	 * Checks if there are enough resources in the bank to be distributed
	 *
	 * @param resourceType     number representation of resource
	 * @param amountWithdrawal number of cards that are being taken from the bank
	 * @return true if there are enough cards to be distributed, false if not
	 */
	public boolean hasResources(ResourceType resourceType, int amountWithdrawal) {

		if (!bankResources.containsKey(resourceType)) { // check if requested resource exists
			return false;
		}

		int amountOfResource = bankResources.get(resourceType); // check how many cards of THIS resource is available in
																// bank

		if (amountOfResource <= 0) { // the bank doesn't have any resourceType left
			return false;

		} else if (amountWithdrawal > amountOfResource) { // cards in the bank but less than what's being withdrawn
			return true; // allows distribution to happen

		} else { // if amountWithdrawal <= amountOfResource
			return true; // allows distribution to happen
		}
	}

	/**
	 * Updates the amount of cards in bank after distributing
	 *
	 * @param resourceType     number representation of resource
	 * @param amountWithdrawal number of cards that are being taken away from the
	 *                         bank
	 * @return the number of cards that can be distributed
	 */
	public int transferToPlayer(ResourceType resourceType, int amountWithdrawal) {

		if (!hasResources(resourceType, amountWithdrawal)) { // requesting a resource type that does not exist
			return 0; // no resources given
		}

		int amountGiven = 0;
		int amountOfResource = bankResources.get(resourceType); // check how many cards of THIS resource is available in
																// bank

		// case 1: some cards available, not enough though
		if (amountWithdrawal > amountOfResource) { // amount being asked for is more than available
			amountGiven = amountOfResource; // what's available is given
			bankResources.put(resourceType, 0); // amount in bank is set to 0

		} else { // case 2: full amount is available
			amountGiven = amountWithdrawal; // amount asked for is given
			bankResources.put(resourceType, amountOfResource - amountWithdrawal); // update available cards in bank
		}

		return amountGiven;

	}

	/**
	 * This method increases the amount of resources in the bank after they're spent
	 *
	 * @param resourceType  number representation of resource
	 * @param amountDeposit how many cards are being sent back to the bank
	 */
	public void resourceDeposit(ResourceType resourceType, int amountDeposit) {

		int amountOfResource = bankResources.get(resourceType);
		int amountGiven = amountOfResource + amountDeposit; // increase amount of the resource available in the bank
		bankResources.put(resourceType, amountGiven);

	}

	/**
	 * Getter, useful when the amount of a resource needs to be known outside the
	 * Bank class
	 *
	 * @param resourceType number representation of resource
	 * @return number of cards available for resource
	 */
	public int getResourceAmount(ResourceType resourceType) {

		return bankResources.get(resourceType);
	}

}