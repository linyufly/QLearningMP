
import java.util.Random;



public class QLearningAgent implements Agent {
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
		double epsilonCheck = rand.nextDouble();
		if(epsilonCheck <= epsilon)
		{
			return rand.nextInt(numOfActions);
			
		}
		
		else
		{
			double maxQ = qValue[state][0];
			for(int i = 0; i < numOfActions; i++)
			{
				if(qValue[state][i] > maxQ)
				{
					maxQ = qValue[state][i];
				}
			}
			int[] maxActions = new int[numOfActions];
			int counter = 0;
			for(int i = 0; i<numOfActions; i++)
			{
				if(qValue[state][i] == maxQ)
				{
					maxActions[counter] = i;
					counter++;
				}
				
			}
			
			
			int chosenAction = rand.nextInt(counter);
			return maxActions[chosenAction];
		}
	}
	
	public void updatePolicy(double reward, int action,
            int oldState, int newState)
	{
		double oldQ = qValue[oldState][action];
		double maxNewStateQ = 0;
		for(int i = 0; i<numOfActions; i++)
		{
			if(qValue[newState][i] > maxNewStateQ)
				maxNewStateQ = qValue[newState][i];
		}
		qValue[oldState][action] = oldQ + rate*(reward + discount*maxNewStateQ - oldQ);
		
	}
	
	public Policy getPolicy()
	{
		int[] policyArray = new int[numOfStates];
		for(int j = 0; j<numOfStates; j++)
		{
			policyArray[j] = chooseAction(j);
		}
		
		return new Policy(policyArray);
	}
	
	private static final double discount = 0.9;
	private static final double rate = 0.1;
	private static final double epsilon = 0.0;

	private int numOfStates;
	private int numOfActions;
	private double[][] qValue;
	private Random rand;
	
}
