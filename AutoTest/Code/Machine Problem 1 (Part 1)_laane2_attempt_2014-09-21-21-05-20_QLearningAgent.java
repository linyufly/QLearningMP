/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;
import java.lang.Double;


public class QLearningAgent implements Agent {
  
/**
 * 
 */
public QLearningAgent() {
    this.rand = new Random();
  }

  /* (non-Javadoc)
 * @see Agent#initialize(int, int)
 */
public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  /**
 * @param state
 * @return
 */
private double bestUtility(int state) {
	  double max = Double.NEGATIVE_INFINITY;
	  for (double a: this.qValue[state]){
		  	if (a > max){
		  		max = a;
		  	}
	  }
	return max;
  }


  /**
 * @param state
 * @return
 */
	public int _chooseAction(int state) {
		double max = Double.NEGATIVE_INFINITY;
		double tie_count = 1;
		int max_i = this.rand.nextInt(this.numOfActions);
		for (int i = 0; i < this.qValue[state].length; i++) {
			double my_value = this.qValue[state][i];
			if (max < my_value) {
				max = my_value;
				max_i = i;
			} else if (max == my_value
					&& (this.rand.nextDouble() <= 1. / ++tie_count)) {
				max = my_value;
				max_i = i;
			}
		}
		return max_i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Agent#chooseAction(int)
	 */
	public int chooseAction(int state) {
	  double prob = this.rand.nextDouble();
	  if(prob > epsilon){
		  	return this._chooseAction(state);
	  } else {
		  return this.rand.nextInt(this.numOfActions);
	  }
  }

  /* (non-Javadoc)
 * @see Agent#updatePolicy(double, int, int, int)
 */
public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	  this.qValue[oldState][action] = this.qValue[oldState][action]+ rate *
			  	(reward + discount * this.bestUtility(newState) - this.qValue[oldState][action]); 
  }

  /* (non-Javadoc)
 * @see Agent#getPolicy()
 */
public Policy getPolicy() {
	  int p[] = new int[this.numOfStates];
	  	for (int i = 0; i < this.numOfStates;i++){
	  		p[i] = this._chooseAction(i);
	  	}

		return new Policy(p);
  }

/**
 * 
 */
private static final double discount = 0.9;
  /**
 * 
 */
private static final double rate = 0.1;
  /**
 * 
 */
private static final double epsilon = 0.05;
  /**
 * 
 */
private Random rand;
  
  /**
 * 
 */
private int numOfStates;
  /**
 * 
 */
private int numOfActions;
  /**
 * 
 */
private double[][] qValue;
}
