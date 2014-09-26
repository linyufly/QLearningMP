import java.util.Random;
import java.util.Vector;
public class QLearningAgent implements Agent {

	int[] states;
	int[] actions;
	private static final double gamma = 0.9;	//discount on rewards value
	private static final double epsilon = 0.0;	//chance of doing random choice
	private static final double alpha = 0.1;	//learning rate
	double[][] qValues;		// array representing each q-value for each state-action pair
	Random randomizer;		// chooses random numbers
	
	public QLearningAgent()
	{
		this.randomizer = new Random();
	}
	public void initialize(int numOfStates, int numOfActions)	// sets QlearningAgent's number of states and actions
	{
		actions = new int[numOfActions];
		states = new int[numOfStates];
		qValues = new double[numOfStates][numOfActions];
		for(int i = 0; i < 4; i++)
		{
			actions[i]=i;
		}
		for(int i = 0; i < numOfStates; i++)
		{
			states[i]=i;
		}
	}
	
	 //chooseAction chooses which action to take based on epsilon-randomness and the best option from the qValues table 
	
	public int chooseAction(int state)	
	{
		if(randomizer.nextDouble()<epsilon)
		{
			return randomizer.nextInt(actions.length);
		}
		return findBestAction(qValues[state]);
		
	}
	// updatePolicy updates the qvalue of the previous state based on the q-learning formula
	public void updatePolicy(double reward, int action, int oldState, int newState)
	{
		double maxNextQ = findHighestQ(qValues[newState]);
		qValues[oldState][action] = qValues[oldState][action]+alpha*(reward+gamma*maxNextQ-qValues[oldState][action]);
		
	}
	// returns a list of which action to take in which state based on the qValues table
	public Policy getPolicy()
	{
		int[] policy = new int[states.length];
		for(int i = 0; i < states.length; i++)
		{
			policy[i]=findBestAction(qValues[i]);
		}
		Policy thisPolicy = new Policy(policy);
		return thisPolicy;
	}
	
	// findHighestQ returns the highest Q value for state s with available actions: actions. 
	public double findHighestQ(double[] actions)
	{
		double highest = actions[0];
		for (double s: actions)
		{
			if(s>highest)
				highest = s;
		}
		return highest;
	}
	// findBestAction looks at the available actions for a given state and finds which of them returns the highest Q value
	public int findBestAction(double[] actions)
	{
		Vector<Integer> options = new Vector<Integer>();
		double highest = actions[0];
		for (double s: actions)
		{
			if(s>highest)
				highest = s;
		}
		
		for(int i = 0; i < actions.length; i++)
		{
			if(actions[i]==highest)
				options.add(i);
		}
		return options.get(randomizer.nextInt(options.size()));

	}
	
	
}
