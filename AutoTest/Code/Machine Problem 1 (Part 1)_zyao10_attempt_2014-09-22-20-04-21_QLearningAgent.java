/* Author: Zhuojun Yao zyao10 */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {

	private static final double discount = 0.9;
	private static final double rate = 0.1;
	private static final double epsilon = 0.0; // get random number x:[0,1], if
												// x<e then choose randomly,
												// else choose greedily
	private int numOfStates;
	private int numOfActions;
	private double[][] QLearningArray;
	private Random rand;

	public QLearningAgent() {
		this.rand = new Random();
	}

	public void initialize(int numOfStates, int numOfActions) {
		this.QLearningArray = new double[numOfStates][numOfActions];
		this.numOfStates = numOfStates;
		this.numOfActions = numOfActions;
	}
	
	public int compareAction(int state) { //returns a random action which has the greatest utility
		double n = QLearningArray[state][0];
		double e = QLearningArray[state][1];
		double s = QLearningArray[state][2];
		double w = QLearningArray[state][3];
		ArrayList<Integer> maxQArray = new ArrayList<Integer>();
		double maxQ = n;
		if(Double.compare(e, maxQ) > 0) maxQ = e;
		if(Double.compare(s, maxQ) > 0) maxQ = s;
		if(Double.compare(w, maxQ) > 0) maxQ = w;
		
		if(Double.compare(n, maxQ) == 0) maxQArray.add(0);
		if(Double.compare(e, maxQ) == 0) maxQArray.add(1);
		if(Double.compare(s, maxQ) == 0) maxQArray.add(2);
		if(Double.compare(w, maxQ) == 0) maxQArray.add(3);
		
		if(maxQArray.size() == 0) {
			throw new NullPointerException("No Max Utility is Found??");
		}
		return maxQArray.get(rand.nextInt(maxQArray.size()));
	}

	public int chooseAction(int state) {
		double randomN = rand.nextDouble();
		if(Double.compare(randomN, epsilon) < 0)
			return rand.nextInt(numOfActions);
		else{
			return compareAction(state);
		}
	}

	public void updatePolicy(double reward, int action, int oldState, int newState) {
		QLearningArray[oldState][action] = QLearningArray[oldState][action] + rate * (reward + discount * QLearningArray[newState][compareAction(newState)] - QLearningArray[oldState][action]);
	}

	public Policy getPolicy() {
		int[] actions = new int[this.numOfStates];
    	for (int i = 0; i < this.numOfStates; i++) {
      		actions[i] = compareAction(i);
    	}
    	return new Policy(actions);

	}
}
