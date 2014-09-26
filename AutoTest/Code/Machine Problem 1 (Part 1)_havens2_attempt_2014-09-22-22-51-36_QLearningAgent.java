/*
Author: Nathan Havens
Date: 9/21/14
*/

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent
{
	//Initialize the agent with the states and actions available
	public void initialize(int numStates, int numActions)
	{
		qValue = new double[numStates][numActions];
		numOfStates = numStates;
		numOfActions = numActions;

		//Initialize the array
		for(int idx1 = 0; idx1 < numStates; idx1++)
		{
			for(int idx2 = 0; idx2 < numActions; idx2++)
			{
				qValue[idx1][idx2] = 0.0;
			}
		}
	}

	//Choose and action based on the epsilon-greedy Q learning algorithm
	public int chooseAction(int state)
	{
		double max_qValue = 0.0;
		int action = 0;

		Random randomGenerator = new Random();

		//Compare the random number to epsilon to determine whether to
		//perform a random action or follow the path with best utility
		if(randomGenerator.nextDouble() < epsilon)
		{
			action = randomGenerator.nextInt(4);
		}
		else
		{
			//Check each action to determine the path that yields the
			//highest utility and choose that path
			for(int idx = 0; idx < numOfActions; idx++)
			{
				if(qValue[state][idx] > max_qValue)
				{
					action = idx;
					max_qValue = qValue[state][idx];
				}
				else if(qValue[state][idx] == max_qValue)
				{
					//Randomly choose between the equal paths
					if(randomGenerator.nextDouble() >= 0.5)
					{
						action = idx;
						max_qValue = qValue[state][idx];
					}
				}
			}
		}
		return action;
	}

	//Update the policy based on the rewards and new states
	public void updatePolicy(double reward, int action, int oldState, int newState)
	{
		qValue[oldState][action] += alpha * (reward + max_q(newState) - qValue[oldState][action]);
	}

	//Return the policy of the agent
	public Policy getPolicy()
	{
		//Create a mapping from each state to the action that maximizes its utility
		double best_utility = 0.0;
		int[] actions = new int[numOfStates];
		for(int state = 0; state < numOfStates; state++)
		{
			//Reset the comparing utility every state, using the starting point
			//as the first action in the state
			best_utility = qValue[state][0];
			for(int action = 1; action < numOfActions; action++)
			{
				if(qValue[state][action] > best_utility)
				{
					actions[state] = action;
					best_utility = qValue[state][action];
				}
			}
		}
		return new Policy(actions);
	}

	//Private helper functions
	private double max_q(int newState)
	{
		double max_qValue = 0.0;
		for(int idx = 0; idx < numOfActions; idx++)
		{
			if(qValue[newState][idx] > max_qValue)
			{
				max_qValue = qValue[newState][idx];
			}
		}
		return max_qValue;
	}

	//Private member variables
	private static final double gamma = 0.9;
	private static final double alpha = 0.1;
	private static final double epsilon = 0.0;

	private int numOfStates;
	private int numOfActions;
	private double[][] qValue;
}

