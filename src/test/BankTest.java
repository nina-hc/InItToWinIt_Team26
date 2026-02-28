package test;

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
        assertSame(1, transferredToPlayer);
    }

    void testTransferToPlayerEdgeNotEnoughResourceInBank() {
        //create
        Bank bank = new Bank();
        //test
        int transferredToPlayer = bank.transferToPlayer(BRICK, 22);
        //check
        assertSame(19, transferredToPlayer);    //should only give what it has
    }

    void testTransferToPlayerEdgeRequestNumberIsNegative() {
        //create
        Bank bank = new Bank();
        //test
        int transferredToPlayer = bank.transferToPlayer(BRICK, -5);
        //check
        assertSame(0, transferredToPlayer);    //should only give what it has
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
        assertSame(14, bank.getResourceAmount(WOOL));

    }

    @Test
    void testResourceDepositEdgeTryingToDepositWhenBankHasMaxResource() {
        //create
        Bank bank = new Bank();
        //test
        bank.resourceDeposit(WOOL, 4);  //by default there should be 12 in the bank alr
        //check
        assertSame(19, bank.getResourceAmount(WOOL));   //nothing should have went into the bank
    }

    @Test
    void testResourceDepositEdgeTryingToDepositNegativeNumbers() {
        //create
        Bank bank = new Bank();
        //test
        bank.transferToPlayer(WOOL, 7); //should be 12 in bank
        bank.resourceDeposit(WOOL, -2);  //should throw an error
        //check
        assertSame(12, bank.getResourceAmount(WOOL));   //nothing should have went into the bank
    }


    //=======================================================
    @Test
    void testGetResourceAmount() {
        //create
        Bank bank = new Bank();
        //test
        int gotResource = bank.getResourceAmount(LUMBER);
        //check
        assertSame(19, gotResource);
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
        assertSame(12, gotResource);
    }

}