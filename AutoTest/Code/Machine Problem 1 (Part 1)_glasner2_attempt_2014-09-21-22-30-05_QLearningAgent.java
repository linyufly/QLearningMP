/* Author: Brett Glasner */

import java.util.Random;
import java.lang.Math;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
		
	 private static final double discount = 0.9;
	 private static final double rate = 0.1;
	 private static final double epsilon = 0.0;

	 private Random rand;
	 private int numOfStates;
	 private int numOfActions;
	 private double[][] qValue;
	  
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
	  
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  private double bestUtility(int state) {
	
	  //finds the maximum utility possible from executing any of the actions possible in State state
	  return Math.max(Math.max(qValue[state][0], qValue[state][1]), Math.max(qValue[state][2], qValue[state][3]));
	  
  }

  public int chooseAction(int state) {
	
	  //rolls dice to see if we will explore randomly
	  if(rand.nextDouble() < epsilon)
	  {
		  //chooses a random action
		  return rand.nextInt(numOfActions);
	  }
	  else	//if we don't explore randomly we must choose the best possible action instead
	  {
		  //declares a list for storing ties and sets the current best action to action 0
		  ArrayList<Integer> ties = new ArrayList<Integer>();
		  int best = 0;
		  
		  //loops through all possible actions to see if there exists one with a higher qValue
		  for(int i = 1; i < numOfActions; i++)
		  {
			  //sets the best action to action i if the qValue is greater than the current one
			  if(qValue[state][best] < qValue[state][i])
			  {
				  best = i;
			  }
			  
			  //if there is a tie add the actions that tie to our list of ties
			  else if(qValue[state][best] == qValue[state][i])
			  {
				  //if the list of ties is empty add the current best and action i to the list
				  if(ties.size() == 0)
				  {
					  ties.add(best);
					  ties.add(i);
				  }
				  //if the list of ties is not empty only add action i
				  else
				  {
					  ties.add(i);
				  }
			  }
			  
		  }
		  
		  //if the list of ties is not empty then we have at least two actions with the same qValue in this state
		  if(ties.size() != 0)
		  {
			  //pick a random index from the list of ties and then return the action stored at that index
			  int index = rand.nextInt(ties.size());
			  return ties.get(index);
		  }
		 
		  //if there are no ties return the best possible action found
		  return best;
	  }
  }

  public void updatePolicy(double reward, int action, int oldState, int newState) {
	  
	  qValue[oldState][action] = qValue[oldState][action] + rate*(reward + discount*bestUtility(newState) - qValue[oldState][action]);
  }

  public Policy getPolicy() {
	  
	  int [] actions = new int[numOfStates];
	  for(int i = 0; i < numOfStates; i++)
	  {
		  int best = 0;
		  
		  //loops through all possible actions to see if there exists one with a higher qValue
		  for(int j = 1; j < numOfActions; j++)
		  {
			  //sets the best action to action i if the qValue is greater than the current one
			  if(qValue[i][best] < qValue[i][j])
			  {
				  best = j;
			  }
		  }
		  
		  actions[i] = best; 
	  }
	  
	  return new Policy(actions);
	  
  }
  
}
