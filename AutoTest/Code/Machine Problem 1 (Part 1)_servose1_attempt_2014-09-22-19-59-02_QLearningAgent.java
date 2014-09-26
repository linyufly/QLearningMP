/* Author: David Servose 
 * netid: servose1
 * CS440 MP1 part 1
 */

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

  private double bestUtility(int state) {
	  double bestQValue = this.qValue[state][0];
	  
	  for(int i = 1; i < this.numOfActions; i++) {
		  if(this.qValue[state][i] > bestQValue) {
			  bestQValue = this.qValue[state][i];
		  }
	  }
	  
	  return bestQValue;
  }

  public int chooseAction(int state) {
	  ArrayList<Integer> actionList = new ArrayList<Integer>();
	  actionList.add(0);
	  double bestQValue = this.qValue[state][0];
	  int action = 0;
	  
	  if(rand.nextDouble() < epsilon) {
		  return rand.nextInt(this.numOfActions);
	  } else {
		  for(int i = 1; i < this.numOfActions; i++) {
			  if(this.qValue[state][i] > bestQValue) {
				  bestQValue = this.qValue[state][i];
				  actionList.clear();
				  actionList.add(i);
			  } else if(this.qValue[state][i] == bestQValue) {
				  actionList.add(i);
			  }
		  }
		  action = actionList.get(rand.nextInt(actionList.size()));
		  return action;
	  }
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	  this.qValue[oldState][action] = this.qValue[oldState][action] + rate*(reward + discount*bestUtility(newState) - this.qValue[oldState][action]);
  }

  public Policy getPolicy() {
	int[] actions = new int[this.numOfStates];
	ArrayList<Integer> actionList = new ArrayList<Integer>();
	double bestQValue = 0;
	
	for (int i = 0; i < this.numOfStates; i++) {
		actionList.add(0);
		bestQValue = this.qValue[i][0];
		for(int j = 1; j < this.numOfActions; j++) {
			if(this.qValue[i][j] > bestQValue) {
				actionList.clear();
				actionList.add(j);
				bestQValue = this.qValue[i][j];
			} else if (this.qValue[i][j] == bestQValue) {
				actionList.add(j);
			}
		}
		actions[i] = actionList.get(rand.nextInt(actionList.size()));
	}

	return new Policy(actions);
  }

  private static Random rand;
  
  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
