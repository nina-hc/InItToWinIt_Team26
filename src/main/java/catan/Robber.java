package catan;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * when 7 is rolled, no one receives resources
 * everyone with more than 7 resources must select half (rounded down) and return to the bank
 * then player moves the robber to tileID number
 * then player steals 1 random resource from an opponent who has a settlement or city adjacent to the target terrain hex...
 * if the target hex is adjacent to 2 or more players settlements, then u choose which one you want to steal from

 *
 * class representing the Robber when a 7 is rolled
 * handles discarding half the hand of users with 7 or more cards (randomly), moving the robber(randomly), and stealing a resource from a plyer whos on the robbed tile (randomly)
 *
 * @author Synthia Rosenberger
 * @version February 2026, McMaster University
 */
public class Robber {

    //=========VARIABLES========
    private Tile currentTile; //tile that the robber is on
    private Random random;

    //=========METHODS========
    //constructor:
    public Robber(Tile startTile) {
        this.currentTile = startTile;
        this.currentTile.setRobber(true);   //insure robber is on the tile
        this.random = new Random();
    }


    //discard half cards
    public void discardHalf(Player player, Bank bank) {
        ResourceHand hand = player.getResourceHand();
        int totalCards = hand.totalPlayerCard();

        if (totalCards > 7) {
            //calculate and remove cards
            int amountToDiscard = totalCards / 2;    //rounded down naturally
            List<ResourceType> discardedCards = hand.discardHalfForSeven();        //***MAKE A METHOD FOR THIS

            //return discarded cards to the bank
            for (ResourceType card : discardedCards) {
                bank.resourceDeposit(card, 1);
            }

            //print what's happening to screen
            System.out.println("Player " + player.getPlayerID() + " discarded " + discardedCards.size() + " cards to the bank");
        }
    }


    //move robber
    public void moveRobber(Tile chosenTile) {
        currentTile.setRobber(false);   //remove robber from the old tile
        chosenTile.setRobber(true);        //place robber on new tile
        currentTile = chosenTile;
    }


    //steal one random card
    public void stealCard(Player thief, Player victim) {

        ResourceHand victimHand = victim.getResourceHand(); //get players hand of cards
        ResourceType stolenResource = victimHand.removeCardForSteal();  //steal card

        //transfer stolen resource to thief
        if (stolenResource != null) {

            //give card to the thief
            ResourceHand thiefHand = thief.getResourceHand();
            thiefHand.addResource(stolenResource, 1);

            System.out.println("Player " + thief.getPlayerID() + " stole a" + stolenResource + " from player" + victim.getPlayerID());

        } else {
            System.out.println("Player " + victim.getPlayerID() + " has no cards");
        }
    }

    public Tile chooseRandomTile(Board board){

        int index = random.nextInt(19);  //0... 18 tiles
        Tile chosenTile = board.getTile(index);

        //make sure the random tile isnt the same as the previous one
        while (chosenTile == currentTile) {
            index = random.nextInt(19);
            chosenTile = board.getTile(index);
        }

        return chosenTile;
    }


    //maybe should be in a diff class
    public void executeSevenRoll(Board board, Bank bank, Player[] players, Player currentPlayer) {

        System.out.println("A seven was rolled");

        //discard half if there's more than 7 cards in any players hands
        for (Player player : board.getPlayers()) {  
            discardHalf(player, bank);
        }

        //move robber to new tile
        Tile chosenTile = chooseRandomTile(board);
        moveRobber(chosenTile);
        System.out.println("Robber moved");


        //find players connected to robbed tile
        List<Player> victims = board.getPlayersAdjacentToTile(chosenTile);

        //remove current player... thief cant steal from themselves
        victims.remove(currentPlayer);

        if (victims.isEmpty()) {
            System.out.println("There are no players to steal from on this tile");
            return;
        }

        //randomly choose a victim to steal from
        Player victim = victims.get(random.nextInt(victims.size()));
        stealCard(currentPlayer, victim);   //thief == current player

    }
}
