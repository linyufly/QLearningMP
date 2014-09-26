/* Author: Robert Gazdziak (gazdzia2) */
/* Collaborated with: Thomas Lesniak (tlesnia2) */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.q = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }
//Returns best possible action (or random action of multiple best actions)
  private int bestUtility(int state) {
		double max = q[state][0];
		//Array list that holds best actions
		  ArrayList<Integer> bestActions = new ArrayList<Integer>();
		  bestActions.add(0);
		  for(int i = 1; i<numOfActions; i++) {
			  //if greater than max, clear list and put this action on
			  if(q[state][i] > max) {
				  max = q[state][i];
				  bestActions.clear();
				  bestActions.add(i);
			  }
			  //If equal, add on to the list
			  else if(q[state][i] == max) {
				  bestActions.add(i);
			  }
		  }
		  //Choose random action from list of best actions
		  int action = rand.nextInt(bestActions.size());
		  return bestActions.get(action);
	}
  //Returns best possible action or random action depending on epsilon
  public int chooseAction(int state) {
	  //Random double for epsilon-greedy
	  double greedy = rand.nextDouble();
	  int action = 0;
	  //if smaller, choose (one of the possible) best option(s)
	  if(greedy < (1.0-epsilon))
		  action = bestUtility(state);
	  //If equal or larger choose random action
	  else
		  action = rand.nextInt(numOfActions);
	  return action;
  }
//update the policy of oldState+action
  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	  q[oldState][action] = q[oldState][action] + 
			  rate*(reward+discount*(q[newState][chooseAction(newState)]-q[oldState][action]));
  }
//Returns best policy for every state
  public Policy getPolicy() {
	  int[] actions = new int[numOfStates];
	  //run through all states
	  for(int i = 0; i<numOfStates; i++) {
		  //for each state pick the best action
		  actions[i] = bestUtility(i);
	  }
	  return new Policy(actions);
  }

  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.00;

  private Random rand;
  private int numOfStates;
  private int numOfActions;
  private double[][] q;
}
