/**
 * Author: Michal Ruopp
 * netID : ruopp2
 */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {

	private double[][] qValues;
	private int numOfStates;
	private int numOfActions;
	private Random rand;

	private static final double discount = 0.9;
	private static final double rate = 0.1;
	private static final double epsilon = 0.0;

	public QLearningAgent() {
		/* do nothing */
	}

	public void initialize(int numOfStates, int numOfActions) {
		this.qValues = new double[numOfStates][numOfActions];
		this.numOfStates = numOfStates;
		this.numOfActions = numOfActions;
		this.rand = new Random();
	}

	public int chooseAction(int state) {
		/**
		 * "...in state s, choose the action with the highest expected utility:
		 * pi* (s) = argmax( sigma( T (s, a, s') * U^(pi* (s')) ) )"
		 * https://courses.engr.illinois.edu/cs440/lectures/Lec5.pdf ; Slide 27
		 */

		int retval = 0;
		double r = rand.nextDouble();
		double max = 0.0;

		if (r < epsilon) {
			return rand.nextInt(4);
		}

		/*max = qValues[state][0];
		retval = 0;

		if (max < qValues[state][1]) {
			max = qValues[state][1];
			retval = 1;
		} else if (max < qValues[state][2]) {
			max = qValues[state][2];
			retval = 2;
		} else if (max < qValues[state][3]) {
			max = qValues[state][3];
			retval = 3;
		}*/

		return bestAction(state);
	}

	public void updatePolicy(double reward, int action, int oldState, int newState) {
		double max = qValues[newState][0];
		if (max < qValues[newState][1]) {
			max = qValues[newState][1];
		} else if (max < qValues[newState][2]) {
			max = qValues[newState][2];
		} else if (max < qValues[newState][3]) {
			max = qValues[newState][3];
		}

		qValues[oldState][action] += rate * (reward + (discount * max) - qValues[oldState][action]);
	}

	public Policy getPolicy() {
		int[] actions = new int[this.numOfStates];
		double max = 0.0;

		for (int i = 0; i < numOfStates; i++) {
			actions[i] = bestAction(i);
		}

		Policy retval = new Policy(actions);
		return retval;
	}

	/*private int bestAction(int state) {
		double max = 0.0;
		int retval; // action to be returned

		max = qValues[state][0];
		retval = 0;

		if (max < qValues[state][1]) {
			max = qValues[state][1];
			retval = 1;
		} else if (max < qValues[state][2]) {
			max = qValues[state][2];
			retval = 2;
		} else if (max < qValues[state][3]) {
			max = qValues[state][3];
			retval = 3;
		}

		return retval;
	}*/

	private int bestAction(int state) {
		double random = rand.nextDouble();
		double max = 0.0;
		int retval; // action to be returned

		max = qValues[state][0];
		retval = 0;

		if (qValues[state][0] == qValues[state][1] && qValues[state][1] == qValues[state][2] && qValues[state][2] == qValues[state][3]) {
			if (random < 0.25) {
				retval = 0;
			} else if (0.25 <= random && random < 0.5) {
				retval = 1;
			} else if (0.5 <= random && random < 0.75) {
				retval = 2;
			} else {
				retval = 3;
			}

			return retval;
		}

		if (qValues[state][0] == qValues[state][2] && qValues[state][2] == qValues[state][3] && qValues[state][3] > qValues[state][1]) {
			if (random < 0.33) {
				retval = 0;
			} else if (0.33 <= random && random < 0.67) {
				retval = 2;
			} else {
				retval = 3;
			}

			return retval;
		}

		if (qValues[state][0] == qValues[state][1] && qValues[state][1] == qValues[state][3] && qValues[state][3] > qValues[state][2]) {
			if (random < 0.33) {
				retval = 0;
			} else if (0.33 <= random && random < 0.67) {
				retval = 1;
			} else {
				retval = 3;
			}

			return retval;
		}

		if (qValues[state][0] == qValues[state][1] && qValues[state][1] == qValues[state][2] && qValues[state][2] > qValues[state][3]) {
			if (random < 0.33) {
				retval = 0;
			} else if (0.33 <= random && random < 0.67) {
				retval = 1;
			} else {
				retval = 2;
			}

			return retval;
		}

		if (qValues[state][3] == qValues[state][1] && qValues[state][1] == qValues[state][2] && qValues[state][2] > qValues[state][0]) {
			if (random < 0.33) {
				retval = 3;
			} else if (0.33 <= random && random < 0.67) {
				retval = 1;
			} else {
				retval = 2;
			}

			return retval;
		}

		if (max == qValues[state][1]) {
			if (random < 0.5) {
				retval = 1;
			}
		} else if (max < qValues[state][1]) {
			max = qValues[state][1];
			retval = 1;
		} else if (max == qValues[state][2]) {
			if (random < 0.5) {
				retval = 2;
			}
		} else if (max < qValues[state][2]) {
			max = qValues[state][2];
			retval = 2;
		} else if (max == qValues[state][3]) {
			if (random < 0.5) {
				retval = 3;
			}
		} else if (max < qValues[state][3]) {
			max = qValues[state][3];
			retval = 3;
		}

		return retval;
	}
}
