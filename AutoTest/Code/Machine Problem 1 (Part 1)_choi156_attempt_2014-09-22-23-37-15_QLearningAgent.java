/* Author: Mingcheng Chen */

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

    for (int i = 0; i < numOfStates; i++) {
      for (int j = 0; j < numOfActions; j++) {
        this.qValue[i][j] = 0.0;
      }
    }
  }

public int chooseAction(int state) {
   
	double max = this.qValue[state][0];
	ArrayList<Integer> array = new ArrayList<Integer>();  
	int size = 0;
	if (this.rand.nextDouble() <= epsilon) {
		return this.rand.nextInt(numOfActions);
	}
   
	for (int i = 1; i < this.numOfActions; i++) {
		if (this.qValue[state][i] > max) {
			max = this.qValue[state][i];
		}
	}
    
    for (int i = 0; i < this.numOfActions; i++) {
       	if (this.qValue[state][i] == max) {
       		array.add(i);
       	}
    }
    size = array.size();
    return array.get(this.rand.nextInt(size));
  }

public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	  
	  double temp = (1.0 - rate);
	  double max = this.qValue[newState][0];
	  for (int i = 1; i < this.numOfActions; i++) {
	      if (this.qValue[newState][i] > max) {
	        max = this.qValue[newState][i];
	      }
	    }
	  
	  this.qValue[oldState][action] *= temp;
	  	  
	  this.qValue[oldState][action] += rate * (reward + discount * max);
  }

public Policy getPolicy() {
    int[] action = new int[this.numOfStates];
    for (int state = 0; state < this.numOfStates; state++) {
    	double max = this.qValue[state][0];
    	for (int i = 1; i < this.numOfActions; i++) {
  	      	if (this.qValue[state][i] > max) {
  	        max = this.qValue[state][i];
  	      }
  	    }
    	for (int i = 0; i < this.numOfActions; i++) {
    		if (this.qValue[state][i] == max) {
    			action[state] = i;
    		break;
        }
      }
    }

    return new Policy(action);
}

private static final double discount = 0.9;
private static final double rate = 0.1;
private static final double epsilon = 0.05;
private int numOfStates;
private int numOfActions;
private double[][] qValue;
private Random rand;
}