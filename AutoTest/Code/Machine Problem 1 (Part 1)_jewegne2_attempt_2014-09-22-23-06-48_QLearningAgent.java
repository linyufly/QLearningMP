/* Author: Mingcheng Chen */

import java.util.ArrayList;
import java.util.Random;

public class QLearningAgent implements Agent {
	private static final double DISCOUNT = 0.9;
	private static final double RATE = 0.1;
	private static final double EPSILON = 0.0;

	private Random qRandom;
	private int numOfStates;
	private int numOfActions;
	private double[][] qValue;

	public QLearningAgent() {
		this.qRandom = new Random();
	}

	/**
	 * will be called only once at the beginning. It tells the agent the number
	 * of states and the number of actions in the world. The states are
	 * identi􏰁ed by integers from 0 to (numOfStates − 1), similarly the actions
	 * are 0 to (numOfActions − 1). In all the cases, numOfActions should be
	 * equal to 4
	 */
	public void initialize(int numOfStates, int numOfActions) {
		this.qValue = new double[numOfStates][numOfActions];
		this.numOfStates = numOfStates;
		this.numOfActions = numOfActions;
	}

	/**
	 * 
	 * @param state
	 * @return
	 */
	private double bestUtility(int state) {
		return this.qValue[state][bestActionFromState(state)];
	}

	private int bestActionFromState(int state) {
		ArrayList<Integer> topActions = new ArrayList<Integer>();
		double topQValue = -1;
		int topAction = -1;

		for (int i = 0; i < 4; i++) {
			double currentQValue = this.qValue[state][i];

			if (currentQValue >= topQValue) {
				if (currentQValue > topQValue) {
					topActions.clear();
				}
				topActions.add((Integer) i);
				topQValue = currentQValue;
			}
		}

		if (topActions.size() > 1) {
			topAction = topActions
					.get(this.qRandom.nextInt(topActions.size() - 1));
		} else {
			topAction = topActions.get(0);
		}

		return topAction;
	}

	/**
	 * The chooseAction method returns your selected action (an integer from {0,
	 * 1, 2, 3}) given the state as an argument.
	 */
	public int chooseAction(int state) {
		float decider = this.qRandom.nextFloat();
		if (decider <= EPSILON) {
			return this.qRandom.nextInt(numOfActions);
		} else {
			return bestActionFromState(state);
		}
	}

	/**
	 * The updatePolicy method processes the reward received by executing action
	 * which transitions from oldState to newState.
	 */
	public void updatePolicy(double reward, int action, int oldState,
			int newState) {
		double error = reward
				+ (DISCOUNT * bestUtility(newState) - bestUtility(oldState));

		this.qValue[oldState][action] = this.qValue[oldState][action] + RATE
				* error;
	}

	/**
	 * The getPolicy method should return a Policy object, which specifies the
	 * action for each state. More details can be found in Policy.java.
	 */
	public Policy getPolicy() {
		int[] states = new int[this.numOfStates];
		for (int i = 0; i < this.numOfStates; i++) {
			states[i] = bestActionFromState(i);
		}

		return new Policy(states);
	}
}
