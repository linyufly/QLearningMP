/* Author: Aditi Mhapsekar */

import java.util.Random;

public class QLearningAgent implements Agent {
	
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  private double bestUtility(int state) {
	  double max = qValue[state][0];
	  for (int j=1; j<numOfActions; j++) {
		  if (qValue[state][j] > max) {
			  max = qValue[state][j];
		  }
	  }
	  return max;
  }

  public int chooseAction(int state) {
	  if (rand.nextDouble() < epsilon) {
		  return rand.nextInt(this.numOfActions);
	  } else {
		  Double max = qValue[state][0];
		  int action = 0;
		  for (int i=1; i<numOfActions; i++) {
			  if (max < qValue[state][i]) {
				  max = qValue[state][i];
				  action = i;
			  } else if (max.equals(qValue[state][i]) && rand.nextInt(2) == 0) {
				  action = i;				  
			  }
		  }
		  return action;
	  }
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {	  
	  qValue[oldState][action] = qValue[oldState][action] + 
			  						rate * (reward + discount * bestUtility(newState) - qValue[oldState][action]); 
  }

  public Policy getPolicy() {
	  int[] actions = new int[this.numOfStates];
	  
	  for (int i=0; i<numOfStates; i++) {
		  double max = qValue[i][0];
		  int action = 0;
		  for (int j=1; j<numOfActions; j++) {
			  if (qValue[i][j] > max) {
				  max = qValue[i][j];
				  action = j;
			  }
		  }
		  actions[i] = action;
	  }
	  
	  return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
