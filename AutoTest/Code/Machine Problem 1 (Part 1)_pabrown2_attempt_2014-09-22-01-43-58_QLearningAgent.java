/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
	  
  }
  
  /* Initialize private members */
  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
    this.rand = new Random();
  }
  
  /* Epsilon % chance of picking random action, else choose the action with the highest Q value (random if tie) */
  public int chooseAction(int state)
  {	  
	  if(epsilonNum == 0)
		  return bestAction(state);
	  
	  if(this.rand.nextInt((int)Math.floor(epsilonNum)) == 1)
		  return this.rand.nextInt(4);
	  else
		  return bestAction(state);
  }
  
  /* Update the Q value table according to equation 21.8 from the textbook */
  public void updatePolicy(double reward, int action,
          int oldState, int newState)
  {
	  qValue[oldState][action] = qValue[oldState][action] + rate * (reward + (discount * bestUtility(newState)) - qValue[oldState][action]);
  }

  /* Creates a Policy object from the Q value table */
  public Policy getPolicy()
  {
	  int[] actions = new int[this.numOfStates];
	  for(int i = 0; i < this.numOfStates; i++)
	  {
		  actions[0] = bestAction(i);
	  }
	  policy = new Policy(actions);
	  return policy;
  }
  
  /* Helper Function: Returns the Q Value of the best direction from a given state */
  private double bestUtility(int state)
  {
	  double result = qValue[state][0];
	  
	  for(int i = 1; i < 4; i++)
	  {
		  if(qValue[state][i] > result)
		  {
			  result = qValue[state][i];
		  }
	  }
	  
	  return result;
  }
  
  /* Helper Function: Returns the best direction from a given state based on the Q Values */
  private int bestAction(int state)
  {
	  if(allQsZero(state))
		  return this.rand.nextInt(4);
	  
	  int result = 0;
	  double maxVal = 0;
	  
	  for(int i = 0; i < 4; i++)
	  {
		  if(qValue[state][i] == maxVal)
		  {
			  result = randomPickofTwo(result, i);
			  maxVal = qValue[state][result];
		  }
		  else if(qValue[state][i] > maxVal)
		  {
			  result = i;
			  maxVal = qValue[state][i];
		  }
	  }
	  
	  return result;
  }
  
  /* Helper Function: Returns true if Q Values for all 4 directions from a given state are 0, else false */
  boolean allQsZero(int state)
  {
	  return (qValue[state][0] == 0) && (qValue[state][1] == 0) && (qValue[state][2]) == 0 && (qValue[state][3] == 0);
  }
  
  /* Helper Function: Returns a random pick of the 2 numbers passed in as parameters */
  int randomPickofTwo(int one, int two)
  {
	  if(rand.nextInt(2) == 1)
		  return one;
	  else
		  return two;
  }

  private static final double discount = 0.9;				// Discount Factor = 0.9
  private static final double rate = 0.1;					// Learning Rate = 0.1
  private static final double epsilon = 0.05;				// Greedy Epsilon value = 0.05
  private static final double epsilonNum = epsilon * 100;	

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
  private Random rand;
  private Policy policy;
}