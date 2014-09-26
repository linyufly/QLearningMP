/* Author: Jared Saul (jsaul3); given base code by Mingcheng Chen */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QLearningAgent implements Agent {
	public QLearningAgent() {
		this.rand = new Random();
	}

	public void initialize(int numOfStates, int numOfActions) {
		this.qValue = new double[numOfStates][numOfActions]; // Automatically initialized to 0
		this.numOfStates = numOfStates;
		this.numOfActions = numOfActions;
	}
	
	/**
	 * For any given state, there are {@code numOfActions} possible actions.  We should pick
	 * the one with the best {@code Q(s,a)} value.  However, we can have ties, so this
	 * function must be implemented in such a way that we randomly pick from all actions
	 * with the best Q-value.  Finally, we should note that there is an {@code epsilon}
	 * chance that we should simply pick randomly, period.
	 */
	public int chooseAction(int state) {
		if (rand.nextDouble() < epsilon) { // Within epsilon's range, so choose randomly:
			return rand.nextInt(this.numOfActions);
		} else { // Outside epsilon's range, so choose action with best Q-value:
			return bestAction(state);
		}
	}
	
	/**
	 * Helper function.  Given a state, return the best Q-value of all possible actions.
	 */
	private double bestQValue(int state) {
		double max = qValue[state][0];
		for (int i = 1; i < numOfActions; i++) {
			max = Math.max(max, qValue[state][i]);
		}
		return max;
	}
	
	/**
	 * Helper function.  Given a state, return the action number of the action with the best Q-value of all possible actions.
	 * Given a tie, it will randomly pick an action out of the best possible actions.
	 */
	private int bestAction(int state) {
		double maxValue = bestQValue(state);
		List<Integer> bestActions = new ArrayList<Integer>(); // Make an array to handle ties
		for (int i = 0; i < numOfActions; i++) {
			if (qValue[state][i] == maxValue) {
				bestActions.add(i);
			}
		}
		return bestActions.get(rand.nextInt(bestActions.size()));
	}
	
	/**
	 * Called after every state change.  To avoid making thousands of new Policy objects, just
	 * update the array, and let the one final call to getPolicy() get the actual Policy.
	 */
	public void updatePolicy(double reward, int action, int oldState, int newState) {
		// Formula: Q(s,a) = Q(s,a) + alpha(R(s->s' by a) + gamma*(max a' for Q(s',a')) - Q(s,a))
		qValue[oldState][action] += learningRate * (reward + (discountFactor * bestQValue(newState)) - qValue[oldState][action]);
	}
	
	/**
	 * This is called only when the simulation is over.
	 * Take the best actions and give them to the new Policy.
	 */
	public Policy getPolicy() {
		int[] actions = new int[this.numOfStates];
		for (int i = 0; i < this.numOfStates; i++) {
			actions[i] = bestAction(i);
		}
		return new Policy(actions);
	}

	/**
	 * An array of Q values, with indices [state, action].
	 */
	private double[][] qValue;
	
	private int numOfStates;
	private int numOfActions;
	private Random rand;
	
	private static final double discountFactor = 0.9;
	private static final double learningRate = 0.1;
	private static final double epsilon = 0.0;
	
}
