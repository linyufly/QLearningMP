/* Author: Haneul Kim */

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

      this.policy = new int[this.numOfStates];
      for (int i = 0; i<this.numOfStates; i++) {
          this.policy[i] = rand.nextInt(this.numOfActions);
      }

  }

  private double bestUtility(int state) {
	  
	  double maxQ = qValue[state][0];
	  for (int i=1; i<this.numOfActions; i++) {
		  if (maxQ < qValue[state][i])
			  maxQ = qValue[state][i];
	  }
	  return maxQ;
  }

  private int bestAction(int state) {
	  int ret=0;
	  double maxQ = qValue[state][0];
	  
	  for (int i=1; i<this.numOfActions; i++) {
		  if (maxQ < qValue[state][i]) {
			  maxQ = qValue[state][i];
			  ret = i;
		  }
	  }

	  int count = 0;
	  int[] tied = new int[this.numOfActions];
	  
	  for (int i=0; i<this.numOfActions; i++) {
		  if (maxQ == qValue[state][i]) {
			  count++;
			  tied[count-1] = i;
		  }
	  }
	  
	  if (count > 1)
		  return tied[rand.nextInt(count)];
	  
	  return ret;
  }
  public int chooseAction(int state) {
    // 0 - north, 1 - east, 2 - south, 3 - west
	  if (rand.nextInt(100)+1 <= epsilon*100) {

		  int ret = rand.nextInt(this.numOfActions);
		  return ret;
	  }

	  return policy[state];
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {

	  qValue[oldState][action] += rate*(reward + discount*bestUtility(newState) - qValue[oldState][action]);
	  policy[oldState] = bestAction(oldState);
	  
  }

  public Policy getPolicy() {

	  return new Policy(policy);

  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
  private int[] policy;
  private Random rand;
}
