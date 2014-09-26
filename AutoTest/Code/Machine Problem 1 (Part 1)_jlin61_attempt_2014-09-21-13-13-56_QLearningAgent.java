/* Author: Jiaxin Lin jlin61 */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
	public QLearningAgent() {
		this.rand = new Random();
	}

	public void initialize(int numOfStates, int numOfActions) {
		this.qValue = new double[numOfStates][numOfActions];
		this.numOfStates = numOfStates;
		this.numOfActions = numOfActions;
	}
	
	public int maxIndex(int state) { //returns a random action which has the greatest utility
		ArrayList<Integer> maxIndexes = new ArrayList<Integer>();
		double maxQ = qValue[state][0];
		for (int i = 0; i < this.numOfActions; i++) {
			int compare = Double.compare(qValue[state][i], maxQ);
			if (compare == 0) {// founnd a same value
				maxIndexes.add(i);
			} else if (compare > 0) { //found a value greate than current max
				maxIndexes.clear();
				maxIndexes.add(i);
				maxQ = qValue[state][i];
			}
		}
		if(maxIndexes.size() == 0) {
			throw new NullPointerException("No Max Utility is Found??");
		}
		return maxIndexes.get(rand.nextInt(maxIndexes.size()));
	}

	

	private double bestUtility(int state) {
		return qValue[state][maxIndex(state)];

	}

	public int chooseAction(int state) {
		// deal with epsilon, choose a number X
		double randomN = rand.nextDouble();
		if (Double.compare(randomN, epsilon) < 0) {// choose randomly
			return rand.nextInt(this.numOfActions);
		} else { // choose greedily
			return maxIndex(state);// choose in the maximums
		}

	}

	public void updatePolicy(double reward, int action, int oldState, int newState) {
		qValue[oldState][action] = qValue[oldState][action] + this.rate * (reward + this.discount * bestUtility(newState) - qValue[oldState][action]);

	}

	public Policy getPolicy() {
		int[] actions = new int[this.numOfStates];
		for (int s = 0; s < this.numOfStates; s++) {
			actions[s] = maxIndex(s);
		}
		return new Policy(actions);

	}

	private static final double discount = 0.9;
	private static final double rate = 0.1;
	private static final double epsilon = 0.0; // get random number x:[0,1], if
												// x<e then choose randomly,
												// else choose greedily

	private int numOfStates;
	private int numOfActions;
	private double[][] qValue;
	private Random rand;
}
