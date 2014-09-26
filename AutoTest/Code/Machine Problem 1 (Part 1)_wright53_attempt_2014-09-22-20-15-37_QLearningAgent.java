/* Author: Mingcheng Chen */

import java.util.ArrayList;
import java.util.Random;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    this.rand = new Random();
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
  }

  private double bestQValue(int state) {
	  // Compute max w.r.t. actions for the given state
	  int bestAction = -1;
	  for (int j = 0; j < numOfActions; ++j)
	  {
		  if (bestAction == -1 || qValue[state][j] > qValue[state][bestAction])
		  {
			  bestAction = j;
		  }
	  }
	  return qValue[state][bestAction];
  }

  private int chooseBestAction(int state) {
	  // Compute argmax w.r.t. actions for the given state
	  ArrayList<Integer> bestActionArr = new ArrayList<Integer>();
	  double bestQValue = 0.0;
	  for (int j = 0; j < numOfActions; ++j)
	  {
		  if (j == 0 || qValue[state][j] >= bestQValue)
		  {
			  if (j > 0 && qValue[state][j] > bestQValue)
			  {
				  bestActionArr.clear();
			  }
			  bestQValue = qValue[state][j];
			  bestActionArr.add(new Integer(j));
		  }
	  }
	  return bestActionArr.get(rand.nextInt(bestActionArr.size()));
  }
  
  public int chooseAction(int state) {
	  double randValue = this.rand.nextDouble();
	  int chosenAction;
	  if (randValue < epsilon)
	  {
		  // Choose action randomly
		  chosenAction = this.rand.nextInt(numOfActions);
	  }
	  else
	  {
		  // Be greedy with current Q
		  chosenAction = chooseBestAction(state);
	  }
	  return chosenAction;
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	  qValue[oldState][action] += rate * (reward
			  + discount * bestQValue(newState)
			  - qValue[oldState][action]);
  }

  public Policy getPolicy() {
	  int[] actions = new int[numOfStates];
	  for (int i = 0; i < numOfStates; ++i)
	  {
		  // Always be greedy in the final step (from Piazza post 103)
		  actions[i] = chooseBestAction(i);
	  }
	  return new Policy(actions);
  }

  Random rand;
  
  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.0;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
}
