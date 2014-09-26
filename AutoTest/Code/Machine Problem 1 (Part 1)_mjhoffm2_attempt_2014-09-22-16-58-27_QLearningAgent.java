/* Author: Mingcheng Chen */
//also Matt Hoffman

import java.util.Random;
import java.util.ArrayList;

public class QLearningAgent implements Agent
{
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
		if(rand.nextFloat() < epsilon)
		{
			//explore
			return rand.nextInt(numOfActions);
		}
		//return best opition, or random of best options
		int best = 0;
		int bcount = 1;

		for(int i=1; i<numOfActions; i++)
		{
			if(qValue[state][i] > qValue[state][best])
			{
				bcount = 1;
				best = i;
			}
			else if(qValue[state][i] == qValue[state][best])
			{
				bcount++;
			}
		}

		if(bcount == 1)
			return best;
		int[] options = new int[bcount];

		for(int i=0, j=0; i<numOfActions && j<bcount; i++)
		{
			if(qValue[state][i] == qValue[state][best])
			{
				options[j] = i;
				j++;
			}
		}
		return options[rand.nextInt(bcount)];
	}

	private double bestUtility(int state)
	{
		double utility = qValue[state][0];

		for(int i=1; i<numOfActions; i++)
		{
			if(qValue[state][i] > utility)
				utility = qValue[state][i];
		}
		return utility;
	}

	public void updatePolicy(double reward, int action, int oldState, int newState)
	{
		//Q(a, s) = Q(a, s) + a*(Reward + discount*max(Q(nextStates)) - Q(a, s))
		qValue[oldState][action] = qValue[oldState][action] + rate*(reward + discount*bestUtility(newState) - qValue[oldState][action]);
	}

	public Policy getPolicy()
	{
		int[] action = new int[numOfStates];

		for(int state = 0; state < this.numOfStates; state++)
		{
			//return best opition, or random of best options
			int best = 0;
			int bcount = 1;

			for(int i=1; i<numOfActions; i++)
			{
				if(qValue[state][i] > qValue[state][best])
				{
					bcount = 1;
					best = i;
				}
				else if(qValue[state][i] == qValue[state][best])
				{
					bcount++;
				}
			}

			if(bcount == 1)
			{
				action[state] = best;
				continue;
			}
			int[] options = new int[bcount];

			for(int i=0, j=0; i<numOfActions && j<bcount; i++)
			{
				if(qValue[state][i] == qValue[state][best])
				{
					options[j] = i;
					j++;
				}
			}
			action[state] = options[rand.nextInt(bcount)];
		}

		return new Policy(action);
	}

	private static final double discount = 0.9;
	private static final double rate = 0.1;
	private static final double epsilon = 0.0; //0.05;

	private int numOfStates;
	private int numOfActions;
	private Random rand;
	private double[][] qValue;
}
