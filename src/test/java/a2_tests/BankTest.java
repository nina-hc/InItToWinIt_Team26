package a2_tests;

import main.java.Bank;
import org.junit.jupiter.api.Test;


import static main.java.ResourceType.*;
import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    @Test
    void testHasResources() {
        //create
        Bank bank = new Bank();
        //test
        boolean hasResource = bank.hasResources(LUMBER, 3);
        //check
        assertTrue(hasResource);    //should return true cuz this resource exists
    }

    @Test
    void testHasResourcesEdgeTooMany() {
        //create
        Bank bank = new Bank();
        //test
        boolean hasResource = bank.hasResources(LUMBER, 38);
        //check
        assertFalse(hasResource);    //should return false
    }

    @Test
    void testHasResourcesEdgeTooLittle() {
        //create
        Bank bank = new Bank();
        //test
        boolean hasResource = bank.hasResources(LUMBER, -3);
        //check
        assertFalse(hasResource);    //should return false
    }


    //=======================================================
    @Test
    void testTransferToPlayer() {
        //create
        Bank bank = new Bank();
        //test
        int transferredToPlayer = bank.transferToPlayer(BRICK, 1);
        //check
        assertEquals(1, transferredToPlayer);
    }

    @Test
    void testTransferToPlayerEdgeNotEnoughResourceInBank() {
        //create
        Bank bank = new Bank();
        //test
        int transferredToPlayer = bank.transferToPlayer(BRICK, 22);
        //check
        assertEquals(19, transferredToPlayer);    //should only give what it has
    }

    @Test
    void testTransferToPlayerEdgeRequestNumberIsNegative() {
        //create
        Bank bank = new Bank();
        //test
        int transferredToPlayer = bank.transferToPlayer(BRICK, -5);
        //check
        assertEquals(0, transferredToPlayer);    //should only give what it has
    }


    //=======================================================
    @Test
    void testResourceDeposit() {
        //create
        Bank bank = new Bank();
        //test
        bank.transferToPlayer(WOOL, 7); //should be 12 in bank
        bank.resourceDeposit(WOOL, 2);  //now there should be 14 in bank
        //check
        assertEquals(14, bank.getResourceAmount(WOOL));

    }

    @Test
    void testResourceDepositEdgeTryingToDepositWhenBankHasMaxResource() {
        //create
        Bank bank = new Bank();
        //test
        bank.resourceDeposit(WOOL, 4);  //by default there should be 12 in the bank alr
        //check
        assertEquals(19, bank.getResourceAmount(WOOL));   //nothing should have went into the bank
    }

    @Test
    void testResourceDepositEdgeTryingToDepositNegativeNumbers() {
        //create
        Bank bank = new Bank();
        //test
        bank.transferToPlayer(WOOL, 7); //should be 12 in bank
        bank.resourceDeposit(WOOL, -2);  //should throw an error
        //check
        assertEquals(12, bank.getResourceAmount(WOOL));   //nothing should have went into the bank
    }


    //=======================================================
    @Test
    void testGetResourceAmount() {
        //create
        Bank bank = new Bank();
        //test
        int gotResource = bank.getResourceAmount(LUMBER);
        //check
        assertEquals(19, gotResource);
    }

    @Test
    void testGetResourceAmountAfterABunchOfBankMovementsHaveHappened() {
        //create
        Bank bank = new Bank();
        //test
        bank.transferToPlayer(LUMBER, 9);
        bank.resourceDeposit(LUMBER, 2);
        int gotResource = bank.getResourceAmount(LUMBER);
        //check
        assertEquals(12, gotResource);
    }

    //=======================================================
    //added these after code has been refactored...

    @Test (expected = NullPointerException.class)
    void testHasResourcesWithNullResourceType() {
        //create
        Bank bank = new Bank();
        //test
        bank.hasResources(null, 5);
    }

    @Test (expected = NullPointerException.class)
    void testTransferToPlayerWithNullResourceType() {
        //create
        Bank bank = new Bank();
        //test
        bank.transferToPlayer(null, 5);
    }

    @Test
    void testTransferToPlayerMultipleResources() {
        //create
        Bank bank = new Bank();
        //test
        bank.transferToPlayer(LUMBER, 3);
        bank.transferToPlayer(BRICK, 2);
        bank.transferToPlayer(WOOL, 5);
        //check
        assertEquals(16, bank.getResourceAmount(LUMBER));
        assertEquals(17, bank.getResourceAmount(BRICK));
        assertEquals(14, bank.getResourceAmount(WOOL));
    }





}