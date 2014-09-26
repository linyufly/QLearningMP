/*package Platform; */

/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
	
  public QLearningAgent() {
    this.random = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  private double bestUtility(int state) {
	  double max = Double.NEGATIVE_INFINITY;
	  
	  for(double qVal : qValue[state])
	  {
		  if(qVal>max)
			max = qVal;  
	  }
		  
	  return max;
  } 

  public int chooseAction(int state) {
	  int action = random.nextInt(this.numOfActions); 
	  
	  double x = random.nextDouble(); //Generating a Random Number
	  
	  if(x < epsilon) // Being Random
		return  action; // between 0 and 3 (inclusive) 
	  else // Being Greedy
		 return greedyORndom(state);
  }
  
  public int greedyORndom(int state)
  {
//	  double max = Double.NEGATIVE_INFINITY;
	  ArrayList<Integer> actionList = new ArrayList<Integer>();
	  //Find maximum q Value
//	  for(double qVal : qValue[state])
//	  {
//		  if(qVal>max)
//			max = qVal;  
//	  }
	  double max = bestUtility(state);
	  // Keep count of all those actions having Similar max values
	  for(int i=0; i<=this.numOfActions-1; i++)
	  {
		  if(max == qValue[state][i])
			  actionList.add(i);
	  }
	  //Choose the random action out of those having similar values to maximum q Value
	  return actionList.get(random.nextInt(actionList.size()));
  }
  
  public int greedy(int state)
  {   int maxIndex = -1;
	  double max = Double.NEGATIVE_INFINITY;

	  for(int i=0; i<=this.numOfActions-1; i++)
	  {
		  if(qValue[state][i] > max)
		  {
			  max = qValue[state][i];
		  	  maxIndex = i;
		  }
	  }
     return maxIndex;
  }  

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	  //Using this function as an opportunity to update qValue
	  //the policy will be updated and returned at once by getPolicy() method
	  qValue[oldState][action] = (1-rate)*qValue[oldState][action] + rate*(reward + discount*bestUtility(newState));
  }

  public Policy getPolicy() {
	  // Configuring all the policy values and returning the policy object
	  int[] actionPolicy =  new int[this.numOfStates];
	  
	  for(int i=0; i<=this.numOfStates-1; i++)
	  {
		  actionPolicy[i] = greedy(i);
	  } 
	  
	  return new Policy(actionPolicy);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.2;//0.05

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
  private Random random;//added member
}
