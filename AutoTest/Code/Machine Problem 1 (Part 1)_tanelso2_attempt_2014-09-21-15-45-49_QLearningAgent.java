/* Author: Thomas Nelson */

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
	  return qValue[state][bestAction(state)];
  }
  
  private int bestAction(int state) {
	  ArrayList<Integer> a = new ArrayList<Integer>();
	  double bestUtility = -Double.MAX_VALUE;
	  for(int i = 0; i < numOfActions; i++) {
		  if(qValue[state][i] == bestUtility) {
			a.add(i);
		  }
		  if(qValue[state][i] > bestUtility) {
			  bestUtility = qValue[state][i];
			  a.clear();
			  a.add(i);
		  }
	  }
	  return a.get(rand.nextInt(a.size()));
  }

  public int chooseAction(int state) {
	  double r = rand.nextDouble();
	  if(r<epsilon) {
		  return rand.nextInt(this.numOfActions);
	  }
	  else {
		  return bestAction(state);
	  }
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	  qValue[oldState][action] = qValue[oldState][action] + rate*(reward+discount*bestUtility(newState)-qValue[oldState][action]);

  }

  public Policy getPolicy() {
	  int ret[] = new int[numOfStates];
	  for(int i = 0; i < numOfStates; i++) {
		  ret[i] = bestAction(i);
	  }
	  
	  return new Policy(ret);

  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.1;

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
