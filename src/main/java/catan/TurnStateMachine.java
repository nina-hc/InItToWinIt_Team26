package catan;



public class TurnStateMachine {
	/*initialize state to the start state*/
	private TurnState state = TurnState.START;

    /**
     * method to check if player input is a valid option happening in the correct order of moves
     * @param command player input
     * @return true or false depending if the move is valid
     */
	public boolean isValidOption(String command){
		switch(state){
			case START:
				//double check with marva if it's a capital at the start or all lowercase
				if("Roll".equals(command)){
					return true;
				}
				if("List".equals(command)){
					return true;
				}
				System.out.println("Error: You must roll first");
				return false;
			case ROLLED:
				if("Roll".equals(command)){
					System.out.println("Error: You have already rolled. You cannot roll again");
					return false;
				}
				return true;
			default:
				return false;
		}
	}

	/**
	 * Next state logic. Dictates where to go given the current state
	 * @param currentState current state
	 * @param command the command given
	 * @return the next state or throws an exception for unknown state
	 */
	private TurnState nextState(TurnState currentState, String command){
		switch(currentState){
			case START:
				/*if we get Roll then we go to the Rolled state*/
				if("Roll".equals(command)){
					return TurnState.ROLLED;
				}
				//otherwise we stay in start state
				return TurnState.START;

			case ROLLED:
				/*Go is the only command that moves us forward*/
				if("Go".equals(command)){
					return TurnState.END;
				}
				return TurnState.ROLLED;
			/*Ask group consensus for what should be the default*/
			default:
				throw new IllegalArgumentException("Error: Unknown state"+ currentState);
		}
	}

	/**
	 * Moves to the next state
	 * @param command given command
	 */
	public void goToNextState(String command){
		state = nextState(state,command);
	}

	public boolean isTurnDone(){
		return state == TurnState.END;
	}

}
