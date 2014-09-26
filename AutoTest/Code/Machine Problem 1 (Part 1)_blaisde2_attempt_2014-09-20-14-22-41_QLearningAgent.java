import java.util.Random;

public class QLearningAgent implements Agent {

	private static final double discount = 0.9;
	private static final double rate = 0.1;
	private static final double epsilon = 0.00;

	private int numOfStates;
	private int numOfActions;
	private double[][] qValue;
	
	private Random rand;

	public QLearningAgent() {
		this.rand = new Random();
	}

	public void initialize(int numOfStates, int numOfActions) {
		this.qValue = new double[numOfStates][numOfActions];
		this.numOfStates = numOfStates;
		this.numOfActions = numOfActions;
	}
	
	/**
	 * @param state The state for which to find the action with the maximum expected utility
	 * @return One of the actions which will result in the maximum expected utility for this state
	 */
	private int maxUtilityAction(int state) {
		int numBestKnownActions = 0;
		int[] bestKnownActions = new int[numOfActions];
		
		for (int action = 0; action < numOfActions; action++) {
			if (numBestKnownActions == 0 || qValue[state][action] == qValue[state][bestKnownActions[0]])
				bestKnownActions[numBestKnownActions++] = action;
			else if (qValue[state][action] > qValue[state][bestKnownActions[0]]) {
				bestKnownActions = new int[numOfActions];
				numBestKnownActions = 1;
				bestKnownActions[0] = action;
			}
		}
		
		return bestKnownActions[(int) (rand.nextDouble()*numBestKnownActions)];

	}

	/**
	 * Find the action which maximizes Q(a,s)
	 * 
	 * @param state The state for which to find the action with the greatest expected utility
	 */
	public int chooseAction(int state) {
		if (rand.nextDouble() < epsilon) // Randomly decide if not to be greedy this time
			return (int) (rand.nextDouble() * numOfActions);
		
		return maxUtilityAction(state); // Return the action with the current expected maximum utility
	}

	public void updatePolicy(double reward, int action, int oldState, int newState) {
		qValue[oldState][action] += rate * (reward + (discount * qValue[newState][maxUtilityAction(newState)]) - qValue[oldState][action]);
	}

	public Policy getPolicy() {
		int[] mapping = new int[numOfStates];
		for (int state = 0; state < numOfStates; state++) {
			mapping[state] = maxUtilityAction(state);
		}
		return new Policy(mapping);
	}



}
