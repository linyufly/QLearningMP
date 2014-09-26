package Platform;
/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {

	private static final double discount = 0.9;
	private static final double rate = 0.1;
	private static final double epsilon = 0.0;

	private int numOfStates;
	private int numOfActions;
	private double[][] qValue;

	public QLearningAgent() {
	}

	public void initialize(int numOfStates, int numOfActions) {
		this.qValue = new double[numOfStates][numOfActions];
		this.numOfStates = numOfStates;
		this.numOfActions = numOfActions;
	}

	public int chooseAction(int state) {
		double greedy = Math.random();		// Used to determine greedy or not
		
		if (greedy < epsilon) {				// choose random action
			int randAction = (int) (Math.random() * numOfActions);
			return randAction;
		}
		
		else {								// be greedy
			return highestQAction(state);
		}
	}
	
	/**
	 * Returns action with the highest Q-value. If two or more actions result
	 * in the same Q-value, choose one of them uniformly at random.
	 * @param state the state from which the resulting action takes place
	 * @return the action with the highest Q-value
	 */
	private int highestQAction(int state) {
		double[] values = qValue[state];
		ArrayList<Integer> bestActions = new ArrayList<Integer>();
		double maxValue = 0.0;
		
		for (int action = 0; action < numOfActions; action++) {
			double thisVal = values[action];
			
			if (thisVal > maxValue) {		// we found an action with a better q-value
				bestActions.clear();
				bestActions.add(action);
				maxValue = thisVal;
			}
			
			// found another action with the same q-value
			else if (Double.compare(thisVal, maxValue) == 0) {
				bestActions.add(action);
			}
		}
		
		int randAction = (int) (Math.random() * bestActions.size());
		return bestActions.get(randAction);
	}

	public void updatePolicy(double reward, int action, int oldState, int newState) {
		
		double oldValue = qValue[oldState][action];
		qValue[oldState][action] = oldValue + rate *
											(reward + 
											discount * maxQValue(newState) -	// estimate of optimal future value
											oldValue);
	}
	
	private double maxQValue(int state) {
		double[] values = qValue[state];
		double max = 0.0;
		for (int index = 0; index < numOfActions; index++) {
			if (values[index] > max) {
				max = values[index];
			}
		}
		
		return max;
	}

	public Policy getPolicy() {
		int[] actions = new int[numOfStates];
		for (int curState = 0; curState < numOfStates; curState++) {
			actions[curState] = highestQAction(curState);
		}
		
		return new Policy(actions);
	}

}
