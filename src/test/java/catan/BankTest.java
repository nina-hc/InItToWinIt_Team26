/*
package catan;


import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    @Test
    void testHasResources() {
        //create
        Bank bank = new Bank();
        //test
        boolean hasResource = bank.hasResources(ResourceType.LUMBER, 3);
        //check
        assertTrue(hasResource);    //should return true cuz this resource exists
    }

    @Test
    void testHasResourcesEdgeTooMany() {
        //create
        Bank bank = new Bank();
        //test
        boolean hasResource = bank.hasResources(ResourceType.LUMBER, 38);
        //check
        assertFalse(hasResource);    //should return false
    }

    @Test
    void testHasResourcesEdgeTooLittle() {
        //create
        Bank bank = new Bank();
        //test
        boolean hasResource = bank.hasResources(ResourceType.LUMBER, -3);
        //check
        assertFalse(hasResource);    //should return false
    }


    //=======================================================
    @Test
    void testTransferToPlayer() {
        //create
        Bank bank = new Bank();
        //test
        int transferredToPlayer = bank.transferToPlayer(ResourceType.BRICK, 1);
        //check
        assertEquals(1, transferredToPlayer);
    }

    @Test
    void testTransferToPlayerEdgeNotEnoughResourceInBank() {
        //create
        Bank bank = new Bank();
        //test
        int transferredToPlayer = bank.transferToPlayer(ResourceType.BRICK, 22);
        //check
        assertEquals(19, transferredToPlayer);    //should only give what it has
    }

    @Test
    void testTransferToPlayerEdgeRequestNumberIsNegative() {
        //create
        Bank bank = new Bank();
        //test
        int transferredToPlayer = bank.transferToPlayer(ResourceType.BRICK, -5);
        //check
        assertEquals(0, transferredToPlayer);    //should only give what it has
    }


    //=======================================================
    @Test
    void testResourceDeposit() {
        //create
        Bank bank = new Bank();
        //test
        bank.transferToPlayer(ResourceType.WOOL, 7); //should be 12 in bank
        bank.resourceDeposit(ResourceType.WOOL, 2);  //now there should be 14 in bank
        //check
        assertEquals(14, bank.getResourceAmount(ResourceType.WOOL));

    }

    @Test
    void testResourceDepositEdgeTryingToDepositWhenBankHasMaxResource() {
        //create
        Bank bank = new Bank();
        //test
        bank.resourceDeposit(ResourceType.WOOL, 4);  //by default there should be 12 in the bank alr
        //check
        assertEquals(19, bank.getResourceAmount(ResourceType.WOOL));   //nothing should have went into the bank
    }

    @Test
    void testResourceDepositEdgeTryingToDepositNegativeNumbers() {
        //create
        Bank bank = new Bank();
        //test
        bank.transferToPlayer(ResourceType.WOOL, 7); //should be 12 in bank

	    assertThrows(IllegalArgumentException.class, () -> {bank.resourceDeposit(ResourceType.WOOL, -2);});
        //check
        assertEquals(12, bank.getResourceAmount(ResourceType.WOOL));   //nothing should have went into the bank
    }


    //=======================================================
    @Test
    void testGetResourceAmount() {
        //create
        Bank bank = new Bank();
        //test
        int gotResource = bank.getResourceAmount(ResourceType.LUMBER);
        //check
        assertEquals(19, gotResource);
    }

    @Test
    void testGetResourceAmountAfterABunchOfBankMovementsHaveHappened() {
        //create
        Bank bank = new Bank();
        //test
        bank.transferToPlayer(ResourceType.LUMBER, 9);
        bank.resourceDeposit(ResourceType.LUMBER, 2);
        int gotResource = bank.getResourceAmount(ResourceType.LUMBER);
        //check
        assertEquals(12, gotResource);
    }

    //=======================================================
    //added these after code has been refactored...

    @Test
    void testHasResourcesWithNullResourceType() {
        //create
        Bank bank = new Bank();
        //test
	    assertThrows(IllegalArgumentException.class, () -> {bank.hasResources(null, 5);});

    }

    @Test
    void testTransferToPlayerWithNullResourceType() {
        //create
        Bank bank = new Bank();
        //test
		assertThrows(NullPointerException.class, () -> {bank.transferToPlayer(null, 5);});
    }

    @Test
    void testTransferToPlayerMultipleResources() {
        //create
        Bank bank = new Bank();
        //test
        bank.transferToPlayer(ResourceType.LUMBER, 3);
        bank.transferToPlayer(ResourceType.BRICK, 2);
        bank.transferToPlayer(ResourceType.WOOL, 5);
        //check
        assertEquals(16, bank.getResourceAmount(ResourceType.LUMBER));
        assertEquals(17, bank.getResourceAmount(ResourceType.BRICK));
        assertEquals(14, bank.getResourceAmount(ResourceType.WOOL));
    }





}*/
