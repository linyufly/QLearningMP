/* 
 * Author: Thomas Lesniak (tlesnia2)
 * Collaboration with Robert Gazdziak (gazdzia2)
 */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
	public QLearningAgent() {
		this.rand = new Random();
	}

	public void initialize(int numOfStates, int numOfActions) {
		this.q = new double[numOfStates][numOfActions];
		this.numOfStates = numOfStates;
		this.numOfActions = numOfActions;
	}

	private int chooseBestAction(int state) {
		// assume 0 is the max state initially and go from there
		double max = q[state][0];
		ArrayList<Integer> bestActions = new ArrayList<Integer>();
		bestActions.add(0);
		// add the best action to the list
		for (int i = 1; i < numOfActions; i++) {
			if (q[state][i] > max) 
			{
				// clear the list if a new max is found
				max = q[state][i];
				bestActions.clear();
				bestActions.add(i);
			} else if (q[state][i] == max) 
			{
				// append the max
				bestActions.add(i);
			}
		}
		// return random best action from the list
		int randAction = rand.nextInt(bestActions.size());
		return bestActions.get(randAction);
	}

	public int chooseAction(int state) {
		// returns either the best action or a random 
		// action based on the epsilon value.
		if (rand.nextDouble() < 1.0 - epsilon) {
			return chooseBestAction(state);
		} else {
			return rand.nextInt(numOfActions);
		}
	}

	public void updatePolicy(double reward, int action, int oldState,
			int newState) {
		// updates Q via eqn 21.8
		q[oldState][action] = q[oldState][action]
				+ rate * (reward + discount * (q[newState][chooseAction(newState)] - q[oldState][action]));
	}

	public Policy getPolicy() {
		// returns a list of best actions to take for each state
		int[] actions = new int[this.numOfStates];
		for (int i = 0; i < this.numOfStates; i++) {
			actions[i] = chooseBestAction(i);
		}
		return new Policy(actions);
	}

	private static final double discount = 0.9;
	private static final double rate = 0.1;
	private static final double epsilon = 0.0;

	private Random rand;
	private int numOfStates;
	private int numOfActions;
	private double[][] q;
}
