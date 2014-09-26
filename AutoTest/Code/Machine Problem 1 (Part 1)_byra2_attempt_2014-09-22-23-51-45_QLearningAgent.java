/* Author: Shravan Byra */

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent 
{
	private static final double discount = 0.9;
	private static final double rate = 0.1;
  	private static final double epsilon = 0.0;
	
	
	private Random rand;
	private int numOfStates;
	private int numOfActions;
	private double[][] qValue;
	
	
	public QLearningAgent() 
	{
		this.rand = new Random();
	}
	
	public void initialize(int numOfStates, int numOfActions) 
	{
	    this.qValue = new double[numOfStates][numOfActions];
	    this.numOfStates = numOfStates;
	    this.numOfActions = numOfActions;
	}
	
	 
	
	public int chooseAction(int state) 
	{
		double randomness = rand.nextDouble();
		if(randomness < epsilon)
		{
			return rand.nextInt(4);
		}
		else
		{
			return getBestAction(state);
		}
		
	}
	
	public void updatePolicy(double reward, int action, int oldState, int newState) 
	{
		double newStateMaxQ = getBestQ(newState);
		qValue[oldState][action] += rate*(reward + (discount* newStateMaxQ) - qValue[oldState][action]);
	}
	
	public Policy getPolicy() 
	{
		int[] actions = new int[this.numOfStates];
    	for (int i = 0; i < this.numOfStates; i++) 
    	{
			actions[i] = getBestAction(i);
    	}

    	return new Policy(actions);
	}
	
	private int getBestAction(int state)
	{
		
		double maxQ = qValue[state][0];
		int myAction = 0;
		for(int i = 0; i < 4; i++)
		{
			if(maxQ < qValue[state][i])
			{
				maxQ = qValue[state][i];
				myAction = i;
			}
		}
		int numMax = 0;
		for(int i = 0; i < 4; i++)
		{
			if(qValue[state][i] == maxQ)
			{
				numMax++;
			}
		}
		int[] randArr = new int[numMax];
		int index = 0;
		for(int i = 0; i < 4; i++)
		{
			if(qValue[state][i] == maxQ)
			{
				randArr[index] = i;
				index++;
			}
		}
		return randArr[rand.nextInt(randArr.length)];
	}
	
	private double getBestQ(int state)
	{

		double maxQ = qValue[state][0];
		int myAction = 0;
		for(int i = 0; i < 4; i++)
		{
			if(maxQ < qValue[state][i])
			{
				maxQ = qValue[state][i];
				myAction = i;
			}
		}
		
		return maxQ;
	}

}
