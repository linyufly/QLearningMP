/**
 * @author Victoria Vanderbach
 *
 */
import java.util.Random;

public class QLearningAgent implements Agent {

	public QLearningAgent() {
		rand = new Random();
	}


	@Override
	public void initialize(int numOfStates, int numOfActions) { //this should be good now
			numStates = numOfStates; //what are my states? these are generated for me
			numActions = numOfActions; //actions: only 4 actions NSEW
			Q_Array = new double[numOfStates][numOfActions];
			
			//should I start with a random policy? No, I don't generate policy until the end

	}

	@Override
	public int chooseAction(int state) { //This should be ok for now
		//need to sort through Q_Array at state index to find best action
		//May have to adjust this to follow it's policy instead of generating as it goes
		//No, see comment in initialize.
		
		//need to implement random action using e-greedy algorithm
		int maxIndex = 0;
		for(int i = 1; i < numActions; i++)
		{
			if(Q_Array[state][i] > Q_Array[state][maxIndex])
				maxIndex = i;
			else if(Q_Array[state][i] == Q_Array[state][maxIndex])
			{
				if(rand.nextDouble() <= 0.5)
				{
					maxIndex = i;
				}
			}
		}
		//implement e-greedy here
		if (rand.nextDouble() < (epsilon))
			maxIndex = rand.nextInt(numActions);
		
		return maxIndex;
	}

	@Override
	public void updatePolicy(double reward, int action, int oldState,
			int newState) {
		//Simulator determines rewards, not me
		//Q_Array[state][action]
		double oldQ = (1.0 - rate) * Q_Array[oldState][action];
		
		//find action that maxs newQ
		int maxIndex = 0;
		for(int i = 1; i < numActions; i++)
		{
			if(Q_Array[newState][i] > Q_Array[newState][maxIndex])
				maxIndex = i;
		}
		
		double newQ = rate * (reward + (discount * Q_Array[newState][maxIndex]));
		
		Q_Array[oldState][action] = oldQ + newQ;
		
		return;
		
	}

	@Override
	public Policy getPolicy() { //This should be good now
		// TODO return policy that I have currently generated.
		int[] actions = new int[numStates];
	    for (int i = 0; i < numStates; i++) {
	    	int maxIndex = 0;
	    	for (int j = 0; j < numActions; j++)
	    	{
	    		if(Q_Array[i][j] > Q_Array[i][maxIndex])
					maxIndex = j;
	    	}
	    	actions[i] = maxIndex;
	    }
		return new Policy(actions);
	}
	
	private static final double discount = 0.9;
	private static final double rate = 0.1;
	private static final double epsilon = 0.00;
	
	Random rand;
	private int numStates;
	private int numActions;
	private double[][] Q_Array;
}
