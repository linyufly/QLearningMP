/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
	public QLearningAgent() {
		this.rand = new Random();
	}

	public void initialize(int numOfStates, int numOfActions) {
		this.qValue = new double[numOfStates][numOfActions]; // reward, states
																// and actions
		this.numOfStates = numOfStates;
		this.numOfActions = numOfActions;

	}

	// best utility not needed.
	// private double bestUtility(int state) {
	//
	// }

	public int chooseAction(int state) {
	    
	    if(rand.nextDouble() < epsilon){
	      int i = rand.nextInt(4);
	      return i;
	    }
		
		
		int maxI = 0;
		double maxQ = qValue[state][0];
		int numMaxQ = 1;
		for (int i = 1; i < numOfActions; i++) {
			if (qValue[state][i] > maxQ) {
				maxQ = qValue[state][i];
				maxI = i;
				numMaxQ = 1;
			}
			if (qValue[state][i] == maxQ) {
				numMaxQ++;
			}
		}

		// deal with several equal max Qs:
		if (numMaxQ == 1) {
			return maxI;
		}

		// randomly pick up one from several equals.
		int order = Math.abs(rand.nextInt(10)) % numMaxQ;

		for (int i = 0; i < numOfActions; i++) {
			if (qValue[state][i] == maxQ) {
				order--;
				if (order < 0)
					return i;
			}

		}
		return maxI; // should never be reached. 

	}

	public void updatePolicy(double reward, int action, int oldState,
			int newState) {
		// update reward.
		double q = qValue[oldState][action];
		double maxQ = qValue[newState][0];
		for (int i = 1; i < numOfActions; i++) {
			if (qValue[newState][i] > maxQ) {
				maxQ = qValue[newState][i];
			}
		}
		double value = q + rate * (reward + discount * maxQ - q);
		qValue[oldState][action] = value;

	}

	public Policy getPolicy() {
		int[] actions = new int[numOfStates];
		for (int i = 0; i < numOfStates; i++) {
			actions[i] = chooseAction(i);
		}
		Policy p = new Policy(actions);
		return p;
	}

	private static final double discount = 0.9;// garma
	private static final double rate = 0.1; // alpha
	private static final double epsilon = 0.00;// 0 for part1.
											// original example: 0.05;

	private int numOfStates;
	private int numOfActions;
	private double[][] qValue;
	private Random rand;
}
