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
  }

  //private double bestUtility(int state) {

  //}

  public int chooseAction(int state) {
	if( rand.nextDouble() > epsilon )
	{
		double maxQ = qValue[state][0];
		int action = 0;
		for( int j = 1; j < 4; j++ ) {
			if( qValue[state][j] > maxQ ) {
				maxQ = qValue[state][j];
				action = j;
			} else if( qValue[state][j] == maxQ ) {
				if( rand.nextInt(2) == 1 )
					action = j;
			}
		}
		return action;
	} else {
		return rand.nextInt(4);
	}
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	double maxQ = qValue[newState][0];
	for( int i = 1; i < 4; i++ )
		if( qValue[newState][i] > maxQ )
			maxQ = qValue[newState][i];

	qValue[oldState][action] = qValue[oldState][action] + rate * (reward + discount * maxQ - qValue[oldState][action]);

  }

  public Policy getPolicy() {
	int[] actions = new int[numOfStates];
	
	for( int i = 0; i < numOfStates; i++ )
	{
		double maxQ = qValue[i][0];
		int action = 0;
		for( int j = 1; j < 4; j++ ) {
			if( qValue[i][j] > maxQ ) {
				maxQ = qValue[i][j];
				action = j;
			} else if( qValue[i][j] == maxQ ) {
				if( rand.nextInt(2) == 1 )
					action = j;
			}
			actions[i] = action;
		}
	}
	Policy ret = new Policy(actions);
	return ret;
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.05;

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
