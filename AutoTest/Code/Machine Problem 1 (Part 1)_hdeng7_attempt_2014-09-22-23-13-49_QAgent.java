/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;
//finished version
//hdeng7


public class QAgent implements Agent {
  public QAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
	for (int i = 0; i < numOfStates; i++) {
		for (int j = 0; j < numOfActions; j++) {
			this.qValue[i][j] = 0.0;
		}  
	}
 }

  private double bestUtility(int state) {
	 double best = -1;
		for (int i = 0; i < this.numOfActions; i++) {
			if (this.qValue[state][i] > best) {
				best = this.qValue[state][i];
			}
		}
		return best;
  }

  public int chooseAction(int state) {

	if (rand.nextDouble() <= epsilon) {
		return rand.nextInt(this.numOfActions);
	}



	int result[] = new int[numOfActions];
	int maxCounter=0; 
	double maxQ = this.bestUtility(state);
	for (int i = 0; i < this.numOfActions; i++) {

		if (this.qValue[state][i] == maxQ) {
			result[maxCounter]=i;			
			maxCounter++;
		}	
	}
	
	return result[this.rand.nextInt(maxCounter)];


  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	this.qValue[oldState][action] = this.qValue[oldState][action] + rate* (reward + discount * this.bestUtility(newState) -this.qValue[oldState][action]);
  }

  public Policy getPolicy() {
	int[] actions = new int[this.numOfStates];
	for (int i = 0; i < this.numOfStates; i++) {
		double maxQ = this.bestUtility(i);
		for (int j = 0; j < this.numOfActions; j++) {
			if (this.qValue[i][j] == maxQ) {
				actions[i] = j;
				break;
			}
		}	
	}

	return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.05;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
  private Random rand;
}
