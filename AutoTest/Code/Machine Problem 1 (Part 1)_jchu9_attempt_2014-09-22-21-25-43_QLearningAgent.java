

import java.util.Random;

public class QLearningAgent
	implements Agent {

	public void initialize(int numOfStates, int numOfActions)
	{
		numStates = numOfStates;
		numActions = numOfActions;
		
		qValue = new double[numOfStates][numOfActions];
		for (int k = 0; k < numOfStates; k++)
		{
			for (int l = 0; l < numOfActions; l++)
			{
				qValue[k][l] = 0;
			}
		}
	}

	public int chooseAction(int state)
	{
		int act = 0;
		double current = -100000000000000000.0; // in case all q values are negative
		
		for (int i = 0; i < numActions; i++)
		{
			if (current < qValue[state][i])
			{
				// if current qValue is less than qValue of next action,
				// set current = qValue of that action
				// and set act = that action
				act = i;
				current = qValue[state][i];
			}
			if (current == qValue[state][i])
			{
				Random rand = new Random();
				int b = rand.nextInt(2);
				if (b == 0)
					act = i;
			}
		}
		
		return act;
	}
	
	private double maxQ(int state)
	{
		// find the max q value from the possible actions, return

		Random rand = new Random();
		double x = rand.nextDouble();
		
		if (x < epsilon)
		{
			int act = rand.nextInt(4);
			double cue = qValue[state][act];
			return cue;
		}
		
		else
		{
			double cue = -100000000000000000.0;
			
			for (int i = 0; i < numActions; i++)
			{
				if (cue < qValue[state][i])
					cue = qValue[state][i];
			}
			
			return cue;
		}
	}

	public void updatePolicy(double reward, int action, int oldState, int newState)
	{
//		System.out.println(reward);
		qValue[oldState][action] = qValue[oldState][action] +
					alpha * (reward + gamma * maxQ(newState) - qValue[oldState][action]);
//		System.out.printlnln(qValue[oldState][action]);
	}

	public Policy getPolicy()
	{
//		System.out.println("getPolicy");
		int[] retval = new int[numStates];
		
		// go through all states
		for (int i = 0; i < numStates; i++)
		{
			double current = -Double.MAX_VALUE; // in case there are large negative q values
			// go through each action for each state
			for (int j = 0; j < numActions; j++)
			{
				if (current < qValue[i][j])
				{
					// if current qValue of the previous action for this state is
					// smaller than qValue of the current action for this state,
					// set current to be the qValue of the current action,
					// then add to retval
					retval[i] = j;
					current = qValue[i][j];
				}
				if (current == qValue[i][j])
				{
					Random rand = new Random();
					int b = rand.nextInt(2);
					if (b == 0)
						retval[i] = j;
				}
			}
			if (current == -Double.MAX_VALUE)
			{
				Random rand = new Random();
				retval[i] = rand.nextInt(4);
			}
		}
//		System.out.println(retval);
		return new Policy(retval);
	}
	
	private int numStates;
	private int numActions;
	private double[][] qValue;
	
	private static final double gamma = 0.9;
	private static final double alpha = 0.1;
	private static final double epsilon = 1.0;
}
