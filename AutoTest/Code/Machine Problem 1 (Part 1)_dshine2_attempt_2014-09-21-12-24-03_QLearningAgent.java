/* Author: Deepak Shine */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent 
{
	public int x = 0; //Position of agent in grid  
	public int y = 4; 
	
	public QLearningAgent() 
	{
		//this.rand = new Random();
	}

	public void initialize(int numOfStates, int numOfActions) 
	{
		this.qValue = new double[numOfStates][numOfActions];
		this.numOfStates = numOfStates;
		this.numOfActions = numOfActions;
	}

	private /*double*/ void bestUtility(int state) 
	{

	}

	// Only 4 actions, so returns an integer between 0-3
	public int chooseAction(int state) 
	{
		double rand = Math.random(); 
		if (rand < epsilon)
		{
			return (int)(Math.random() * 4); //Take a random action
		}
		double[] qValues = new double[4];
		qValues[0] = qValue[state][0]; 
		qValues[1] = qValue[state][1]; 
		qValues[2] = qValue[state][2];
		qValues[3] = qValue[state][3];
		
		int indexWithHighestQ = 0;
		int multHighestQ = 0; 
		
		double maxQ = Math.max(qValues[0], qValues[1]); 
		maxQ = Math.max(maxQ, qValues[2]);
		maxQ = Math.max(maxQ, qValues[3]);
		
		if (maxQ == qValues[0])
		{
			indexWithHighestQ = 0;
			multHighestQ++; 
		}
	    if (maxQ == qValues[1])
		{
			indexWithHighestQ = 1;
			multHighestQ++;
		}
		if (maxQ == qValues[2])
		{
			indexWithHighestQ = 2;
			multHighestQ++;
		}
		if (maxQ == qValues[3])
		{
			indexWithHighestQ = 3;
			multHighestQ++;
		}
		
		if (multHighestQ > 1) //Multiple actions that result in highest Q 
			indexWithHighestQ = (int)(Math.random() * multHighestQ); 
		
		return indexWithHighestQ; 
	}

	// Processes the reward received by executing action which transition
	// oldState->newState
	public void updatePolicy(double reward, int action,
	                           int oldState, int newState)
	{
		  //Update the Q value
		  qValue[oldState][action] = qValue[oldState][action] 
				  + rate * (reward + discount * maxQ(newState) - qValue[oldState][action]);
	}
	
	public double maxQ(int state)
	{
		double q0 = qValue[state][0]; 
		double q1 = qValue[state][1]; 
		double q2 = qValue[state][2]; 
		double q3 = qValue[state][3]; 
		
		double currentMax = Math.max(q0, q1);
		currentMax = Math.max(currentMax, q2); 
		currentMax = Math.max(currentMax, q3);
		return currentMax; 
	}

	public Policy getPolicy() 
	{
		int[] bestActions = new int[numOfStates];
		for (int i = 0; i < bestActions.length; i++)
		{
			bestActions[i] = chooseAction(i); 
		}
		
		Policy policy = new Policy(bestActions); 
		return policy; 
	}

	private static final double discount = 0.9;
	private static final double rate = 0.1;
	private static final double epsilon = 0.0; // SET TO 0 BEFORE SUBMISSION - Randomness in choosing action

	private int numOfStates;
	private int numOfActions;
	private double[][] qValue;
}
