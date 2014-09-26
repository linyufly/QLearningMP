// package mp1;
// (previous line for package)

/* Author: Mingcheng Chen */
/* Student: Cassio dos Santos Sousa */
/* Class: CS440 - Fall 2014 */
/* MP1 - Part 1 */

import java.util.Random;
import java.util.ArrayList;

public class QAgent implements Agent {

	public QAgent() {
		this.rand = new Random();
	}

	public void initialize(int numOfStates, int numOfActions) {
		this.qValue = new double[numOfStates][numOfActions];
		this.numOfStates = numOfStates;
		this.numOfActions = numOfActions;
	}

	private double bestUtility(int state) {
		double biggest_utility = qValue[state][0]; /* default utility */
		for (int an_action = 0; an_action < numOfActions; an_action++) {
			if (biggest_utility < qValue[state][an_action])
				biggest_utility = qValue[state][an_action];
		}
		return biggest_utility;
	}

	public int chooseAction(int state) {
		double utility = bestUtility(state);
		int next_action = 0; /* default action */
		ArrayList<Integer> possible_actions = new ArrayList<Integer>();
		for (int an_action = 0; an_action < numOfActions; an_action++) {
			if (utility == qValue[state][an_action]) {
				next_action = an_action;
				possible_actions.add(an_action);
			}
		}
		int num_of_actions = possible_actions.size();
		if (num_of_actions <= 1)
			return next_action;
		else
			/* if two actions have the same utility, choose one randomly */
			return possible_actions.get(rand.nextInt(num_of_actions));
	}

	public void updatePolicy(double reward, int action, int oldState,
			int newState) {
		int action_prime = chooseAction(newState);
		qValue[oldState][action] = (1.0 - rate) * qValue[oldState][action]
				+ rate * (reward + discount * qValue[newState][action_prime]);
	}

	public Policy getPolicy() {
		int[] actions = new int[this.numOfStates];
		for (int a_state = 0; a_state < this.numOfStates; a_state++) {
			if (rand.nextDouble() < epsilon)
				actions[a_state] = rand.nextInt(numOfActions);
			else
				actions[a_state] = chooseAction(a_state);
		}
		return new Policy(actions);
	}

	private static final double discount = 0.9;
	private static final double rate = 0.1;
	private static final double epsilon = 0.0; /* as required */

	private int numOfStates;
	private int numOfActions;
	private double[][] qValue;
	private Random rand;
}