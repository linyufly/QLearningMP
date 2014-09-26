/* Author:  Goran Tomic */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
	  System.out.print("Hello");
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
    policy = new int[this.numOfStates];
    for (int i = 0; i < this.numOfStates; i++) {
       policy[i] = rand.nextInt(this.numOfActions);
    	//policy[i] = 1;
    }

    
  }

  private double bestUtility(int state) {
	  double best = qValue[state][0];
	  for(int i = 1 ; i < numOfActions; i++){
		  if (qValue[state][i] > best)
			  best = qValue[state][i];
	  }
	  return best;
  }
  private int bestAction(int state) {
	  double best = qValue[state][0];
	  int[] optActions = new int[numOfActions];
	  int action = 0;
	  for(int i = 0 ; i < numOfActions; i++){
			  if (qValue[state][i] > best){
				  best = qValue[state][i];
				  action = i;
			  }
	}
	  int count=-1;
	  for (int i  = 0 ; i < numOfActions; i++){
		  if (qValue[state][i] == best){
			  count++;
			  optActions[count] = i;
		  }
	  }
	  if (count != 0)
		  return optActions[rand.nextInt(count+1)];
  
	  return action;
}
  public int chooseAction(int state) {
	 
	  int prob = rand.nextInt(100) + 1;
	  //System.out.print(prob + '\n');
	  //System.out.print("prob\n");
	  if ( prob <= epsilon*100){
		  int r = rand.nextInt(this.numOfActions);
		  //System.out.print(r);
		  return r;
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
  private static final double epsilon = 0.00;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
  private Random rand;
  private int[] policy;
}

