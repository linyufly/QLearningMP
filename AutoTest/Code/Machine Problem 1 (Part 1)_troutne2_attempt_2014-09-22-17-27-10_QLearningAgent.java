/* Author: Jason Troutner */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
	  public QLearningAgent() {
		    
		  }

	  public void initialize(int numOfStates, int numOfActions) {
	    this.qValue = new double[numOfStates][numOfActions];
	    this.numOfStates = numOfStates;
	    this.numOfActions = numOfActions;
	  }

	  public int chooseAction(int state) {
		  double r = Math.random();
		  if(r<epsilon)
		  {
			  int randomAction = (int) (Math.random()*numOfActions);
			  return randomAction;
		  }
		  
		  int bestAction = 0;
		  int ties = 1;
		  double bestQ = this.qValue[state][0];
		  for(int a=1; a<numOfActions; a++)
		  {
			  double nextQ = this.qValue[state][a];
			  if(nextQ>bestQ)
			  {
				  ties = 1;
				  bestQ = nextQ;
				  bestAction = a;
			  }
			  if(nextQ==bestQ && 0.5<Math.random())
			  {
				  ties++;
				  bestAction = a;
			  }
		  }
		  if(ties>1)
		  {
			  int randPick = (int) (Math.random()*ties);
			  for(int a=0; a<numOfActions; a++)
			  {
				  double nextQ = this.qValue[state][a];
				  if(nextQ==bestQ && randPick==0)
				  {
					  bestAction = a;
				  }
				  randPick--;
			  }
		  }
		  return bestAction;
	  }

	  public void updatePolicy(double reward, int action,
	                           int oldState, int newState) {
		  double maxQaPrime = this.qValue[newState][0];
		  for(int a=1; a<numOfActions; a++)
		  {
			  double nextQaPrime = this.qValue[newState][a];
			  if(nextQaPrime > maxQaPrime)
				  maxQaPrime = nextQaPrime;
		  }
		  this.qValue[oldState][action] = this.qValue[oldState][action] 
				  + rate*(reward + discount*maxQaPrime - this.qValue[oldState][action]);
	  }

	  public Policy getPolicy() {
		  int[] actions;
		  actions = new int[numOfStates];
		  for(int s=0; s<numOfStates; s++)
		  {
			  int bestAction = 0;
			  double bestQ;
			  for(int a=1; a<numOfActions; a++)
			  {
				  bestQ = this.qValue[s][bestAction];
				  double nextQ = this.qValue[s][a];
				  if(nextQ>bestQ)
				  {
					  bestQ = nextQ;
					  bestAction = a;
				  }
			  }
			  actions[s] = bestAction;
		  }
		  return new Policy(actions);
	  }

	  private static final double discount = 0.9;
	  private static final double rate = 0.1;
	  private static final double epsilon = 0;

	  private int numOfStates;
	  private int numOfActions;
	  private double[][] qValue;
	}
