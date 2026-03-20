package catan;



    /**
     * Strategy for building a road.
     * Gives immediate value if the player can afford and legally place a road.
     *
     * Value:
     * - Building a road without earning a VP = 0.8
     *
     * @author Marva Hassan
     */
    public class PlaceRoadStrategy extends AbstractStrategy implements StrategyEvaluator {

        /**
         * Evaluates the benefit of applying the road-building rule.
         *
         * @param player the current player
         * @param board the game board
         * @param randomizer randomizer for selecting placements
         * @param bank the game bank
         * @param placementValidator validates legal placements
         * @return 0.8 if a road can be built, otherwise 0.0
         */
        @Override
        public double evaluate(Player player, Board board, Randomizer randomizer, Bank bank, PlacementValidator placementValidator) {

            if (canBuildRoad(player, placementValidator)) {
                return 0.8;
            }

            return 0.0;
        }

        /**
         * Executes the strategy by attempting to build a road.
         *
         * @param player the current player
         * @param board the game board
         * @param randomizer randomizer for selecting placements
         * @param bank the game bank
         * @param placementValidator validates legal placements
         */
        @Override
        public void executeStrategy(Player player, Board board, Randomizer randomizer, Bank bank, PlacementValidator placementValidator) {


            if (canBuildRoad(player, placementValidator)) {
                BuildRoad buildRoad = new BuildRoad(player, board, randomizer, bank, placementValidator);
                buildRoad.execute();
            }
        }
    }



