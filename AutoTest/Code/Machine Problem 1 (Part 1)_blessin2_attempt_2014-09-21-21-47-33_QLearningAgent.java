import java.util.ArrayList;

/**
 * @author Scott Blessing
 * CS 440 - MP1
 * 9/20/14
 */

public class QLearningAgent implements Agent {

	private int numStates = 0;
	private int numActions = 0;
	
	private static final double discount = 0.9;
	private static final double rate = 0.1;
	private static final double epsilon = 0.0;
	
	private double[][] qValue;
	
	/**
	 * Default Constructor
	 */
	public QLearningAgent() {
		
	}

	@Override
	public void initialize(int numOfStates, int numOfActions) 
	{
		numStates = numOfStates;
		numActions = numOfActions;
		qValue = new double[numStates][numActions];
	}


	@Override
	public int chooseAction(int state) 
	{
		double r = Math.random();
		if (r < epsilon)
		{
			//Random
			return (int)(Math.random()*numActions);
		}
		else
		{
			//Greedy using Q
			return getBestQChoice(state);
		}
	}
	
	
	private int getBestQChoice(int state)
	{
		boolean[] topChoices = new boolean[numActions];
		double bestActionVal = qValue[state][0];
		for (int i = 0; i < numActions; i++)
		{
			double val = qValue[state][i];
			if (val > bestActionVal)
			{
				bestActionVal = val;
				topChoices = new boolean[numActions];
				topChoices[i] = true;
			}
			else if (val == bestActionVal)
			{
				topChoices[i] = true;
			}
		}
			
		//Choose from best valued options
		int numChoices = 0;
		for (boolean b : topChoices)
			if (b) numChoices++;
		int[] choices = new int[numChoices];
		int j = 0;
		for (int i = 0; i < numActions; i++)
			if (topChoices[i]) choices[j++] = i;
		
		return choices[((int)(Math.random()*numChoices))];
	}

	private double maxQ(int state)
	{
		double maxVal = qValue[state][0];
		for (double val : qValue[state])
			if (val > maxVal)
				maxVal = val;
		return maxVal;
	}

	@Override
	public void updatePolicy(double reward, int action, int oldState, int newState) 
	{
		qValue[oldState][action] += rate * (reward + discount*maxQ(newState) - qValue[oldState][action]); 
	}

	
	@Override
	public Policy getPolicy() 
	{
		int[] actions = new int[numStates];
		for (int i = 0; i < numStates; i++)
		{
			actions[i] = getBestQChoice(i);
		}
		return new Policy(actions);
	}

}
