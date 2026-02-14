package InItToWinIt_Team26;

import java.util.Random;

/**
 * Randomizes a roll of two six-sided dice
 */
public class Randomizer {

    // random object used for generating numbers
    private Random random;

    /**
     * constructor initializes the random object
     */
    public Randomizer() {

        this.random = new Random();
    }

    /**
     * roll two six-sided dice and return the sum
     * @return total of two dice (2-12)
     */
    public int rollDice() {
        int die1 = random.nextInt(6) + 1; //1-6
        int die2 = random.nextInt(6) + 1; //1-6
        return die1 + die2;
    }

    /**
     * return a random integer in an inclusive range
     * @param min lower bound (inclusive)
     * @param max upper bound (inclusive)
     * @return random integer between min and max
     */
    public int randomSelection(int min, int max) {

        return random.nextInt(max - min + 1) + min;
    }





}//Randomizer