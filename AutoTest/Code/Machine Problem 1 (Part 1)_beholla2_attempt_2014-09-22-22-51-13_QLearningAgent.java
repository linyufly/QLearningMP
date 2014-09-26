/* Author: Mingcheng Chen */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent {
  public QLearningAgent() {
    
  }

  public void initialize(int numOfStates, int numOfActions) {
    this.qValue = new double[numOfStates][numOfActions];
    this.numOfStates = numOfStates;
    this.numOfActions = numOfActions;
	this.rand = new Random();
  }

  private double bestUtility(int state) {
	
	double solution = this.qValue[state][0];
	
	for(int a = 0; a < this.numOfActions; a++)
	{
		if(this.qValue[state][a] > solution)
		{
			solution = this.qValue[state][a];
		}
	}
	
	return solution;
  }

  public int chooseAction(int state) {
	
	double test = this.rand.nextDouble();
	
	double maximum = this.bestUtility(state);
	
	ArrayList<Integer> candidates = new ArrayList<Integer>();
	
	if (test <= epsilon) 
	{
      return this.rand.nextInt(numOfActions);
    }
	else
	{	
		for(int a = 0; a < this.numOfActions; a++)
		{
			if(this.qValue[state][a] == maximum)
			{
				candidates.add(a);
			}
		}
	}
	
	return candidates.get(this.rand.nextInt(candidates.size()));
  }

  public void updatePolicy(double reward, int action,
                           int oldState, int newState) {
	
	this.qValue[oldState][action] *= (1.0 - rate);
	
    this.qValue[oldState][action] += rate * (reward + discount * this.bestUtility(newState));

  }

  public Policy getPolicy() {
	int[] actions = new int[this.numOfStates];

    for (int a = 0; a < this.numOfStates; a++)
	{
		double maximum = this.bestUtility(a);

		for (int b = 0; b < this.numOfActions; b++) 
		{
			if (this.qValue[a][b] == maximum) 
			{
				actions[a] = b;
			}
      }
    }

    return new Policy(actions);
  }


  private static final double discount = 0.9;
  private static final double rate = 0.1;
  private static final double epsilon = 0.05;

  private int numOfStates;
  private int numOfActions;
  private double[][] qValue;
  private Random rand;
}
