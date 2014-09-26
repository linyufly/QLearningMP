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

  private double bestUtility(int state) {
	  
	  int i = 0;
	  double bestUtil = 0;
	  
	  for(i=0; i < this.numOfActions; i++)
	  {
		  if(this.qValue[state][i] > bestUtil)
		  {
			  bestUtil = this.qValue[state][i];
		  }
	  }
	  
	  return bestUtil;
  }
  
  private int bestAction(int state) {
	  
	  int i = 0, ctr = 0, bestIdx = 0, numActions = this.numOfActions;
	  int[] bestList = new int[numActions];
	  double bestUtil = 0;
	  int bestAction = 0;
	  
	  for(i=0; i < numActions; i++)
	  {
		  if(this.qValue[state][i] == bestUtil)
		  {
			  
			  bestList[ctr] = i;
			  ctr++;
			  
		  } else if(this.qValue[state][i] > bestUtil) {
			  
			  ctr = 0;
			  
			  bestUtil = this.qValue[state][i];
			  bestAction = i;
			  bestList[ctr] = i;
			  
			  ctr++;
			  
		  }
	  }
	  
	  if(ctr > 1)
	  {
		  bestIdx = this.rand.nextInt(ctr);
		  bestAction = bestList[bestIdx];
	  }
	  
	  return bestAction;
	  
  }

  public int chooseAction(int state) {
	  
	  if (this.rand.nextDouble() < epsilon) {
		  
		  return this.rand.nextInt(this.numOfActions);
		  
	  } else {
		  
		  return bestAction(state);
		  
	  }
	  
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	  
	  this.qValue[oldState][action] = this.qValue[oldState][action] + rate * ( reward + (discount * bestUtility(newState) ) - this.qValue[oldState][action] );
  }

  public Policy getPolicy() {
	  
	  int[] actions = new int[this.numOfStates];
	  int i = 0;
	  
	  for(i = 0; i < this.numOfStates; i++)
	  {		
		  actions[i] = bestAction(i);
	  }
	  
	  return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
  private Random rand;
}
