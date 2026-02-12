package InItToWinIt_Team26;

/**
 * The following is the Bank class in Catan
 * The number of each resource card remaining in the bank can be found and updated here
 * This class is souly responsible for checking resource availability before distributing, and updating resource values when cards are deposited into the bank
 *
 * @author Synthia Rosenberger
 * @version February 2026, McMaster University
 */
public class Bank {

    //*************************************************************************************
    //the bank begins with 19 cards of each resource
    //the bank will be stored in an array with each index representing a resource:
    //---------------------------------------------------------------
    // ---> 0 = lumber, 1 = wool, 2 = grain, 3 = brick, 4 = ore <---
    //---------------------------------------------------------------
    //*************************************************************************************

    /**
     * An array to store the bank resources
     */
    private int[] bankResources;


    /**
     * Bank constructor, creates an array with 5 resources x 19 cards each
     */
    public Bank() {
        bankResources = new int[]{19, 19, 19, 19, 19};
    }


    /**
     * This method assigns a name to each resource so they can be returned by name and not just index number
     *
     * @param resourceType number representation of resource
     * @return name of resource
     */
    private String resourceName(int resourceType) {

        if (resourceType == 0) {
            return "Lumber";

        } else if (resourceType == 1) {
            return "Wool";

        } else if (resourceType == 2) {
            return "Grain";

        } else if (resourceType == 3) {
            return "Brick";

        } else if (resourceType == 4) {
            return "Ore";
        } else {
            return "ERROR: Resource type does not exist in this bank";
        }
    }


    /**
     * Checks if there are enough resources in the bank to be distributed
     *
     * @param resourceType number representation of resource
     * @param amountWithdrawal number of cards that are being taken from the bank
     * @return true if there are enough cards to be distributed, false if not
     */
    public boolean hasResources(int resourceType, int amountWithdrawal) {

        if (resourceType < 0 || resourceType >= bankResources.length) {     //requesting a resource type that does not exist
            System.out.println("ERROR: resource does not exist");
            return false;
        }

        int amountOfResource = bankResources[resourceType]; //check how many cards of THIS resource is available in bank

        if (amountOfResource <= 0) {    //the bank doesn't have any resourceType left
            return false;

        } else if (amountWithdrawal > amountOfResource) {   //cards in the bank but less than what's being withdrawn
            return true;    //allows distribution to happen

        } else {    //if amountWithdrawal <= amountOfResource
            return true;    //allows distribution to happen
        }
    }


    /**
     * Updates the amount of cards in bank after distributing
     *
     * @param resourceType number representation of resource
     * @param amountWithdrawal number of cards that are being taken away from the bank
     * @return the number of cards that can be distributed
     */
    public int transferToPlayer(int resourceType, int amountWithdrawal) {

        if (!hasResources(resourceType, amountWithdrawal)) {     //requesting a resource type that does not exist
            return 0;   //no resources given
        }

        int amountGiven = 0;
        int amountOfResource = bankResources[resourceType];     //check how many cards of THIS resource is available in bank

        //case 1: some cards available, not enough though
        if (amountWithdrawal > amountOfResource) {      //amount being asked for is more than available
            amountGiven = amountOfResource;             //what's available is given
            bankResources[resourceType] = 0;            //amount in bank is set to 0


        } else {    //case 2: full amount is available
            amountGiven = amountWithdrawal;                     //amount asked for is given
            bankResources[resourceType] -= amountWithdrawal;    //update available cards in bank
        }

        return amountGiven;

    }


    /**
     * This method increases the amount of resources in the bank after they're spent
     *
     * @param resourceType number representation of resource
     * @param amountDeposit how many cards are being sent back to the bank
     */
    public void resourceDeposit(int resourceType, int amountDeposit) {

        if (resourceType >= 0 &&  resourceType < bankResources.length) {    //check resource exists
            bankResources[resourceType] += amountDeposit;       //update number of cards available for given resource
        }
    }


    /**
     * Getter, useful when the amount of a resource needs to be known outside the Bank class
     *
     * @param resourceType number representation of resource
     * @return number of cards available for resource
     */
    public int getResourceAmount(int resourceType) {

        if (resourceType >= 0 &&  resourceType < bankResources.length) {    //check resource exists
            return bankResources[resourceType];
        } else {
            return -1;      //ERROR
        }
    }

}